package com.klasha.android.ui

import android.content.Context
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.klasha.android.databinding.ActivityOtpBinding

internal class OtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding

    private var otp: String = ""
    private var isOtp: Boolean = false

    private var transactionCredentials: TransactionCredentials = TransactionCredentials.getInstance()
    private var imm: InputMethodManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val message = intent.getStringExtra("message")
        val email = intent.getStringExtra("email")
        binding.otpMessage.text = SpannableStringBuilder(message)
        binding.tvEmail.text = SpannableStringBuilder(email)

        binding.pinChar0.setOnClickListener { otpTextInputFocus() }

        binding.pinChar1.setOnClickListener { otpTextInputFocus() }

        binding.pinChar2.setOnClickListener { otpTextInputFocus() }

        binding.pinChar3.setOnClickListener { otpTextInputFocus() }

        binding.pinChar4.setOnClickListener { otpTextInputFocus() }

        binding.pinChar5.setOnClickListener { otpTextInputFocus() }

        binding.otpTextInput.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN){
                when (keyCode) {
                    KeyEvent.KEYCODE_0 -> keyDown('0')
                    KeyEvent.KEYCODE_1 -> keyDown('1')
                    KeyEvent.KEYCODE_2 -> keyDown('2')
                    KeyEvent.KEYCODE_3 -> keyDown('3')
                    KeyEvent.KEYCODE_4 -> keyDown('4')
                    KeyEvent.KEYCODE_5 -> keyDown('5')
                    KeyEvent.KEYCODE_6 -> keyDown('6')
                    KeyEvent.KEYCODE_7 -> keyDown('7')
                    KeyEvent.KEYCODE_8 -> keyDown('8')
                    KeyEvent.KEYCODE_9 -> keyDown('9')
                    else -> backKeyDown()
                }
            }
            false
        }
    }

    private fun otpTextInputFocus(){
        binding.otpTextInput.requestFocus()
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm!!.showSoftInput(binding.otpTextInput, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun keyDown(partPin: Char){
        otp += partPin
        if (otp.length<6){
            fillOtpSpace()
        }else if (otp.length == 6){
            fillOtpSpace()
            submit()
        }
    }

    private fun backKeyDown(){
        val length = otp.length
        if (length > 0){
            otp = otp.slice(0 until length - 1)
            fillOtpSpace()
        }
    }

    private fun fillOtpSpace(){
        binding.pinChar0.text = SpannableStringBuilder((otp.getOrNull(0)?:"").toString())
        binding.pinChar1.text = SpannableStringBuilder((otp.getOrNull(1)?:"").toString())
        binding.pinChar2.text = SpannableStringBuilder((otp.getOrNull(2)?:"").toString())
        binding.pinChar3.text = SpannableStringBuilder((otp.getOrNull(3)?:"").toString())
        binding.pinChar4.text = SpannableStringBuilder((otp.getOrNull(4)?:"").toString())
        binding.pinChar5.text = SpannableStringBuilder((otp.getOrNull(5)?:"").toString())
    }

    private fun submit(){
        transactionCredentials.otp = otp
        isOtp = true
        synchronized(transactionCredentials){
            transactionCredentials.notify()
        }
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!isOtp){
            transactionCredentials.pin = ""
            synchronized(transactionCredentials){
                transactionCredentials.notify()
            }
        }
        imm?.hideSoftInputFromWindow(binding.otpTextInput.windowToken, 0)
    }
}