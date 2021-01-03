package com.e.kakaocustommessage.CreateText

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.e.kakaocustommessage.R
import kotlinx.android.synthetic.main.fragment_text_preview.*


class TextPreviewFragment : Fragment() {

    lateinit var mView : View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_text_preview, container, false)
        return mView
    }

    override fun onStart() {
        super.onStart()

        if((activity as CreateTextActivity).title.length==0) textPreviewText.visibility = View.GONE
        textPreviewText.text = (activity as CreateTextActivity).title
        if((activity as CreateTextActivity).text.length==0) textPreviewTextsub.visibility = View.GONE
        textPreviewTextsub.text = (activity as CreateTextActivity).text
        if((activity as CreateTextActivity).imageBitmap!=null)
            textPreviewImageVIew.setImageBitmap((activity as CreateTextActivity).imageBitmap)
        if((activity as CreateTextActivity).imageURL!=null)
            textPreviewImageVIew.setImageURI((activity as CreateTextActivity).imageURL)


        if((activity as CreateTextActivity).button1Checked) {
            button1.visibility = View.VISIBLE
            button1.text = (activity as CreateTextActivity).button1link
            if((activity as CreateTextActivity).button2Checked) {
                button2.visibility = View.VISIBLE
                button2.text = (activity as CreateTextActivity).button2link
            }
            else button2.visibility = View.GONE
        }
        else {
            button1.visibility = View.GONE
            button2.visibility = View.GONE
        }
    }

}