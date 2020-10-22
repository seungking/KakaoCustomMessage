package com.e.kakaocustommessage.CreateFeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.e.kakaocustommessage.R
import kotlinx.android.synthetic.main.activity_create_text.*

class CreateFeedActivity : AppCompatActivity() {
    var fragmentManager: FragmentManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_feed)

        fragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.createActivityFrame, FeedParamFragment(),
            FeedParamFragment::class.java.simpleName).commit()

        toParamFragment.setOnClickListener {
            toParamFragment.setTextColor(ContextCompat.getColor(this,R.color.kakaoSelect))
            toPreviewFragment.setTextColor(ContextCompat.getColor(this,R.color.kakaoUnSelect))

            val fragment = fragmentManager!!.findFragmentByTag(FeedParamFragment::class.java.getSimpleName())
            if (fragment != null) {
                fragmentManager!!.beginTransaction().hide(fragmentManager!!.findFragmentByTag(FeedPreviewFragment::class.java.getSimpleName())!!).commit()
                fragmentManager!!.beginTransaction().show(fragmentManager!!.findFragmentByTag(FeedParamFragment::class.java.getSimpleName())!!).commit()
            }
        }

        toPreviewFragment.setOnClickListener {
            toParamFragment.setTextColor(ContextCompat.getColor(this,R.color.kakaoUnSelect))
            toPreviewFragment.setTextColor(ContextCompat.getColor(this,R.color.kakaoSelect))

            val fragment = fragmentManager!!.findFragmentByTag(FeedPreviewFragment::class.java.getSimpleName())
            fragmentManager!!.beginTransaction().hide(fragmentManager!!.findFragmentByTag(FeedParamFragment::class.java.getSimpleName())!!).commit()

            if (fragment != null)
                fragmentManager!!.beginTransaction().show(fragmentManager!!.findFragmentByTag(FeedPreviewFragment::class.java.getSimpleName())!!).commit()
            else
                fragmentManager!!.beginTransaction().add(R.id.createActivityFrame, FeedPreviewFragment(), FeedPreviewFragment::class.java.simpleName).commit()
        }
    }
}