package com.e.kakaocustommessage.CreateText

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.e.kakaocustommessage.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.kakao.sdk.talk.TalkApiClient
import com.kakao.sdk.template.model.*
import com.rengwuxian.materialedittext.MaterialEditText
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_create_text.*


class CreateTextActivity : AppCompatActivity() {

    var fragmentManager: FragmentManager? = null
    var text : String = ""
    var button1Checked : Boolean = false
    var imageBitmap : Bitmap? = null
    var imageURL : Uri? = null
    lateinit var mAdView : AdView

    var mInflater : LayoutInflater? = null
    var rootView : ConstraintLayout? = null
    var screenLoading : View? = null

    var contentMaterialEditText : MaterialEditText? = null
    var buttonMaterialEditText : MaterialEditText? = null
    var buttonLinkMaterialEditText : MaterialEditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_text)

        mInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        rootView = findViewById(R.id.rootView)
        screenLoading = mInflater!!.inflate(R.layout.layout_loading, null)

        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        fragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(
            R.id.createActivityFrame,
            TextParamFragment(),
            TextParamFragment::class.java.simpleName
        ).commit()

        createBackBtn.setOnClickListener {
            finish()
        }

        sendBtn.setOnClickListener{

            rootView!!.addView(screenLoading)

            if(button1Checked) sendMessageWithButton()
            else sendMessage()
        }

        toParamFragment.setOnClickListener {
            toParamFragment.setTextColor(ContextCompat.getColor(this, R.color.kakaoSelect))
            toPreviewFragment.setTextColor(ContextCompat.getColor(this, R.color.kakaoUnSelect))

            val fragment = fragmentManager!!.findFragmentByTag(TextParamFragment::class.java.getSimpleName())
            if (fragment != null) {
                fragmentManager!!.beginTransaction().remove(
                    fragmentManager!!.findFragmentByTag(
                        TextPreviewFragment::class.java.getSimpleName()
                    )!!
                ).commit()
                fragmentManager!!.beginTransaction().show(
                    fragmentManager!!.findFragmentByTag(
                        TextParamFragment::class.java.getSimpleName()
                    )!!
                ).commit()
            }
        }

        toPreviewFragment.setOnClickListener {
            toParamFragment.setTextColor(ContextCompat.getColor(this, R.color.kakaoUnSelect))
            toPreviewFragment.setTextColor(ContextCompat.getColor(this, R.color.kakaoSelect))

            val fragment = fragmentManager!!.findFragmentByTag(TextPreviewFragment::class.java.getSimpleName())
            fragmentManager!!.beginTransaction().hide(
                fragmentManager!!.findFragmentByTag(
                    TextParamFragment::class.java.getSimpleName()
                )!!
            ).commit()
            if (fragment != null)
                fragmentManager!!.beginTransaction().show(
                    fragmentManager!!.findFragmentByTag(
                        TextPreviewFragment::class.java.getSimpleName()
                    )!!
                ).commit()
             else
                fragmentManager!!.beginTransaction().add(
                    R.id.createActivityFrame,
                    TextPreviewFragment(),
                    TextPreviewFragment::class.java.simpleName
                ).commit()
        }


    }

    fun sendMessage(){

        ///https://play.google.com/store/apps/details?id=com.e.namematching
        val defaultText = TextTemplate(
            text = contentMaterialEditText!!.text.toString(),
            link = Link(
                webUrl = "https://play.google.com/store/apps/details?id=com.e.namematching",
                mobileWebUrl = "https://play.google.com/store/apps/details?id=com.e.namematching"
            ),
            buttons = listOf(
                Button(
                    "",
                    Link(
                        webUrl = "",
                        mobileWebUrl = ""
                    )
                )
            )
        )

        TalkApiClient.instance.sendDefaultMemo(defaultText) { error ->
            rootView!!.removeView(screenLoading)
            if (error != null) {
                Log.e("log1", "나에게 보내기 실패", error)
                Toasty.error(this, "나에게 보내기 실패").show()
            } else {
                Log.i("log1", "나에게 보내기 성공")
                Toasty.success(this, "나에게 보내기 성공").show()
            }
        }
    }

    fun sendMessageWithButton(){

        val defaultText = TextTemplate(
            text = contentMaterialEditText!!.text.toString(),
            link = Link(
                webUrl = "https://play.google.com/store/apps/details?id=com.e.namematching",
                mobileWebUrl = "https://play.google.com/store/apps/details?id=com.e.namematching"
            ),
            buttons = listOf(
                Button(
                    buttonMaterialEditText?.text!!.toString(),
                    Link(
                        webUrl = "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=${buttonLinkMaterialEditText?.text!!.toString()}",
                        mobileWebUrl = "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=${buttonLinkMaterialEditText?.text!!.toString()}"
                    )
                )
            )
        )

        TalkApiClient.instance.sendDefaultMemo(defaultText) { error ->
            rootView!!.removeView(screenLoading)
            if (error != null) {
                Log.e("log1", "나에게 보내기 실패", error)
            } else {
                Log.i("log1", "나에게 보내기 성공")
            }
        }
    }

}