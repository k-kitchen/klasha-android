package com.klasha.android.model.request

import com.google.gson.annotations.SerializedName
import com.klasha.android.model.*
import com.klasha.android.model.CountryCode
import com.klasha.android.model.MPESAOption
import com.klasha.android.model.PaymentType

internal data class ExchangeRequest(
    val sourceCurrency: Currency,
    val destinationCurrency: Currency,
    val amount: Double = 1.0,
    var email: String = "",
    var phone: String = ""
)

// Card

internal data class ChargeCardRequest(
    val mode: String,
    val pin: String,
    @SerializedName("tx_ref")
    val txRef: String
)

internal data class ValidatePaymentRequest(
    val otp: String,
    @SerializedName("flw_ref")
    val flwRef: String,
    val type: PaymentType
)

internal data class SendCardPaymentRequest(
    @SerializedName("card_number")
    val cardNumber: String,
    val cvv: String,
    @SerializedName("expiry_month")
    val expiryMonth: String,
    @SerializedName("expiry_year")
    val expiryYear: String,
    val currency: Currency,
    val country: CountryCode,
    val amount: Double,
    val rate: Double,
    val sourceCurrency: Currency,
    val sourceAmount: Double,
    @SerializedName("tx_ref")
    val transactionReference: String,
    @SerializedName("phone_number")
    val phoneNumber: String = "",
    val email: String = "",
    @SerializedName("fullname")
    val fullName: String = "",
) {
    @SerializedName("redirect_url")
    private val redirectURL: String = "https://165.232.102.181:7701/pay/ravecallback"
    private val businessId: Int = 1
    private val paymentType: String = "woo"
    private val rememberMe: Boolean = true

}

// Wallet

internal data class WalletLoginRequest(
    private val username: String,
    private val password: String
)

internal data class MakeWalletPaymentRequest(
    val currency: Currency,
    val amount: Double,
    val rate: Double,
    val sourceCurrency: Currency,
    val sourceAmount: Double,
    val phone_number: String,
    @SerializedName("fullname")
    val fullName: String,
    @SerializedName("tx_ref")
    val transactionReference: String,
    val email: String
) {
    private val paymentType: String = "woo"
    private val rememberMe: Boolean = true
}

// Bank Transfer

internal data class BankTransferRequest(
    @SerializedName("tx_ref")
    val transactionReference: String,
    val amount: Double ,
    val currency: Currency,
    val email: String,
    @SerializedName("phone_number")
    val phoneNumber: String = ""
)

// Mobile Money

internal data class MobileMoneyRequest(
    val currency: Currency,
    val amount: Double,
    @SerializedName("phone_number")
    val phoneNumber: String,
    val email: String,
    @SerializedName("fullname")
    val fullName: String,
    @SerializedName("tx_ref")
    val txRef: String,
    val voucher: String,
    val network: Network
)

// mpesa

internal data class MPESARequest(
    val amount: Double,
    @SerializedName("phone_number")
    val phoneNumber: String,
    val email: String,
    @SerializedName("fullname")
    val fullName: String,
    @SerializedName("tx_ref")
    val transactionReference: String,
    val option: MPESAOption,
)

internal data class USSDRequest(
    @SerializedName("tx_ref")
    val transactionReference: String,
    @SerializedName("account_bank")
    val accountBank: String,
    val amount: Double,
    val currency: Currency,
    val email: String
)