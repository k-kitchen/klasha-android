package com.klasha.android.ui

import android.content.Context
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.klasha.android.databinding.ActivityPinBinding

internal class PinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPinBinding
    
    private var pin: String = ""
    private var isPin: Boolean = false

    private var transactionCredentials: TransactionCredentials = TransactionCredentials.getInstance()
    private lateinit var imm: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra("email")
        binding.tvEmail.text = SpannableStringBuilder(email)

        binding.pinChar0.setOnClickListener {
            textInputFocus()
        }

        binding.pinChar1.setOnClickListener {
            textInputFocus()
        }

        binding.pinChar2.setOnClickListener {
            textInputFocus()
        }

        binding.pinChar3.setOnClickListener {
            textInputFocus()
        }

        binding.textInput.setOnKeyListener { _, keyCode, event ->
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

    private fun textInputFocus(){
        binding.textInput.requestFocus()
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.textInput, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun keyDown(partPin: Char){
        pin += partPin
        if (pin.length<4){
            fillPinSpaces()
        }else if (pin.length == 4){
            fillPinSpaces()
            submit()
        }
    }

    private fun backKeyDown(){
        val length = pin.length
        if (length > 0){
            pin = pin.slice(0 until length - 1)
            fillPinSpaces()
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
        imm.hideSoftInputFromWindow(binding.textInput.windowToken, 0)
    }
}