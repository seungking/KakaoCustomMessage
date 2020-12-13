package com.e.kakaocustommessage.CreateLocation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.e.kakaocustommessage.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_create_text.*

class CreateLocationActivity : AppCompatActivity() {
    lateinit var mAdView : AdView
    var fragmentManager: FragmentManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_location)

        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        fragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.createActivityFrame, LocationParamFragment(),
            LocationParamFragment::class.java.simpleName).commit()

        toParamFragment.setOnClickListener {
            toParamFragment.setTextColor(ContextCompat.getColor(this,R.color.kakaoSelect))
            toPreviewFragment.setTextColor(ContextCompat.getColor(this,R.color.kakaoUnSelect))

            val fragment = fragmentManager!!.findFragmentByTag(LocationParamFragment::class.java.getSimpleName())

            if (fragment != null) {
                fragmentManager!!.beginTransaction().hide(fragmentManager!!.findFragmentByTag(LocationPreviewFragment::class.java.getSimpleName())!!).commit()
                fragmentManager!!.beginTransaction().show(fragmentManager!!.findFragmentByTag(LocationParamFragment::class.java.getSimpleName())!!).commit()
            }
        }

        toPreviewFragment.setOnClickListener {
            toParamFragment.setTextColor(ContextCompat.getColor(this,R.color.kakaoUnSelect))
            toPreviewFragment.setTextColor(ContextCompat.getColor(this,R.color.kakaoSelect))

            val fragment = fragmentManager!!.findFragmentByTag(LocationPreviewFragment::class.java.getSimpleName())
            fragmentManager!!.beginTransaction().hide(fragmentManager!!.findFragmentByTag(LocationParamFragment::class.java.getSimpleName())!!).commit()

            if (fragment != null)
                fragmentManager!!.beginTransaction().show(fragmentManager!!.findFragmentByTag(LocationPreviewFragment::class.java.getSimpleName())!!).commit()
            else
                fragmentManager!!.beginTransaction().add(R.id.createActivityFrame, LocationPreviewFragment(), LocationPreviewFragment::class.java.simpleName).commit()
        }
    }
}