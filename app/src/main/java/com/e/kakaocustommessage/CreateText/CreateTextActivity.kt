package com.e.kakaocustommessage.CreateText

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.airbnb.lottie.parser.ColorParser
import com.e.kakaocustommessage.R
import kotlinx.android.synthetic.main.activity_create_text.*


class CreateTextActivity : AppCompatActivity() {
    var fragmentManager: FragmentManager? = null

    var text : String = ""
    var button1Checked : Boolean = false
    var button2Checked : Boolean = false
    var button1link : String = ""
    var button2link : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_text)

        fragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.createActivityFrame, TextParamFragment(),TextParamFragment::class.java.simpleName).commit()

        toParamFragment.setOnClickListener {
            toParamFragment.setTextColor(ContextCompat.getColor(this,R.color.kakaoSelect))
            toPreviewFragment.setTextColor(ContextCompat.getColor(this,R.color.kakaoUnSelect))

            val fragment = fragmentManager!!.findFragmentByTag(TextParamFragment::class.java.getSimpleName())
            if (fragment != null) {
                fragmentManager!!.beginTransaction().remove(fragmentManager!!.findFragmentByTag(TextPreviewFragment::class.java.getSimpleName())!!).commit()
                fragmentManager!!.beginTransaction().show(fragmentManager!!.findFragmentByTag(TextParamFragment::class.java.getSimpleName())!!).commit()
            }
        }

        toPreviewFragment.setOnClickListener {
            toParamFragment.setTextColor(ContextCompat.getColor(this,R.color.kakaoUnSelect))
            toPreviewFragment.setTextColor(ContextCompat.getColor(this,R.color.kakaoSelect))

            val fragment = fragmentManager!!.findFragmentByTag(TextPreviewFragment::class.java.getSimpleName())
            fragmentManager!!.beginTransaction().hide(fragmentManager!!.findFragmentByTag(TextParamFragment::class.java.getSimpleName())!!).commit()
            if (fragment != null)
                fragmentManager!!.beginTransaction().show(fragmentManager!!.findFragmentByTag(TextPreviewFragment::class.java.getSimpleName())!!).commit()
             else
                fragmentManager!!.beginTransaction().add(R.id.createActivityFrame, TextPreviewFragment(),TextPreviewFragment::class.java.simpleName).commit()
        }
    }
}