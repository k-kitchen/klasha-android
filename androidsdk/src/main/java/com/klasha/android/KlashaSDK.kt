package com.klasha.android

import android.app.Activity
import android.content.Intent
import com.klasha.android.model.*
import com.klasha.android.model.request.*
import com.klasha.android.model.response.*
import com.klasha.android.ui.*
import retrofit2.Response
import java.lang.ref.WeakReference
import kotlin.collections.ArrayList
import kotlin.concurrent.thread


object KlashaSDK {

    private var isInitialized = false

    private var authToken: String? = null
    private var weakReferenceActivity: WeakReference<Activity> = WeakReference(null)
    private var country: Country? = null
    private var sourceCurrency: Currency? = null

    private var instance: Klasha? = null

    fun initialize(
        activity: Activity,
        authToken: String,
        userCountry: Country,
        businessCurrency: Currency,
        isDevelopment: Boolean = false,
    ) {
        this.authToken = authToken
        this.weakReferenceActivity = WeakReference<Activity>(activity)
        this.sourceCurrency = businessCurrency
        this.country = userCountry

        isInitialized = true

        instance = Klasha(authToken, weakReferenceActivity, isDevelopment)
    }


    fun chargeCard(charge: Charge, transactionCallback: TransactionCallback) {
        /*
        Flow for Card Payment
        1. Get Exchange
        2. Use exchange to send card payment
        3. Charge card
        4. Validate payment
         */

        if (!checkSdkInitialised(weakReferenceActivity.get()!!, transactionCallback)) {
            return
        }

        if (!checkValidAmount(charge.amount, transactionCallback)) {
            return
        }

        transactionCallback.transactionInitiated(charge.transactionReference)

        instance?.getExchange(ExchangeRequest(
            this.sourceCurrency!!,
            country!!.currency,
            charge.amount
        ),
            object : Klasha.ExchangeCallback {
                override fun success(response: Response<ExchangeResponse>) {
                    val amount = charge.amount
                    val rate = response.body()!!.rate
                    val sourceAmount = amount / rate
                    val sendCardRequest = SendCardPaymentRequest(
                        charge.card!!.number, charge.card.cvv.toString(),
                        charge.card.expiryMonth.toString(), charge.card.expiryYear.toString(),
                        country!!.currency, country!!.countryCode, amount, rate,
                        sourceCurrency!!, sourceAmount,
                        charge.transactionReference, charge.phone,
                        charge.email, charge.fullName
                    )
                    sendCardPayment(charge.email, sendCardRequest, transactionCallback)
                }

                override fun error(message: String) {
                    transactionCallback.error(weakReferenceActivity.get()!!, message)
                }

            })
    }

    fun bankTransfer(charge: Charge, transactionCallback: BankTransferTransactionCallback) {
        /*
        Flow for Bank Transfer
        1. Send Transaction details
         */

        if (!checkSdkInitialised(weakReferenceActivity.get()!!, transactionCallback)) {
            return
        }

        if (!checkValidAmount(charge.amount, transactionCallback)) {
            return
        }

        transactionCallback.transactionInitiated(charge.transactionReference)

        val bankTransferRequest = BankTransferRequest(
            charge.transactionReference,
            charge.amount,
            country!!.currency,
            charge.email,
            charge.phone
        )
        instance?.bankTransfer(
            bankTransferRequest,
            country!!.currency,
            object : Klasha.BankTransferCallback {
                override fun success(response: Response<BankTransferResponse>) {
                    val bt = response.body()!!
                    val btr = BankTransferResp(
                        bt.txRef,
                        bt.meta.authorization.transferAccount,
                        bt.meta.authorization.transferBank,
                        bt.meta.authorization.accountExpiration,
                        bt.meta.authorization.transferMode,
                        bt.meta.authorization.transferAmount,
                        bt.meta.authorization.mode
                    )
                    transactionCallback.success(weakReferenceActivity.get()!!, btr)
                }

                override fun error(message: String) {
                    transactionCallback.error(weakReferenceActivity.get()!!, message)
                }

            })
    }

    fun mobileMoney(charge: Charge, transactionCallback: TransactionCallback) {
        /*
        Flow for mobile money
        1. Send Transaction details
         */

        if (!checkSdkInitialised(weakReferenceActivity.get()!!, transactionCallback)) {
            return
        }

        transactionCallback.transactionInitiated(charge.transactionReference)

        val mobileMoneyRequest = MobileMoneyRequest(
            country!!.currency, charge.amount, charge.phone,
            charge.email, charge.fullName, charge.transactionReference,
            charge.mobileMoney!!.voucher, charge.mobileMoney.network

        )
        instance?.mobileMoney(
            mobileMoneyRequest,
            country!!.currency,
            object : Klasha.MobileMoneyCallback {
                override fun success(response: Response<MobileMoneyResponse>) {
                    transactionCallback.success(
                        weakReferenceActivity.get()!!,
                        response.body()!!.txRef
                    )
                }

                override fun error(message: String) {
                    transactionCallback.error(weakReferenceActivity.get()!!, message)
                }
            })
    }

    fun mpesa(charge: Charge, transactionCallback: TransactionCallback) {
        /*
        Flow for mpesa
        1. Send Transaction details
         */

        if (!checkSdkInitialised(weakReferenceActivity.get()!!, transactionCallback)) {
            return
        }

        if (!checkValidAmount(charge.amount, transactionCallback)) {
            return
        }

        transactionCallback.transactionInitiated(charge.transactionReference)

        val mpesaRequest = MPESARequest(
            charge.amount, charge.phone, charge.email,
            charge.fullName, charge.transactionReference, MPESAOption.mpesa
        )
        instance?.mpesa(mpesaRequest, country!!.currency, object : Klasha.MPESACallback {
            override fun success(response: Response<MPESAResponse>) {
                transactionCallback.success(
                    weakReferenceActivity.get()!!,
                    response.body()!!.txRef
                )
            }

            override fun error(message: String) {
                transactionCallback.error(weakReferenceActivity.get()!!, message)
            }

        })
    }

    fun wallet(charge: Charge, transactionCallback: TransactionCallback) {
        /*
        Flow for wallet
        1. Get Exchange
        2. Login with credentials
        3. Charge from wallet
        */

        if (!checkSdkInitialised(weakReferenceActivity.get()!!, transactionCallback)) {
            return
        }

        if (!checkValidAmount(charge.amount, transactionCallback)) {
            return
        }

        transactionCallback.transactionInitiated(charge.transactionReference)

        instance?.getExchange(ExchangeRequest(this.sourceCurrency!!, country!!.currency),
            object : Klasha.ExchangeCallback {
                override fun success(response: Response<ExchangeResponse>) {
                    val rate = response.body()!!.rate

                    getWalletCredentials(charge.amount, country!!.symbol, charge.email) { login ->
                        if (login.username.isEmpty() || login.password.isEmpty()) {
                            transactionCallback.error(
                                weakReferenceActivity.get()!!,
                                Error.INVALID_WALLET_LOGIN.name
                            )
                            return@getWalletCredentials
                        }
                        val walletLoginRequest = WalletLoginRequest(
                            login.username, login.password
                        )
                        walletLogin(walletLoginRequest, charge, rate, transactionCallback)
                    }
                }

                override fun error(message: String) {
                    transactionCallback.error(weakReferenceActivity.get()!!, message)
                }

            })
    }

    fun getBankCodes(transactionCallback: BankCodeCallback) {
        if (!checkSdkInitialised(weakReferenceActivity.get()!!, transactionCallback)) {
            return
        }

        instance?.getBankCodes(object : Klasha.BankCodeCallback {

            override fun success(response: Response<ArrayList<BankCodeResponse>>) {
                transactionCallback.success(weakReferenceActivity.get()!!, response.body()!!)
            }

            override fun error(message: String) {
                transactionCallback.error(weakReferenceActivity.get()!!, message)
            }
        })
    }

    fun ussd(charge: Charge, transactionCallback: USSDCallback) {
        if (!checkSdkInitialised(weakReferenceActivity.get()!!, transactionCallback)) {
            return
        }

        if (!checkValidAmount(charge.amount, transactionCallback)) {
            return
        }

        transactionCallback.transactionInitiated(charge.transactionReference)

        val ussdRequest = USSDRequest(
            charge.transactionReference,
            charge.accountBank,
            charge.amount,
            this.sourceCurrency!!,
            charge.email
        )
        instance?.ussd(ussdRequest, this.country!!.currency, object : Klasha.USSDCallback {
            override fun success(response: Response<USSDResponse>) {
                transactionCallback.success(weakReferenceActivity.get()!!, response.body()!!)
            }

            override fun error(message: String) {
                transactionCallback.error(weakReferenceActivity.get()!!, message)
            }

        })
    }

    fun baePay(charge: Charge, transactionCallback: BaePayCallback) {
        if (!isInitialized) {
            transactionCallback.error(
                weakReferenceActivity.get()!!,
                "SDK Not Initialized"
            )
            return
        }

        val baePayRequest = BaePayRequest(
            charge.amount,
            charge.baePay!!.description,
            charge.baePay.name,
            country!!.currency,
            charge.baePay.bae,
            charge.baePay.phoneNumber ?: "",
            charge.baePay.medium.value,
            charge.email,
            charge.baePay.baeEmail!!
        )
        instance?.baePay(baePayRequest, object : Klasha.BaePayCallback {
            override fun success(response: Response<BaePayResponse>) {
                transactionCallback.success(
                    weakReferenceActivity.get()!!,
                    response.body()!!.message
                )
            }

            override fun error(message: String) {
                transactionCallback.error(weakReferenceActivity.get()!!, message)
            }

        })
    }

    private fun sendCardPayment(
        email: String,
        sendCardRequest: SendCardPaymentRequest,
        transactionCallback: TransactionCallback
    ) {
        instance?.sendCardPayment(sendCardRequest, country!!.currency, object :
            Klasha.SendCardPaymentCallback {
            override fun success(response: Response<SendCardPaymentResponse>) {
                getPin(email) { pin ->
                    if (pin.isEmpty() || pin.length < 4) {
                        transactionCallback.error(
                            weakReferenceActivity.get()!!,
                            Error.INVALID_CARD_PIN.name
                        )
                        return@getPin
                    } else {
                        val chargeCardRequest = ChargeCardRequest(
                            response.body()!!.data.meta.authorization.mode,
                            pin, response.body()!!.txRef
                        )
                        chargeCard(
                            email,
                            chargeCardRequest,
                            transactionCallback
                        )
                    }
                }
            }

            override fun error(message: String) {
                transactionCallback.error(weakReferenceActivity.get()!!, message)

            }
        })
    }

    private fun chargeCard(
        email: String,
        chargeCardRequest: ChargeCardRequest,
        transactionCallback: TransactionCallback
    ) {
        instance?.chargeCard(chargeCardRequest, country!!.currency, object :
            Klasha.ChargeCardCallback {
            override fun success(response: Response<ChargeCardResponse>) {
                getOtp(email, response.body()!!.message) { otp ->
                    if (otp.isEmpty() || otp.length < 4) {
                        transactionCallback.error(
                            weakReferenceActivity.get()!!,
                            Error.INVALID_OTP.name
                        )
                        return@getOtp
                    } else {
                        val validatePaymentRequest = ValidatePaymentRequest(
                            otp,
                            response.body()!!.flwRef,
                            PaymentType.card
                        )
                        validatePayment(
                            validatePaymentRequest,
                            transactionCallback
                        )
                    }
                }

            }

            override fun error(message: String) {
                transactionCallback.error(weakReferenceActivity.get()!!, message)

            }

        })
    }

    private fun validatePayment(
        validatePaymentRequest: ValidatePaymentRequest,
        transactionCallback: TransactionCallback
    ) {
        instance?.validatePayment(
            validatePaymentRequest,
            country!!.currency,
            object :
                Klasha.ValidatePaymentCallback {
                override fun success(response: Response<ValidatePaymentResponse>) {
                    transactionCallback.success(
                        weakReferenceActivity.get()!!,
                        response.body()!!.txRef
                    )
                }

                override fun error(message: String) {
                    transactionCallback.error(weakReferenceActivity.get()!!, message)
                }
            })
    }

    private fun walletLogin(
        walletLoginRequest: WalletLoginRequest,
        charge: Charge, rate: Double,
        transactionCallback: TransactionCallback
    ) {
        instance?.walletLogin(
            walletLoginRequest,
            object :
                Klasha.WalletLoginCallback {
                override fun success(response: Response<WalletLoginResponse>) {
                    val amount = charge.amount
                    val sourceAmount = amount / rate
                    val walletPaymentRequest = MakeWalletPaymentRequest(
                        country!!.currency, charge.amount,
                        rate, sourceCurrency!!, sourceAmount,
                        charge.phone, charge.fullName,
                        charge.transactionReference, response.body()!!.email
                    )
                    walletPayment(walletPaymentRequest, transactionCallback)
                }

                override fun error(message: String) {
                    transactionCallback.error(weakReferenceActivity.get()!!, message)

                }
            })
    }

    private fun walletPayment(
        walletPaymentRequest: MakeWalletPaymentRequest,
        transactionCallback: TransactionCallback
    ) {
        instance?.walletPayment(
            walletPaymentRequest,
            object :
                Klasha.WalletPaymentCallback {
                override fun success(response: Response<MakeWalletPaymentResponse>) {
                    if (response.isSuccessful) {
                        transactionCallback.success(
                            weakReferenceActivity.get()!!,
                            response.body()!!.walletTnxId
                        )
                    } else {
                        transactionCallback.error(
                            weakReferenceActivity.get()!!,
                            response.body()!!.message
                        )
                    }
                }

                override fun error(message: String) {
                    transactionCallback.error(weakReferenceActivity.get()!!, message)

                }
            })
    }

    private fun checkValidAmount(amount: Double, callback: Callback): Boolean {
        return if (amount <= 0.0) {
            callback.error(
                weakReferenceActivity.get()!!,
                Error.ZERO_AMOUNT.name
            )
            false
        } else {
            true
        }
    }

    private fun checkSdkInitialised(activity: Activity, callback: Callback): Boolean {
        if (!isInitialized) {
            callback.error(activity, Error.SDK_NOT_INITIALISED.name)
        }
        return isInitialized
    }

    private fun getPin(email: String, callback: (String) -> Unit) {
        val intent = Intent(weakReferenceActivity.get(), PinActivity::class.java)
        intent.putExtra("email", email)
        weakReferenceActivity.get()!!.startActivity(intent)

        thread {
            val transactionCredentials = TransactionCredentials.getInstance()

            synchronized(transactionCredentials) {
                transactionCredentials.wait()
            }

            callback(transactionCredentials.pin)
        }
    }

    private fun getOtp(email: String, message: String, callback: (String) -> Unit) {
        val intent = Intent(weakReferenceActivity.get(), OtpActivity::class.java)
        intent.putExtra("message", message)
        intent.putExtra("email", email)
        weakReferenceActivity.get()!!.startActivity(intent)

        thread {
            val transactionCredentials = TransactionCredentials.getInstance()

            synchronized(transactionCredentials) {
                transactionCredentials.wait()
            }

            callback(transactionCredentials.otp)
        }
    }

    private fun getWalletCredentials(
        amount: Double,
        symbol: String,
        email: String,
        callback: (Login) -> Unit
    ) {
        val intent = Intent(weakReferenceActivity.get(), WalletLoginActivity::class.java)
        intent.putExtra("amount", amount)
        intent.putExtra("symbol", symbol)
        intent.putExtra("email", email)
        weakReferenceActivity.get()!!.startActivity(intent)

        thread {
            val transactionCredentials = TransactionCredentials.getInstance()

            synchronized(transactionCredentials) {
                transactionCredentials.wait()
            }

            callback(transactionCredentials.login)
        }
    }

    interface Callback {
        fun transactionInitiated(transactionReference: String)
        fun error(ctx: Activity, message: String)
    }

    interface TransactionCallback : Callback {
        fun success(ctx: Activity, transactionReference: String)
    }

    interface BankTransferTransactionCallback : Callback {
        fun success(ctx: Activity, bankTransferResponse: BankTransferResp)
    }

    interface BankCodeCallback : Callback {
        fun success(ctx: Activity, bankTransferResponse: ArrayList<BankCodeResponse>)
    }

    interface USSDCallback : Callback {
        fun success(ctx: Activity, ussdResponse: USSDResponse)
    }

    interface BaePayCallback : Callback {
        fun success(ctx: Activity, baePayResponse: String)
    }
}
