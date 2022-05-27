package com.klasha.android.model.response

import com.google.gson.annotations.SerializedName
import com.klasha.android.model.Currency

internal data class ExchangeResponse(
    val rate: Double,
    val destinationCurrency: Currency,
    val amount: Double,
    var cards: Array<Any>,
)

// Card

/**
 * whenever the status is error; data is null
 */
internal data class ChargeCardResponse(
    val status: String,
    val message: String,
    @SerializedName("tx_ref")
    val txRef: String,
    @SerializedName("flw_ref")
    val flwRef: String,
    val data: Array<Any>
)

/**
 * whenever the status is error, data will be null
 * whenever the status is successful, tx_ref, amount, processor_response are set
 */
internal data class ValidatePaymentResponse(
    val status: String,
    val message: String,
    val data: Array<Any>,
    @SerializedName("tx_ref")
    val txRef: String,
    val amount: Double,
    @SerializedName("processor_response")
    val processorResponse: String
)

/**
 * when there is an error; status, message, data and txRef are set
 */
internal data class SendCardPaymentResponse(
    @SerializedName("tx_ref")
    val txRef: String,
    val data: Data,
    val message: String,
    val status: String
) {
    internal data class Data(
        val status: String,
        val message: String,
        val meta: Meta
    )

    internal data class Meta(
        val authorization: Authorization
    )

    internal data class Authorization(
        val mode: String,
        val field: Array<String>
    )
}

// Wallet

/**
 * when there is an error; exception, error are set and http-status is 400
 */
internal data class WalletLoginResponse(
    val firstName: String,
    val lastName: String,
    val email: String,
    val exception: String,
    val error: String
)

/**
 * when there is an error; message, error are set and http-status is 400
 */
internal data class MakeWalletPaymentResponse(
    val id: Int,
    val toWallet: Wallet,
    val walletTnxId: String,
    val amount: Double,
    val narration: String,
    val userTransactionType: String,
    val fromWallet: Wallet,
    val tempId: Double,
    val tnxStatus: String,
    val dAmount: Double,
    val createdAt: String,
    val updatedAt: String,
    val message: String,
    val error: String
){
    internal data class Wallet(
        val id: Int,
        val accountNo: String,
        val balance: Double,
        val userId: Double,
        val businessId: Int,
        val currency: Currency,
        val walletPin: WalletPin,
        val verified: Boolean,
        val secondStepVerified: Boolean,
        val enabled: Boolean,
        val coin: Double,
        val tag: String,
        val createdAt: String,
        val updatedAt: String
    )

    internal data class WalletPin(
        val id: Double,
        val pin: Double,
        val createdAt: String,
        val updatedAt: String
    )
}

// Bank Transfer

internal data class BankTransferResponse(
    val status: String,
    val message: String,
    val meta: Meta,
    @SerializedName("tx_ref")
    val txRef: String
){
    internal data class Meta(
        val authorization: Authorization
    )
    internal data class Authorization(
        @SerializedName("transfer_reference")
        val transferReference: String,
        @SerializedName("transfer_account")
        val transferAccount: String,
        @SerializedName("transfer_bank")
        val transferBank: String,
        @SerializedName("account_expiration")
        val accountExpiration: String,
        @SerializedName("transfer_note")
        val transferMode: String,
        @SerializedName("transfer_amount")
        val transferAmount: Double,
        val mode: String
    )
}

// Mobile Money
/**
 * error only set when there is an error
 */
internal data class MobileMoneyResponse(
    val status: String,
    val message: String,
    @SerializedName("tx_ref")
    val txRef: String,
    val meta: Meta,
    val data: Data,
    val error: Int
){
    internal data class Meta(val authorization: Authorization)

    internal data class Authorization(val instruction: String)

    internal data class Data(
        val id: Double,
        val message: String,
        @SerializedName("tx_ref")
        val txRef: String,
        @SerializedName("flw_ref")
        val flwRef: String,
        @SerializedName("device_fingerprint")
        val deviceFingerprint: String,
        val amount: Double,
        @SerializedName("charged_amount")
        val chargedAmount: Double,
        @SerializedName("ap_fea")
        val appFee: Double,
        @SerializedName("merchant_fee")
        val merchantFee: Double,
        @SerializedName("processor_response")
        val processorResponse: String,
        @SerializedName("auth_model")
        val authModel: String,
        val currency: Currency,
        val ip: String,
        val narration: String,
        val status: String,
        @SerializedName("payment_type")
        val paymentType: String,
        @SerializedName("fraud_status")
        val fraudStatus: String,
        @SerializedName("chargeType")
        val chargeType: String,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("account_id")
        val accountId: Double,
        val customer: Customer
    )

    internal data class Customer(
        val id: Double,
        @SerializedName("phone_number")
        val phoneNumber: String,
        val name: String,
        val email: String,
        @SerializedName("created_at")
        val createdAt: String
    )
}

// mpesa

internal data class MPESAResponse(
    val status: String,
    val message: String,
    @SerializedName("tx_ref")
    val txRef: String,
    val data: Data
){

    internal data class Data(
        val id: Double,
        @SerializedName("tx_ref")
        val txRef: String,
        @SerializedName("flw_ref")
        val flwRef: String,
        @SerializedName("device_fingerprint")
        val deviceFingerprint: String,
        val amount: Double,
        @SerializedName("charged_amount")
        val chargedAmount: Double,
        @SerializedName("ap_fee")
        val appFee: Double,
        @SerializedName("merchant_fee")
        val merchantFee: Double,
        @SerializedName("processor_response")
        val processorResponse: String,
        @SerializedName("auth_model")
        val authModel: String,
        val currency: Currency,
        val ip: String,
        val narration: String,
        val status: String,
        @SerializedName("auth_url")
        val authUrl: String,
        @SerializedName("payment_type")
        val paymentType: String,
        @SerializedName("fraud_status")
        val fraudStatus: String,
        @SerializedName("chargeType")
        val chargeType: String,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("account_id")
        val accountId: Double,
        val customer: Customer
    )

    internal data class Customer(
        val id: Double,
        @SerializedName("phone_number")
        val phoneNumber: String,
        val name: String,
        val email: String,
        @SerializedName("created_at")
        val createdAt: String
    )
}

internal data class BaePayResponse(
    val message: String,
    val status: String
)

