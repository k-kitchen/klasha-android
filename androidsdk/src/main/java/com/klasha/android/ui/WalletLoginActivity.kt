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
            binding.amount.text = "%,.2f".format(extras.getDouble("amount",0.0))
            binding.email.text = extras.getString("email","")
        }

        binding.buttonLogin.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()

            binding.usernameLayout.errorIconDrawable = null
            binding.usernameLayout.isErrorEnabled = false
            binding.username.error = null
            binding.passwordLayout.errorIconDrawable = null
            binding.passwordLayout.isErrorEnabled = false
            binding.password.error = null

            if (username.isEmpty()){
                binding.usernameLayout.isErrorEnabled = true

                binding.usernameLayout.error = "Email can not be empty"
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()){
                binding.usernameLayout.isErrorEnabled = true

                binding.usernameLayout.error = "Please enter a valid email address"
                return@setOnClickListener
            }

            if (password.isEmpty()){
                binding.passwordLayout.isErrorEnabled = true

                binding.passwordLayout.error = "Password can not be empty"
                return@setOnClickListener
            }

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