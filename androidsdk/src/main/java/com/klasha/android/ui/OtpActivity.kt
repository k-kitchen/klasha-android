package com.klasha.android.ui

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.appcompat.app.AppCompatActivity
import com.klasha.android.databinding.ActivityOtpBinding

internal class OtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding
    private var otp: String = ""
    private var isOtp: Boolean = false

    private var transactionCredentials: TransactionCredentials = TransactionCredentials.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar?.title = "OTP"

        val message = intent.getStringExtra("message")
        binding.otpMessage.text = SpannableStringBuilder(message)

        binding.button0.setOnClickListener {
            buttonAction('0')
        }
        binding.button1.setOnClickListener {
            buttonAction('1')
        }
        binding.button2.setOnClickListener {
            buttonAction('2')
        }
        binding.button3.setOnClickListener {
            buttonAction('3')
        }
        binding.button4.setOnClickListener {
            buttonAction('4')
        }
        binding.button5.setOnClickListener {
            buttonAction('5')
        }
        binding.button6.setOnClickListener {
            buttonAction('6')
        }
        binding.button7.setOnClickListener {
            buttonAction('7')
        }
        binding.button8.setOnClickListener {
            buttonAction('8')
        }
        binding.button9.setOnClickListener {
            buttonAction('9')
        }
        binding.buttonBack.setOnClickListener {
            val length = otp.length
            if (length > 0){
                otp = otp.slice(0 until length - 1)
                fillOtpSpace()
            }else{
                return@setOnClickListener
            }
        }

        binding.buttonOk.setOnClickListener {
            submit()
        }
    }

    private fun buttonAction(partOtp: Char){
        otp += partOtp
        fillOtpSpace()
    }

    private fun fillOtpSpace(){
        binding.otpEditText.text = SpannableStringBuilder(otp)
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
    }
}