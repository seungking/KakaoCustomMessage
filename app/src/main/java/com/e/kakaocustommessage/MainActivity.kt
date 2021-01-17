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
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi0gSORlN3EIvPBLy6qgsB7eb7XDy8DArzxrOinJobfRF0/dByC/S+yL3k4HjvVcE+DWxJdRBkZfGZB6uOqG9yJ19Yn4VfJV7IK61Fs44TjiTqyD/Ammb8lc9arTjdMbeuR/qdicZes6CZsqC53k4gvEwdmI8QlvtX6ctECXzHDuZX99mwwk7nAuvKxVFpywDQyIIbjI4ME7fyMhwQ+QsZVmez8pt5C72zBB5C5PBI2zc+nvM0CChM+1WGVYwp0ky92ozu706c+oZQG+VOQYScBIjXEWaYNE0ufBTK5gZj+KkZ2GNrr5JpR0t095P0g251N0ZHe8IRys+pXCHC/etpwIDAQAB",
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
            bp!!.purchase(this, "donation")
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