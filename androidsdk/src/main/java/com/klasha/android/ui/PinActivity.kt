package com.klasha.android.ui

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.appcompat.app.AppCompatActivity
import com.klasha.android.databinding.ActivityPinBinding

internal class PinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPinBinding
    
    private var pin: String = ""
    private var isPin: Boolean = false

    private var transactionCredentials: TransactionCredentials = TransactionCredentials.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPinBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
            val length = pin.length
            if (length > 0){
                pin = pin.slice(0 until length - 1)
                fillPinSpaces()
            }else{
                return@setOnClickListener
            }
        }
    }

    private fun buttonAction(partPin: Char){
        pin += partPin
        if (pin.length<4){
            fillPinSpaces()
        }else if (pin.length == 4){
            fillPinSpaces()
            submit()
        }
    }

    private fun fillPinSpaces(){
        binding.pinChar0.text = SpannableStringBuilder((pin.getOrNull(0)?:"").toString())
        binding.pinChar1.text = SpannableStringBuilder((pin.getOrNull(1)?:"").toString())
        binding.pinChar2.text = SpannableStringBuilder((pin.getOrNull(2)?:"").toString())
        binding.pinChar3.text = SpannableStringBuilder((pin.getOrNull(3)?:"").toString())
    }

    private fun submit(){
        isPin = true
        transactionCredentials.pin = pin
        synchronized(transactionCredentials){
            transactionCredentials.notify()
        }
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!isPin){
            transactionCredentials.pin = ""
            synchronized(transactionCredentials){
                transactionCredentials.notify()
            }
        }
    }
}