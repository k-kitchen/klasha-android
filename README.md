## Klasha Android SDK

The Klasha Android SDK Supports 5 payments methods out of the box, they are same as that supported by the web platform. They are

- Card Payment
- Bank Transfer
- Mobile Money
- MPESA
- Klasha wallet

To start using the SDK you would need your x-auth-token.

#### Step 1: Initialize the SDK

```kotlin
KlashaSDK
    .initialize(
        WeakReference(activity),
        KLASHA_AUTH_TOKEN,
        Country.NIGERIA, //Customer's country
        Currency.GHS //Source currency
    )
```

#### Step 2a: Card Payment

```kotlin
val card = Card(
    cardNumber,
    exMonth,
    exYear,
    cvv,
    name
)

val charge = Charge(
    amount,
    email,
    name,
    card
)

KlashaSDK.chargeCard(charge, object : KlashaSDK.TransactionCallback {
    override fun transactionInitiated(transactionReference: String) {
        // Implementation when transaction is initiated
    }

    override fun success(ctx: Activity, transactionReference: String) {
        // Implementation when transaction is successful
        ctx.runOnUiThread {
            // UI code goes here
        }
        // Non UI code can be here
    }

    override fun error(ctx: Activity, message: String) {
        // Implementation when transaction fails
        ctx.runOnUiThread {
            // UI code goes here
        }
        // Non UI code can be here
    }

})
```

#### Step 2b: Bank Transfer
```kotlin

val charge = Charge(
    amount,
    email,
    name,
    null
    null
)

KlashaSDK.bankTransfer(charge, object : KlashaSDK.BankTransferTransactionCallback{
    override fun transactionInitiated(transactionReference: String) {
        // Implementation when transaction is initiated
    }
    override fun success(ctx: Activity, bankTransferResponse: BankTransferResp) {
        // Implementation when transaction is successful
        ctx.runOnUiThread {
            // UI code goes here
        }
        // Non UI code can be here
    }

    override fun error(ctx: Activity, message: String) {
        // Implementation when transaction fails
        ctx.runOnUiThread {
            // UI code goes here
        }
        // Non UI code can be here
    }
})

```

#### Step 2c: Klasha wallet

```kotlin

val charge = Charge(
    amount,
    email,
    name,
    null
    null
)

KlashaSDK.wallet(charge, object : KlashaSDK.TransactionCallback {
    override fun transactionInitiated(transactionReference: String) {
        // Implementation when transaction is initiated
    }

    override fun success(ctx: Activity, transactionReference: String) {
        // Implementation when transaction is successful
        ctx.runOnUiThread {
            // UI code goes here
        }
        // Non UI code can be here
    }

    override fun error(ctx: Activity, message: String) {
        // Implementation when transaction fails
        ctx.runOnUiThread {
            // UI code goes here
        }
        // Non UI code can be here
    }

})
```

#### Step 2d: Mobile money
```kotlin

val mobileMoney = MobileMoney(
    voucher,
    Network.MTN
)

val charge = Charge(
    amount,
    email,
    name,
    null,
    null,
    mobileMoney
)

KlashaSDK.mobileMoney(charge, object : KlashaSDK.TransactionCallback{
    override fun error(ctx: Activity, message: String) {
        // Implementation when transaction is initiated
    }

    override fun success(ctx: Activity, transactionReference: String) {
        // Implementation when transaction is successful
        ctx.runOnUiThread {
            // UI code goes here
        }
        // Non UI code can be here
    }

    override fun transactionInitiated(transactionReference: String) {
        // Implementation when transaction fails
        ctx.runOnUiThread {
            // UI code goes here
        }
        // Non UI code can be here
    }

})

```

#### Step 2e: MPESA
```kotlin

val charge = Charge(
    amount,
    email,
    name,
    null,
)

KlashaSDK.mpesa(charge, object : KlashaSDK.TransactionCallback{
    override fun error(ctx: Activity, message: String) {
        // Implementation when transaction is initiated
    }

    override fun success(ctx: Activity, transactionReference: String) {
        // Implementation when transaction is successful
        ctx.runOnUiThread {
            // UI code goes here
        }
        // Non UI code can be here
    }

    override fun transactionInitiated(transactionReference: String) {
        // Implementation when transaction fails
        ctx.runOnUiThread {
            // UI code goes here
        }
        // Non UI code can be here
    }

})

```

#### Step 2f: USSD
```kotlin

val charge = Charge(
    amount,
    email,
    name,
    null,
    null,
    phone,
    accountBank // bank code
)

KlashaSDK.ussd(charge, object : KlashaSDK.USSDCallback{
    override fun success(ctx: Activity, ussdResponse: USSDResponse) {
        // Implementation when transaction fails
        ctx.runOnUiThread {
            // UI code goes here
        }
        // Non UI code can be here    
    }

    override fun transactionInitiated(transactionReference: String) {
        // Implementation when transaction fails
        ctx.runOnUiThread {
            // UI code goes here
        }
        // Non UI code can be here 
    }

    override fun error(ctx: Activity, message: String) {
        // Implementation when transaction is initiated
    }
})

```

#### Getting bank Codes
```kotlin
KlashaSDK.getBankCodes(object : KlashaSDK.BankCodeCallback{
    override fun success(
        ctx: Activity,
        bankTransferResponse: ArrayList<BankCodeResponse>
    ) {
        // Implementation
    }

    override fun transactionInitiated(transactionReference: String) {
        // Implementation
    }

    override fun error(ctx: Activity, message: String) {
        // Implementation
    }

})
```

### Importing the library

1. Add https://jitpack.io to settings.gradle file

```Gradle

dependencyResolutionManagement {
    repositories {
        //....
        //....
        maven { url "https://jitpack.io" }
    }
}

```

2. Add the latest version of the library to the dependency section of your app

```Gradle

dependencies {
    //...

    implementation '....'

    //...
}

```

## Handing errors
To make it easier to handle errors in a custom way, there are some error codes included below

- NO_INTERNET
- INVALID_CARD_PIN
- INVALID_OTP
- INVALID_CREDENTIALS
- TRANSACTION_NOT_SUPPORTED
- UNAUTHORISED
- SERVER_ERROR
- BAD_REQUEST
- BAD_EXCHANGE_REQUEST
- LIMITED_CONNECTIVITY
- SDK_NOT_INITIALISED
- ZERO_AMOUNT
- INVALID_WALLET_LOGIN