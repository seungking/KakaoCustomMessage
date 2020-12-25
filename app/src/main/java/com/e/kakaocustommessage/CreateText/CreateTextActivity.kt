package com.e.kakaocustommessage.CreateText

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.e.kakaocustommessage.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.kakao.sdk.talk.TalkApiClient
import com.kakao.sdk.template.model.*
import kotlinx.android.synthetic.main.activity_create_text.*
import java.io.ByteArrayOutputStream


class CreateTextActivity : AppCompatActivity() {
    var fragmentManager: FragmentManager? = null

    var title : String = ""
    var text : String = ""
    var button1Checked : Boolean = false
    var button2Checked : Boolean = false
    var button1link : String = ""
    var button2link : String = ""
    var button1linkLink : String = ""
    var button2linkLink : String = ""
    var imageBitmap : Bitmap? = null
    var imageURL : Uri? = null
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_text)

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

        val defaultLocation = LocationTemplate(
            address = "경기 성남시 분당구 판교역로 235 에이치스퀘어 N동 8층",
            addressTitle = "카카오 판교오피스 카페톡",
            content = Content(
                title = "신메뉴 출시❤️ 체리블라썸라떼",
                description = "이번 주는 체리블라썸라떼 1+1",
                imageUrl = "http://mud-kage.kakao.co.kr/dn/bSbH9w/btqgegaEDfW/vD9KKV0hEintg6bZT4v4WK/kakaolink40_original.png",
                link = Link(
                )
            )
        )

        sendBtn.setOnClickListener{

            val defaultText = TextTemplate(
                text = text,
                link = Link(
                    webUrl = "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=$button1link",
                    mobileWebUrl = "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=$button1link"
                )
            )

            var stringUri = ""
            if(imageBitmap!=null) stringUri = getImageUri(this, imageBitmap!!).toString()
            else if(imageURL!=null) stringUri = imageURL.toString()

            val defaultFeed = FeedTemplate(
                content = Content(
                    title = title,
                    description = text,
                    imageUrl = stringUri,
                    link = Link(
                    )
                ),
                buttons = listOf(
                    Button(
                        button1link,
                        Link(
                            webUrl = "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=$button1linkLink",
                            mobileWebUrl = "https://m.search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=$button1linkLink"
                        )
                    ),
                    Button(
                        button2link,
                        Link(
                            webUrl = "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=$button2linkLink",
                            mobileWebUrl = "https://m.search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=$button2linkLink"
                        )
                    )
                )
            )


            TalkApiClient.instance.sendDefaultMemo(defaultFeed) { error ->
                if (error != null) {
                    Log.e("log1", "나에게 보내기 실패", error)
                } else {
                    Log.i("log1", "나에게 보내기 성공")
                }
            }
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

    private fun getImageUri(context: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            context.getContentResolver(),
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }
}