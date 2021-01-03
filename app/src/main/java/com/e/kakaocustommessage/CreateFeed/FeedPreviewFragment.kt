package com.e.kakaocustommessage.CreateFeed

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.e.kakaocustommessage.R
import kotlinx.android.synthetic.main.fragment_text_preview.*


class FeedPreviewFragment : Fragment() {

    lateinit var mView : View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_feed_preview, container, false)
        return mView
    }

    override fun onStart() {
        super.onStart()

        if((activity as CreateFeedActivity).title.length==0) textPreviewText.visibility = View.GONE
        textPreviewText.text = (activity as CreateFeedActivity).title
        if((activity as CreateFeedActivity).text.length==0) textPreviewTextsub.visibility = View.GONE
        textPreviewTextsub.text = (activity as CreateFeedActivity).text
        if((activity as CreateFeedActivity).imageBitmap!=null)
            textPreviewImageVIew.setImageBitmap((activity as CreateFeedActivity).imageBitmap)
        if((activity as CreateFeedActivity).imageURL!=null)
            textPreviewImageVIew.setImageURI((activity as CreateFeedActivity).imageURL)


        if((activity as CreateFeedActivity).button1Checked) {
            button1.visibility = View.VISIBLE
            button1.text = (activity as CreateFeedActivity).button1link
            if((activity as CreateFeedActivity).button2Checked) {
                button2.visibility = View.VISIBLE
                button2.text = (activity as CreateFeedActivity).button2link
            }
            else button2.visibility = View.GONE
        }
        else {
            button1.visibility = View.GONE
            button2.visibility = View.GONE
        }
    }

}