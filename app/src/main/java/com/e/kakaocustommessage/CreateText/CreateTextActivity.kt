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
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.kakao.sdk.talk.TalkApiClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Link
import com.kakao.sdk.template.model.TextTemplate
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
    private var mInterstitialAd: InterstitialAd? = null

    var mInflater : LayoutInflater? = null
    var rootView : ConstraintLayout? = null
    var screenLoading : View? = null

    var contentMaterialEditText : MaterialEditText? = null
    var contentMaterialEditTextLink : MaterialEditText? = null
    var buttonMaterialEditText : MaterialEditText? = null
    var buttonLinkMaterialEditText : MaterialEditText? = null

    var btn1linkSitetype : Int = 0
    var msglinkSitetype : Int = 0


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

        //전면광고
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd!!.setAdUnitId("ca-app-pub-1992325656759505/1131222800")
        mInterstitialAd!!.loadAd(AdRequest.Builder().build())
        //

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

        val linkSite : String = when(msglinkSitetype){
            0 -> "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query="
            1 -> "https://www.google.com/search?q="
            2 -> "https://search.daum.net/search?q="
            3 -> "https://map.naver.com/v5/search/"
            4 -> "https://www.google.co.kr/maps/place/"
            else -> "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query="
        }

        var link : String = "https://play.google.com/store/apps/details?id=com.e.namematching"
        if (contentMaterialEditTextLink!!.text!!.length > 0) {
            link = linkSite + contentMaterialEditTextLink!!.text
        }

        val webSite : String = when(btn1linkSitetype){
            0 -> "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query="
            1 -> "https://www.google.com/search?q="
            2 -> "https://search.daum.net/search?q="
            3 -> "https://map.naver.com/v5/search/"
            4 -> "https://www.google.co.kr/maps/place/"
            else -> "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query="
        }

        ///https://play.google.com/store/apps/details?id=com.e.namematching
        val defaultText = TextTemplate(
            text = contentMaterialEditText!!.text.toString(),
            link = Link(
                webUrl = link,
                mobileWebUrl = link
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
                if (mInterstitialAd!!.isLoaded) {
                    mInterstitialAd!!.show()
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.")
                }
                Log.i("log1", "나에게 보내기 성공")
                Toasty.success(this, "나에게 보내기 성공").show()
            }
        }
    }

    fun sendMessageWithButton(){

        val linkSite : String = when(msglinkSitetype){
            0 -> "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query="
            1 -> "https://www.google.com/search?q="
            2 -> "https://search.daum.net/search?q="
            3 -> "https://map.naver.com/v5/search/"
            4 -> "https://www.google.co.kr/maps/place/"
            else -> "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query="
        }

        var link : String = "https://play.google.com/store/apps/details?id=com.e.kakaocustommessage"
        if (contentMaterialEditTextLink!!.text!!.length > 0) {
            link = linkSite + contentMaterialEditTextLink!!.text
        }
        val webSite : String = when(btn1linkSitetype){
            0 -> "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query="
            1 -> "https://www.google.com/search?q="
            2 -> "https://search.daum.net/search?q="
            3 -> "https://map.naver.com/v5/search/"
            4 -> "https://www.google.co.kr/maps/place/"
            else -> "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query="
        }

        val defaultText = TextTemplate(
            text = contentMaterialEditText!!.text.toString(),
            link = Link(
                webUrl = link,
                mobileWebUrl = link
            ),
            buttons = listOf(
                Button(
                    buttonMaterialEditText?.text!!.toString(),
                    Link(
                        webUrl = webSite + buttonLinkMaterialEditText?.text!!.toString(),
                        mobileWebUrl = webSite + buttonLinkMaterialEditText?.text!!.toString()
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
                if (mInterstitialAd!!.isLoaded) {
                    mInterstitialAd!!.show()
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.")
                }
                Log.i("log1", "나에게 보내기 성공")
                Toasty.success(this, "나에게 보내기 성공").show()
            }
        }
    }

}