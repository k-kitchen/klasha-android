package com.klasha

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.klasha.android.KlashaSDK
import com.klasha.android.model.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        KlashaSDK.initialize(
            this,
            "GN1gmENvEPXxH6QcjmQgsDbaQAT/HzVYcMq6GG769rF3kFhTJol7Ixb5MhpIGPTyscJWjY29OxUBCHI+Sj+4rg==",
            Country.NIGERIA, //Customer's country
            Currency.USD, //Business currency
            false, //For Dev testing
        )

        val paymentButton = findViewById<Button>(R.id.trigger)

        paymentButton.setOnClickListener {
            //testCardPayment()
            //testBankTransfer()
            //testWallet()
            //testMobileMoney()
            //testMpesa()
            //testGetBankCodes()
        }
    }

    private fun testCardPayment() {
        val testCard = Card(
            "5531886652142950", 9, 2032, 564
        )
        val card = Card(
            "5061041150007629190", 11, 2023, 229
        )

        val charge = Charge(
            50.0, "dchiaha@gmail.com", "Duke", card
        )

        KlashaSDK.chargeCard(charge, object : KlashaSDK.TransactionCallback {
            override fun transactionInitiated(transactionReference: String) {
                println("CARD PAYMENT INITIATED")
                // Implementation when transaction is initiated
            }

            override fun success(ctx: Activity, transactionReference: String) {
                // Implementation when transaction is successful
                ctx.runOnUiThread {
                    // UI code goes here
                }
                println("CARD PAYMENT SUCCESS: $transactionReference")
                // Non UI code can be here
            }

            override fun error(ctx: Activity, message: String) {
                // Implementation when transaction fails
                ctx.runOnUiThread {
                    // UI code goes here
                }
                println("CARD PAYMENT ERROR: $message")
                // Non UI code can be here
            }

        })
    }

    private fun testBankTransfer() {
        val charge = Charge(
            100.0,
            "test@gmail.com",
            "testname",
            null,
        )


        KlashaSDK.bankTransfer(charge, object : KlashaSDK.BankTransferTransactionCallback {
            override fun transactionInitiated(transactionReference: String) {
                println("BANK TRANSFER INITIATED")
                // Implementation when transaction is initiated
            }

            override fun success(ctx: Activity, bankTransferResponse: BankTransferResp) {
                // Implementation when transaction is successful
                ctx.runOnUiThread {
                    // UI code goes here
                }
                println("BANK TRANSFER SUCCESS: $bankTransferResponse")
                // Non UI code can be here
            }

            override fun error(ctx: Activity, message: String) {
                // Implementation when transaction fails
                ctx.runOnUiThread {
                    // UI code goes here
                }
                println("BANK TRANSFER ERROR? $message")
                // Non UI code can be here
            }
        })
    }

    private fun testWallet() {
        val charge = Charge(
            50.0,
            "test@gmail.com",
            "testname",
            null,
        )

        KlashaSDK.wallet(charge, object : KlashaSDK.TransactionCallback {
            override fun transactionInitiated(transactionReference: String) {
                println("WALLET INITIATED")
                // Implementation when transaction is initiated
            }

            override fun success(ctx: Activity, transactionReference: String) {
                // Implementation when transaction is successful
                ctx.runOnUiThread {
                    // UI code goes here
                }
                println("WALLET SUCCESS: $transactionReference")
                // Non UI code can be here
            }

            override fun error(ctx: Activity, message: String) {
                // Implementation when transaction fails
                ctx.runOnUiThread {
                    // UI code goes here
                }
                println("WALLET ERROR? $message")
                // Non UI code can be here
            }

        })
    }

    private fun testMobileMoney() {
        val mobileMoney = MobileMoney(
            "voucher", Network.MTN
        )

        val charge = Charge(
            100.0,
            "test@gmail.com",
            "testname",
            null,
            mobileMoney,
        )

        KlashaSDK.mobileMoney(charge, object : KlashaSDK.TransactionCallback {
            override fun transactionInitiated(transactionReference: String) {
                // Implementation when transaction is initiated
                println("MOBILE MONEY INITIATED")
            }

            override fun success(ctx: Activity, transactionReference: String) {
                // Implementation when transaction is successful
                ctx.runOnUiThread {
                    // UI code goes here
                }
                println("MOBILE MONEY SUCCESS? $transactionReference")
                // Non UI code can be here
            }

            override fun error(ctx: Activity, message: String) {
                // Implementation when transaction fails
                ctx.runOnUiThread {
                    // UI code goes here
                }
                println("MOBILE MONEY ERROR? $message")
                // Non UI code can be here
            }
        })
    }

    private fun testMpesa() {
        val charge = Charge(
            1000.0,
            "test@gmail.com",
            "testname",
            null,
            phone = "254792371978",
        )

        KlashaSDK.mpesa(charge, object : KlashaSDK.TransactionCallback {
            override fun transactionInitiated(transactionReference: String) {
                // Implementation when transaction is initiated
                println("MPESA INITIATED")
            }

            override fun success(ctx: Activity, transactionReference: String) {
                // Implementation when transaction is successful
                ctx.runOnUiThread {
                    // UI code goes here
                }
                println("MPESA SUCCESS? $transactionReference")
                // Non UI code can be here
            }

            override fun error(ctx: Activity, message: String) {
                // Implementation when transaction fails
                ctx.runOnUiThread {
                    // UI code goes here
                }
                println("MPESA ERRPR? $message")
                // Non UI code can be here
            }

        })
    }

    private fun testGetBankCodes() {
        KlashaSDK.getBankCodes(object : KlashaSDK.BankCodeCallback {
            override fun transactionInitiated(transactionReference: String) {
                println("GET BANK CODES INITIATED")
            }

            override fun success(
                ctx: Activity, bankTransferResponse: ArrayList<BankCodeResponse>
            ) {}

            override fun error(ctx: Activity, message: String) {
                println("GET BANK CODES ERROR $message")
            }

        })
    }
}