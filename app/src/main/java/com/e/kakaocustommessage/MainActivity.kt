package com.e.kakaocustommessage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.e.kakaocustommessage.CreateText.CreateTextActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txt1.setOnClickListener{
            startActivity(Intent(this, SelectActivity::class.java))
        }
    }
}