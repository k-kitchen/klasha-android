package com.klasha.android

import android.app.Activity
import com.klasha.android.model.BankCodeResponse
import com.klasha.android.model.Currency
import com.klasha.android.model.Error
import com.klasha.android.model.USSDResponse
import com.klasha.android.model.request.*
import com.klasha.android.model.response.*
import com.klasha.android.service.ApiFactory
import com.klasha.android.service.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.lang.ref.WeakReference
import java.net.SocketException
import java.net.UnknownHostException
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLHandshakeException

internal class Klasha(
    authToken: String,
    weakReferenceActivity: WeakReference<Activity>,
    isDevelopment: Boolean,
) {
    private val baseUrl: String =
        if (isDevelopment) "https://dev.kcookery.com/" else "https://gate.klasapps.com/"
    private val service: ApiService =
        ApiFactory.createService(weakReferenceActivity.get()!!, baseUrl, authToken)

    fun getExchange(exchangeRequest: ExchangeRequest, exchangeCallBack: ExchangeCallback) {
        service.exchangeMoney(exchangeRequest)
            .enqueue(object : Callback<ExchangeResponse> {
                override fun onResponse(
                    call: Call<ExchangeResponse>,
                    response: Response<ExchangeResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        exchangeCallBack.success(response)
                    } else {
                        exchangeCallBack.error(Error.BAD_EXCHANGE_REQUEST.name)
                    }
                }

                override fun onFailure(call: Call<ExchangeResponse>, t: Throwable) {
                    val message = parseError(t)
                    exchangeCallBack.error(message)
                }
            })
    }

    fun validatePayment(
        validatePaymentRequest: ValidatePaymentRequest,
        destinationCurrency: Currency,
        validatePaymentCallback: ValidatePaymentCallback
    ) {
        service.validatePayment(validatePaymentRequest, destinationCurrency)
            .enqueue(object : Callback<ValidatePaymentResponse> {
                override fun onResponse(
                    call: Call<ValidatePaymentResponse>,
                    response: Response<ValidatePaymentResponse>
                ) {
                    if (response.isSuccessful && response.body()?.status?.trim() == "successful") {
                        validatePaymentCallback.success(response)
                    } else {
                        validatePaymentCallback.error(
                            response.body()?.processorResponse ?: Error.BAD_REQUEST.name
                        )
                    }
                }

                override fun onFailure(call: Call<ValidatePaymentResponse>, t: Throwable) {
                    val message = parseError(t)
                    validatePaymentCallback.error(message)
                }

            })
    }

    fun sendCardPayment(
        sendCardPaymentRequest: SendCardPaymentRequest,
        destinationCurrency: Currency,
        sendCardPaymentCallback: SendCardPaymentCallback
    ) {
        service.sendCardPayment(sendCardPaymentRequest, destinationCurrency)
            .enqueue(object : Callback<SendCardPaymentResponse> {
                override fun onResponse(
                    call: Call<SendCardPaymentResponse>,
                    response: Response<SendCardPaymentResponse>
                ) {
                    if (response.isSuccessful && response.body()?.data?.status?.trim() == "success") {
                        sendCardPaymentCallback.success(response)
                    } else {
                        sendCardPaymentCallback.error(
                            response.body()?.message ?: Error.BAD_REQUEST.name
                        )
                    }
                }

                override fun onFailure(call: Call<SendCardPaymentResponse>, t: Throwable) {
                    val message = parseError(t)
                    sendCardPaymentCallback.error(message)
                }

            })
    }

    fun chargeCard(
        chargeCardRequest: ChargeCardRequest,
        destinationCurrency: Currency,
        chargeCardCallback: ChargeCardCallback
    ) {
        service.chargeCard(chargeCardRequest, destinationCurrency)
            .enqueue(object : Callback<ChargeCardResponse> {
                override fun onResponse(
                    call: Call<ChargeCardResponse>,
                    response: Response<ChargeCardResponse>
                ) {
                    if (response.isSuccessful && response.body()?.status?.trim() != "error") {
                        chargeCardCallback.success(response)
                    } else {
                        chargeCardCallback.error(response.body()?.message ?: Error.BAD_REQUEST.name)
                    }
                }

                override fun onFailure(call: Call<ChargeCardResponse>, t: Throwable) {
                    val message = parseError(t)
                    chargeCardCallback.error(message)
                }
            })
    }

    fun bankTransfer(
        bankTransferRequest: BankTransferRequest,
        destinationCurrency: Currency,
        bankTransferCallback: BankTransferCallback
    ) {
        service.bankTransfer(bankTransferRequest, destinationCurrency)
            .enqueue(object : Callback<BankTransferResponse> {
                override fun onResponse(
                    call: Call<BankTransferResponse>,
                    response: Response<BankTransferResponse>
                ) {
                    if (response.isSuccessful && response.body()?.status?.trim() == "success") {
                        bankTransferCallback.success(response)
                    } else {
                        bankTransferCallback.error(
                            response.body()?.message ?: Error.BAD_REQUEST.name
                        )
                    }
                }

                override fun onFailure(call: Call<BankTransferResponse>, t: Throwable) {
                    val message = parseError(t)
                    bankTransferCallback.error(message)
                }

            })
    }

    fun mobileMoney(
        mobileMoneyRequest: MobileMoneyRequest,
        destinationCurrency: Currency,
        mobileMoneyCallback: MobileMoneyCallback
    ) {
        service.mobileMoney(mobileMoneyRequest, destinationCurrency)
            .enqueue(object : Callback<MobileMoneyResponse> {
                override fun onResponse(
                    call: Call<MobileMoneyResponse>,
                    response: Response<MobileMoneyResponse>
                ) {
                    if (response.isSuccessful && response.body()?.status?.trim() != "error") {
                        mobileMoneyCallback.success(response)
                    } else {
                        mobileMoneyCallback.error(
                            response.body()?.message ?: Error.TRANSACTION_NOT_SUPPORTED.name
                        )
                    }
                }

                override fun onFailure(call: Call<MobileMoneyResponse>, t: Throwable) {
                    val message = parseError(t)
                    mobileMoneyCallback.error(message)
                }
            })
    }

    fun mpesa(
        mpesaRequest: MPESARequest,
        destinationCurrency: Currency,
        mpesaCallback: MPESACallback
    ) {
        service.mpesa(mpesaRequest, destinationCurrency)
            .enqueue(object : Callback<MPESAResponse> {
                override fun onResponse(
                    call: Call<MPESAResponse>,
                    response: Response<MPESAResponse>
                ) {
                    if (response.isSuccessful && response.body()?.status?.trim() != "error") {
                        mpesaCallback.success(response)
                    } else {
                        mpesaCallback.error(response.body()?.message ?: Error.BAD_REQUEST.name)
                    }
                }

                override fun onFailure(call: Call<MPESAResponse>, t: Throwable) {
                    val message = parseError(t)
                    mpesaCallback.error(message)
                }

            })
    }

    fun walletLogin(
        walletLoginRequest: WalletLoginRequest,
        walletLoginCallback: WalletLoginCallback
    ) {
        service.walletLogin(walletLoginRequest)
            .enqueue(object : Callback<WalletLoginResponse> {
                override fun onResponse(
                    call: Call<WalletLoginResponse>,
                    response: Response<WalletLoginResponse>
                ) {
                    if (response.isSuccessful) {
                        walletLoginCallback.success(response)
                    } else {
                        walletLoginCallback.error(
                            response.body()?.error ?: Error.INVALID_CREDENTIALS.name
                        )
                    }
                }

                override fun onFailure(call: Call<WalletLoginResponse>, t: Throwable) {
                    val message = parseError(t)
                    walletLoginCallback.error(message)
                }

            })
    }

    fun walletPayment(
        walletPaymentRequest: MakeWalletPaymentRequest,
        walletPaymentCallback: WalletPaymentCallback
    ) {
        service.walletPayment(walletPaymentRequest)
            .enqueue(object : Callback<MakeWalletPaymentResponse> {
                override fun onResponse(
                    call: Call<MakeWalletPaymentResponse>,
                    response: Response<MakeWalletPaymentResponse>
                ) {
                    if (response.isSuccessful) {
                        walletPaymentCallback.success(response)
                    } else {
                        walletPaymentCallback.error(Error.INVALID_CREDENTIALS.name)
                    }
                }

                override fun onFailure(call: Call<MakeWalletPaymentResponse>, t: Throwable) {
                    val message = parseError(t)
                    walletPaymentCallback.error(message)
                }

            })
    }

    fun ussd(ussdRequest: USSDRequest, destinationCurrency: Currency, ussdCallback: USSDCallback) {
        service.ussd(ussdRequest, destinationCurrency)
            .enqueue(object : Callback<USSDResponse> {
                override fun onResponse(
                    call: Call<USSDResponse>,
                    response: Response<USSDResponse>
                ) {
                    if (response.isSuccessful) {
                        ussdCallback.success(response)
                    } else {
                        ussdCallback.error(Error.SERVER_ERROR.name)
                    }
                }

                override fun onFailure(call: Call<USSDResponse>, t: Throwable) {
                    val message = parseError(t)
                    ussdCallback.error(message)
                }
            })
    }

    fun baePay(baePayRequest: BaePayRequest, baePayCallback: BaePayCallback) {
        service.baePay(baePayRequest)
            .enqueue(object : Callback<BaePayResponse> {
                override fun onResponse(
                    call: Call<BaePayResponse>,
                    response: Response<BaePayResponse>
                ) {
                    if (response.isSuccessful) {
                        baePayCallback.success(response)
                    } else {
                        baePayCallback.error(Error.SERVER_ERROR.name)
                    }
                }

                override fun onFailure(call: Call<BaePayResponse>, t: Throwable) {
                    val message = parseError(t)
                    baePayCallback.error(message)
                }
            })
    }

    fun getBankCodes(bankCodeCallback: BankCodeCallback) {
        service.getBankCodes()
            .enqueue(object : Callback<ArrayList<BankCodeResponse>> {
                override fun onResponse(
                    call: Call<ArrayList<BankCodeResponse>>,
                    response: Response<ArrayList<BankCodeResponse>>
                ) {
                    if (response.isSuccessful) {
                        bankCodeCallback.success(response)
                    } else {
                        bankCodeCallback.error(Error.SERVER_ERROR.name)
                    }
                }

                override fun onFailure(call: Call<ArrayList<BankCodeResponse>>, t: Throwable) {
                    val message = parseError(t)
                    bankCodeCallback.error(message)
                }
            })
    }

    private fun parseError(t: Throwable): String {
        return when (t) {
            is HttpException -> {
                when (t.code()) {
                    HttpsURLConnection.HTTP_UNAUTHORIZED -> Error.UNAUTHORISED.name
                    HttpsURLConnection.HTTP_FORBIDDEN -> Error.UNAUTHORISED.name
                    HttpsURLConnection.HTTP_INTERNAL_ERROR -> Error.SERVER_ERROR.name
                    HttpsURLConnection.HTTP_BAD_REQUEST -> Error.BAD_REQUEST.name
                    else -> t.localizedMessage
                }
            }
            is UnknownHostException -> {
                Error.LIMITED_CONNECTIVITY.name
            }
            is SSLHandshakeException -> {
                Error.LIMITED_CONNECTIVITY.name
            }
            is SocketException -> {
                Error.LIMITED_CONNECTIVITY.name
            }
            else -> {
                t.message.toString()
            }
        }
    }

    interface SDKCallback {
        fun error(message: String)
    }

    interface ExchangeCallback : SDKCallback {
        fun success(response: Response<ExchangeResponse>)
    }

    interface SendCardPaymentCallback : SDKCallback {
        fun success(response: Response<SendCardPaymentResponse>)
    }

    interface ChargeCardCallback : SDKCallback {
        fun success(response: Response<ChargeCardResponse>)
    }

    interface ValidatePaymentCallback : SDKCallback {
        fun success(response: Response<ValidatePaymentResponse>)
    }

    interface BankTransferCallback : SDKCallback {
        fun success(response: Response<BankTransferResponse>)
    }

    interface MobileMoneyCallback : SDKCallback {
        fun success(response: Response<MobileMoneyResponse>)
    }

    interface MPESACallback : SDKCallback {
        fun success(response: Response<MPESAResponse>)
    }

    interface WalletLoginCallback : SDKCallback {
        fun success(response: Response<WalletLoginResponse>)
    }

    interface WalletPaymentCallback : SDKCallback {
        fun success(response: Response<MakeWalletPaymentResponse>)
    }

    interface BankCodeCallback : SDKCallback {
        fun success(response: Response<ArrayList<BankCodeResponse>>)
    }

    interface USSDCallback : SDKCallback {
        fun success(response: Response<USSDResponse>)
    }

    interface BaePayCallback : SDKCallback {
        fun success(response: Response<BaePayResponse>)
    }
}