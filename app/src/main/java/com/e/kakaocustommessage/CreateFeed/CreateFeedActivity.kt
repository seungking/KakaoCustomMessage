package com.e.kakaocustommessage.CreateFeed

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
import com.e.kakaocustommessage.CreateFeed.FeedParamFragment
import com.e.kakaocustommessage.CreateFeed.FeedPreviewFragment
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
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_create_text.*
import java.io.ByteArrayOutputStream


class CreateFeedActivity : AppCompatActivity() {

    // [START storage_field_declaration]
    lateinit var storage: FirebaseStorage

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

    var storageRef : StorageReference? = null
    var imagesRef: StorageReference? = null

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
        setContentView(R.layout.activity_create_feed)

        mInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        rootView = findViewById(R.id.rootView)
        screenLoading = mInflater!!.inflate(R.layout.layout_loading, null)
        screenLoading!!.isClickable = true


        ///////////////////////////////////////////////////////////
        // [START storage_field_initialization]
        storage = Firebase.storage("gs://kakaocustommessage.appspot.com")

        // Create a child reference
        // imagesRef now points to "images"
        // Create a storage reference from our app
        storageRef = storage.reference
        imagesRef = storageRef!!.child( tempKey + "/images")

        ////////////////////////////////////////////////////////////////

        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        fragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(
            R.id.createActivityFrame,
            FeedParamFragment(),
            FeedParamFragment::class.java.simpleName
        ).commit()

        createBackBtn.setOnClickListener {
            finish()
        }

        sendBtn.setOnClickListener{

            rootView!!.addView(screenLoading)

            if(imageBitmap!=null) {

                Log.d("logggg", "from photo imagebitmap")
                val baos = ByteArrayOutputStream()
                imageBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()

                var uploadTask = imagesRef!!.putBytes(data)
                uploadTask.addOnFailureListener {
                    // Handle unsuccessful uploads
                    Log.d("loggg", "image save in firebase : failure")
                }.addOnSuccessListener { taskSnapshot ->

                    Log.d("loggg", "image save in firebase : success")

                    imagesRef!!.downloadUrl
                        .addOnSuccessListener { urlTask ->
                            // download URL is available here
                            stringUri = urlTask.toString()
                            Log.d("loggg", "download url : " + stringUri)
                            sendMessage()
                        }.addOnFailureListener { e ->
                            // Handle any errors
                        }



                }
            } else if (imageURL!=null){

                Log.d("logggg", "from album imageurl")
                val bitmap =
                    MediaStore.Images.Media.getBitmap(getContentResolver(), imageURL)
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()

                var uploadTask = imagesRef!!.putBytes(data)
                uploadTask.addOnFailureListener {
                    // Handle unsuccessful uploads
                    Log.d("loggg", "image save in firebase : failure")
                }.addOnSuccessListener { taskSnapshot ->

                    imagesRef!!.downloadUrl
                        .addOnSuccessListener { urlTask ->
                            // download URL is available here
                            stringUri = urlTask.toString()
                            Log.d("loggg", "download url : " + stringUri)
                            sendMessage()
                        }.addOnFailureListener { e ->
                            // Handle any errors
                        }

                }
            } else {
                sendMessage()
            }

        }

        toParamFragment.setOnClickListener {
            toParamFragment.setTextColor(ContextCompat.getColor(this, R.color.kakaoSelect))
            toPreviewFragment.setTextColor(ContextCompat.getColor(this, R.color.kakaoUnSelect))

            val fragment = fragmentManager!!.findFragmentByTag(FeedParamFragment::class.java.getSimpleName())
            if (fragment != null) {
                fragmentManager!!.beginTransaction().remove(
                    fragmentManager!!.findFragmentByTag(
                        FeedPreviewFragment::class.java.getSimpleName()
                    )!!
                ).commit()
                fragmentManager!!.beginTransaction().show(
                    fragmentManager!!.findFragmentByTag(
                        FeedParamFragment::class.java.getSimpleName()
                    )!!
                ).commit()
            }
        }

        toPreviewFragment.setOnClickListener {
            toParamFragment.setTextColor(ContextCompat.getColor(this, R.color.kakaoUnSelect))
            toPreviewFragment.setTextColor(ContextCompat.getColor(this, R.color.kakaoSelect))

            val fragment = fragmentManager!!.findFragmentByTag(FeedPreviewFragment::class.java.getSimpleName())
            fragmentManager!!.beginTransaction().hide(
                fragmentManager!!.findFragmentByTag(
                    FeedParamFragment::class.java.getSimpleName()
                )!!
            ).commit()
            if (fragment != null)
                fragmentManager!!.beginTransaction().show(
                    fragmentManager!!.findFragmentByTag(
                        FeedPreviewFragment::class.java.getSimpleName()
                    )!!
                ).commit()
            else
                fragmentManager!!.beginTransaction().add(
                    R.id.createActivityFrame,
                    FeedPreviewFragment(),
                    FeedPreviewFragment::class.java.simpleName
                ).commit()
        }


    }


    fun sendMessage(){

        var defaultFeed : FeedTemplate? = null


            Log.d("loggg", "CreateFeed no image")
            defaultFeed = FeedTemplate(
                content = Content(
                    title = title,
                    description = text,
                    imageUrl = stringUri,
                    link = Link(
                        webUrl = "https://play.google.com/store/apps/details?id=com.e.namematching",
                        mobileWebUrl = "https://play.google.com/store/apps/details?id=com.e.namematching"
                    ),
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
            rootView!!.removeView(screenLoading)
            if (error != null) {
                Log.e("log1", "나에게 보내기 실패", error)
                Toasty.error(this, "나에게 보내기 실패").show()
            } else {
                Log.i("log1", "나에게 보내기 성공")
//                imagesRef!!.delete()
                Toasty.success(this, "나에게 보내기 성공").show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("logggg", "destroy")
        imagesRef!!.delete()
    }
}