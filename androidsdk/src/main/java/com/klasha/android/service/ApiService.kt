package com.klasha.android.service

import com.klasha.android.model.Currency
import com.klasha.android.model.request.*
import com.klasha.android.model.request.BankTransferRequest
import com.klasha.android.model.request.ChargeCardRequest
import com.klasha.android.model.request.ExchangeRequest
import com.klasha.android.model.request.SendCardPaymentRequest
import com.klasha.android.model.request.ValidatePaymentRequest
import com.klasha.android.model.response.*
import com.klasha.android.model.response.BankTransferResponse
import com.klasha.android.model.response.ChargeCardResponse
import com.klasha.android.model.response.ExchangeResponse
import com.klasha.android.model.response.SendCardPaymentResponse
import com.klasha.android.model.response.ValidatePaymentResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

internal interface ApiService {

    @POST("pay/{currency}/cardpayment")
    fun sendCardPayment(@Body body: SendCardPaymentRequest, @Path(value="currency", encoded = true) currency: Currency): Call<SendCardPaymentResponse>

    @POST("pay/{currency}/validatepayment")
    fun validatePayment(@Body body: ValidatePaymentRequest, @Path(value="currency", encoded = true) currency: Currency): Call<ValidatePaymentResponse>

    @POST("nucleus/exchange/")
    fun exchangeMoney(@Body body: ExchangeRequest) : Call<ExchangeResponse>

    @POST("pay/{currency}/charge")
    fun chargeCard(@Body body: ChargeCardRequest, @Path(value="currency", encoded = true) currency: Currency): Call<ChargeCardResponse>

    @POST("pay/{currency}/banktransfer")
    fun bankTransfer(@Body body: BankTransferRequest, @Path(value = "currency", encoded = true) currency: Currency): Call<BankTransferResponse>

    @POST("pay/wallet/popup")
    fun walletLogin(@Body body: WalletLoginRequest): Call<WalletLoginResponse>

    @POST("pay/wallet/makePayment")
    fun walletPayment(@Body body: MakeWalletPaymentRequest): Call<MakeWalletPaymentResponse>

    @POST("pay/{currency}/mobilemoney")
    fun mobileMoney(@Body body: MobileMoneyRequest, @Path(value="currency", encoded = true) currency: Currency): Call<MobileMoneyResponse>

    @POST("pay/{currency}/cardpayment")
    fun mpesa(@Body body: MPESARequest, @Path(value="currency", encoded = true) currency: Currency): Call<MPESAResponse>
}