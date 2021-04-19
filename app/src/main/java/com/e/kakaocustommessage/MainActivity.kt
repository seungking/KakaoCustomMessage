package com.e.kakaocustommessage

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.Constants
import com.anjlab.android.iab.v3.TransactionDetails
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BillingProcessor.IBillingHandler {

    var bp: BillingProcessor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bp = BillingProcessor(
            this,
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0Susq9pniZ2eQ5m4T/rcFdmoJn4DeA2/wlXHoPCjnXRRpsbTzvufTHJi1oEqLgpVKHADXhIrfHuIvFZOL0sjbwW5IKOEg+cZAoOvOaKXGYoQ1YA+wnmePV+ldJztNB49MY44BGdogGcu+U/dTpygG7oksPEpw/PAijUd4d95C41nWUXRW9cDD/XQQM5euEZ6ubOmElGaJrHxphzcLtLoJZZy/h/3kPErfhYIKJTFq5+SbroQ4F26oyFl5cP6oyCr5N4vBPp1QTDNb91X/x+46N1QFZtkMF3b5amLh+b1ngj4jCkFEbLA0r0wkIaklznSahY4ioeXNbhwBrmaeE0RfQIDAQAB",
            this
        )

        txt1.setOnClickListener{
            startActivity(Intent(this, SelectActivity::class.java))
        }

        howToLayout.setOnClickListener {
            startActivity(Intent(this, OnBoardActivity::class.java).putExtra("goMain", false))
        }

        contactLayout1.setOnClickListener {
            val email = Intent(Intent.ACTION_SEND)
            email.type = "plain/text"
            val address = arrayOf("ahnseungkl@gmail.com")
            email.putExtra(Intent.EXTRA_EMAIL, address)
            email.putExtra(
                Intent.EXTRA_SUBJECT,
                "[나만의 메시지]\n Device : Android\n Version : ${BuildConfig.VERSION_CODE}\n"
            )
            startActivity(email)
        }

        donationLayout.setOnClickListener {
//            bp!!.purchase(this, "donation")
            Toast.makeText(this,"준비중 입니다!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onProductPurchased(productId: String, details: TransactionDetails?) {
        if (productId == "donation") {
            bp!!.isPurchased("donation")
            Toasty.success(this, "감사합니다!", Toast.LENGTH_SHORT, true).show()
            bp!!.consumePurchase("donation")
        }
    }

    override fun onPurchaseHistoryRestored() {
    }

    override fun onBillingError(errorCode: Int, error: Throwable?) {
        if (errorCode != Constants.BILLING_RESPONSE_RESULT_USER_CANCELED) {
            Toasty.info(this, "Billing Error.", Toast.LENGTH_SHORT, true).show()
        }
    }

    override fun onBillingInitialized() {

    }
}