package com.e.kakaocustommessage.CreateText

import android.R.attr
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.e.kakaocustommessage.R
import kotlinx.android.synthetic.main.fragment_text_param.*


class TextParamFragment : Fragment() {

    val REQUEST_IMAGE_CAPTURE = 1
    val GET_GALLERY_IMAGE = 200
    var mview : View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mview = inflater.inflate(R.layout.fragment_text_param, container, false)

        val photoImg = mview?.findViewById<ImageView>(R.id.photoImg)
        mview?.findViewById<ConstraintLayout>(R.id.sectionBodyBody1)?.visibility = View.GONE

        val selectAlbum = mview?.findViewById<ImageView>(R.id.selectAlbum)
        selectAlbum?.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivityForResult(intent, GET_GALLERY_IMAGE)
        }

        val selectPhoto = mview?.findViewById<ImageView>(R.id.selectCamera)
        selectPhoto?.setOnClickListener {
            dispatchTakePictureIntent()
        }

        val txtEditTitleBody = mview?.findViewById<EditText>(R.id.txtEditTitleBody)
        txtEditTitleBody?.addTextChangedListener{
            (activity as CreateTextActivity).title = txtEditTitleBody.text.toString()
        }
        val txtEditTextBody = mview?.findViewById<EditText>(R.id.txtEditTextBody)
        txtEditTextBody?.addTextChangedListener{
            (activity as CreateTextActivity).text = txtEditTextBody.text.toString()
        }
        val checkboxButton1 = mview?.findViewById<CheckBox>(R.id.checkboxButton1)
        checkboxButton1?.setOnClickListener {
            (activity as CreateTextActivity).button1Checked = checkboxButton1!!.isChecked

            if(checkboxButton1.isChecked){
                sectionBodyBody1.visibility = View.VISIBLE
            }
            else {
                sectionBodyBody1.visibility = View.GONE
            }
        }
        val checkboxButton11 = mview?.findViewById<CheckBox>(R.id.checkboxButton11)
        checkboxButton11?.setOnClickListener {
            (activity as CreateTextActivity).button2Checked = checkboxButton11.isChecked
        }
        val txtEditTextBodyBody = mview?.findViewById<EditText>(R.id.txtEditTextBodyBody)
        txtEditTextBodyBody?.addTextChangedListener {
            (activity as CreateTextActivity).button1link = txtEditTextBodyBody.text.toString()
        }
        val txtEditTextBodyBody1 = mview?.findViewById<EditText>(R.id.txtEditTextBodyBody1)
        txtEditTextBodyBody1?.addTextChangedListener {
            (activity as CreateTextActivity).button2link = txtEditTextBodyBody1.text.toString()
        }

        return mview
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            context?.packageManager?.let {
                takePictureIntent.resolveActivity(it)?.also {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data!!.extras!!.get("data") as Bitmap

            (activity as CreateTextActivity).imageBitmap = imageBitmap
            (activity as CreateTextActivity).imageURL = null

            photoImg?.visibility = View.VISIBLE
            photoImg?.setImageBitmap(imageBitmap)
        }

        if (requestCode === GET_GALLERY_IMAGE && resultCode === RESULT_OK && attr.data != null && data!= null) {
            val selectedImageUri: Uri? = data?.data

            (activity as CreateTextActivity).imageBitmap = null
            (activity as CreateTextActivity).imageURL = selectedImageUri

            photoImg?.visibility = View.VISIBLE
            photoImg?.setImageURI(selectedImageUri)
        }
    }
}