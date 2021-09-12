package com.klasha.android.ui

internal class TransactionCredentials private constructor(): Object() {
    private var _pin: String? = null
    private var _otp: String? = null
    private var _login: Login? = Login("", "")

    companion object{
        private  val instance = TransactionCredentials()

        fun getInstance(): TransactionCredentials{
            return instance
        }
    }

    var pin: String
        get() = _pin?:""
        set(value) {
            _pin = value
        }

    var otp: String
        get() = _otp?:""
        set(value) {
            _otp = value
        }

    var login: Login
        get() = _login!!
        set(value) {
            _login?.username = value.username
            _login?.password = value.password
        }
}

internal data class Login(
    var username: String,
    var password: String
)
