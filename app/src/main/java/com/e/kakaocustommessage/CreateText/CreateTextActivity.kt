package com.e.kakaocustommessage.CreateText

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.e.kakaocustommessage.Helper
import com.e.kakaocustommessage.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.kakao.sdk.talk.TalkApiClient
import com.kakao.sdk.template.model.*
import kotlinx.android.synthetic.main.activity_create_text.*
import java.io.ByteArrayOutputStream


class CreateTextActivity : AppCompatActivity() {

    var fragmentManager: FragmentManager? = null

    var title : String = ""
    var text : String = ""
    var button1Checked : Boolean = false
    var button1link : String = ""
    var button1linkLink : String = ""
    var imageBitmap : Bitmap? = null
    var imageURL : Uri? = null
    lateinit var mAdView : AdView

    var stringUri : String = ""

    var mInflater : LayoutInflater? = null
    var rootView : ConstraintLayout? = null
    var screenLoading : View? = null

    var tempKey = Helper().uniqueID

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
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

        val defaultText = TextTemplate(
            text = title,
            link = Link()
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

    fun sendMessageWithButton(){

        val defaultText = TextTemplate(
            text = title,
            link = Link(
                webUrl = "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=$button1link",
                mobileWebUrl = "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=$button1link"
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