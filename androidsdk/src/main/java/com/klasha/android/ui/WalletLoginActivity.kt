package com.klasha.android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.klasha.android.databinding.ActivityWalletLoginBinding

class WalletLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWalletLoginBinding
    private var isLogin = false

    private var transactionCredentials: TransactionCredentials = TransactionCredentials.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWalletLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null){
            binding.symbol.text = extras.getString("symbol","")
            binding.amount.text = extras.getDouble("amount",0.0).toString()
            binding.email.text = extras.getString("email","")
        }

        binding.buttonLogin.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()

            val login = Login(username, password)
            submit(login)
        }

        binding.buttonCancel.setOnClickListener {
            finish()
        }
    }

    private fun submit(login: Login){
        transactionCredentials.login = login
        isLogin = true
        synchronized(transactionCredentials){
            transactionCredentials.notify()
        }
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!isLogin){
            transactionCredentials.login = Login("", "")
            synchronized(transactionCredentials){
                transactionCredentials.notify()
            }
        }
    }
}