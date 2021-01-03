package com.e.kakaocustommessage.CreateText

import android.Manifest
import android.R.attr
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.browser.customtabs.CustomTabsClient.getPackageName
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.e.kakaocustommessage.R
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_text_param.*
import java.io.File
import java.io.IOException
import java.net.URI
import java.net.URL


class TextParamFragment : Fragment() {

    var mview: View? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mview = inflater.inflate(R.layout.fragment_text_param, container, false)

        val photoImg = mview?.findViewById<ImageView>(R.id.photoImg)
        mview?.findViewById<ConstraintLayout>(R.id.sectionBodyBody1)?.visibility = View.GONE

        val photoImgRemoveBtn = mview?.findViewById<Button>(R.id.photoImgRemoveBtn)
        photoImgRemoveBtn?.setOnClickListener {
            (activity as CreateTextActivity).imageBitmap = null
            (activity as CreateTextActivity).imageURL = null

            photoImg?.visibility = View.GONE
            photoImgRemoveBtn.visibility = View.GONE
        }


        val txtEditTitleBody = mview?.findViewById<EditText>(R.id.txtEditTitleBody)
        txtEditTitleBody?.addTextChangedListener {
            (activity as CreateTextActivity).title = txtEditTitleBody.text.toString()
        }

        val txtEditTextBodyBody = mview?.findViewById<EditText>(R.id.txtEditTextBodyBody)
        txtEditTextBodyBody?.addTextChangedListener {
            (activity as CreateTextActivity).button1link = txtEditTextBodyBody.text.toString()
        }

        val checkboxButton1 = mview?.findViewById<CheckBox>(R.id.checkboxButton1)
        checkboxButton1?.setOnClickListener {
            (activity as CreateTextActivity).button1Checked = checkboxButton1!!.isChecked

            if (checkboxButton1.isChecked) {
                txtEditTextBodyBody!!.isClickable = true
                txtEditTextBodyBody!!.isFocusableInTouchMode = true
                txtEditTextBodyBody!!.background = requireContext().getDrawable(R.drawable.cornerswhite)
            } else {
                txtEditTextBodyBody!!.isClickable = false
                txtEditTextBodyBody!!.isFocusableInTouchMode = false
                txtEditTextBodyBody!!.background = requireContext().getDrawable(R.drawable.cornerclickablefalse)
            }
        }

        return mview
    }


}