package com.e.kakaocustommessage.CreateFeed

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.e.kakaocustommessage.CreateLocation.CreateLocationActivity
import com.e.kakaocustommessage.R
import kotlinx.android.synthetic.main.fragment_feed_preview.*
import kotlinx.android.synthetic.main.fragment_text_preview.*
import kotlinx.android.synthetic.main.fragment_text_preview.button1
import kotlinx.android.synthetic.main.fragment_text_preview.textPreviewText


class LocationPreviewFragment : Fragment() {

    lateinit var mView : View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_location_preview, container, false)
        return mView
    }

    override fun onStart() {
        super.onStart()

        if((activity as CreateLocationActivity).title.length==0) textPreviewText.visibility = View.GONE
        textPreviewText.text = (activity as CreateLocationActivity).title
        if((activity as CreateLocationActivity).text.length==0) textPreviewTextsub.visibility = View.GONE
        textPreviewTextsub.text = (activity as CreateLocationActivity).text
        if((activity as CreateLocationActivity).imageBitmap!=null)
            textPreviewImageVIew.setImageBitmap((activity as CreateLocationActivity).imageBitmap)
        if((activity as CreateLocationActivity).imageURL!=null)
            textPreviewImageVIew.setImageURI((activity as CreateLocationActivity).imageURL)


        if((activity as CreateLocationActivity).button1Checked) {
            button1.visibility = View.VISIBLE
        }
        else {
            button1.visibility = View.GONE
        }
    }

}