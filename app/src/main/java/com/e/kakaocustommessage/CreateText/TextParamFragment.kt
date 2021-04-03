package com.e.kakaocustommessage.CreateText

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.e.kakaocustommessage.R
import com.rengwuxian.materialedittext.MaterialEditText


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

        (activity as CreateTextActivity).contentMaterialEditText = mview?.findViewById<MaterialEditText>(R.id.contentMaterialEditText)
        (activity as CreateTextActivity).buttonMaterialEditText = mview?.findViewById<MaterialEditText>(R.id.buttonMaterialEditText)
        (activity as CreateTextActivity).buttonLinkMaterialEditText = mview?.findViewById<MaterialEditText>(R.id.buttonLinkMaterialEditText)

        val checkboxButton1 = mview?.findViewById<CheckBox>(R.id.checkboxButton1)
        checkboxButton1?.setOnClickListener {
            (activity as CreateTextActivity).button1Checked = checkboxButton1!!.isChecked

            if (checkboxButton1.isChecked) {
                mview?.findViewById<MaterialEditText>(R.id.buttonMaterialEditText)!!.isClickable = true
                mview?.findViewById<MaterialEditText>(R.id.buttonLinkMaterialEditText)!!.isFocusableInTouchMode = true
            } else {
                mview?.findViewById<MaterialEditText>(R.id.buttonMaterialEditText)!!.isClickable = true
                mview?.findViewById<MaterialEditText>(R.id.buttonLinkMaterialEditText)!!.isFocusableInTouchMode = true
            }
        }

        return mview
    }


}