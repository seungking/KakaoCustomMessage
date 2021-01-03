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

    val REQUEST_IMAGE_CAPTURE = 1
    val GET_GALLERY_IMAGE = 200
    var mview: View? = null

    //퍼미션 응답 처리 코드
    private val multiplePermissionsCode = 100

    //필요한 퍼미션 리스트
//원하는 퍼미션을 이곳에 추가하면 된다.
    private val requiredPermissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mview = inflater.inflate(R.layout.fragment_text_param, container, false)

        checkPermissions()

        val photoImg = mview?.findViewById<ImageView>(R.id.photoImg)
        mview?.findViewById<ConstraintLayout>(R.id.sectionBodyBody1)?.visibility = View.GONE

        val photoImgRemoveBtn = mview?.findViewById<Button>(R.id.photoImgRemoveBtn)
        photoImgRemoveBtn?.setOnClickListener {
            (activity as CreateTextActivity).imageBitmap = null
            (activity as CreateTextActivity).imageURL = null

            photoImg?.visibility = View.GONE
            photoImgRemoveBtn.visibility = View.GONE
        }

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
        txtEditTitleBody?.addTextChangedListener {
            (activity as CreateTextActivity).title = txtEditTitleBody.text.toString()
        }
        val txtEditTextBody = mview?.findViewById<EditText>(R.id.txtEditTextBody)
        txtEditTextBody?.addTextChangedListener {
            (activity as CreateTextActivity).text = txtEditTextBody.text.toString()
        }

        val txtEditTextBodyBodyLink = mview?.findViewById<EditText>(R.id.txtEditTextBodyBodyLink)
        txtEditTextBodyBodyLink?.addTextChangedListener {
            (activity as CreateTextActivity).button1linkLink =
                txtEditTextBodyBodyLink.text.toString()
        }
        val txtEditTextBodyBodyLink1 = mview?.findViewById<EditText>(R.id.txtEditTextBodyBodyLink1)
        txtEditTextBodyBodyLink1?.addTextChangedListener {
            (activity as CreateTextActivity).button2linkLink =
                txtEditTextBodyBodyLink1.text.toString()
        }
        val txtEditTextBodyBody = mview?.findViewById<EditText>(R.id.txtEditTextBodyBody)
        txtEditTextBodyBody?.addTextChangedListener {
            (activity as CreateTextActivity).button1link = txtEditTextBodyBody.text.toString()
        }
        val txtEditTextBodyBody1 = mview?.findViewById<EditText>(R.id.txtEditTextBodyBody1)
        txtEditTextBodyBody1?.addTextChangedListener {
            (activity as CreateTextActivity).button2link = txtEditTextBodyBody1.text.toString()
        }

        val checkboxButton1 = mview?.findViewById<CheckBox>(R.id.checkboxButton1)
        checkboxButton1?.setOnClickListener {
            (activity as CreateTextActivity).button1Checked = checkboxButton1!!.isChecked

            if (checkboxButton1.isChecked) {

                sectionBodyBody1.visibility = View.VISIBLE

                txtEditTextBodyBody!!.isClickable = true
                txtEditTextBodyBodyLink!!.isClickable = true

                txtEditTextBodyBody!!.isFocusableInTouchMode = true
                txtEditTextBodyBodyLink!!.isFocusableInTouchMode = true

                txtEditTextBodyBody!!.background = requireContext().getDrawable(R.drawable.cornerswhite)
                txtEditTextBodyBodyLink!!.background = requireContext().getDrawable(R.drawable.cornerswhite)

//                Log.d("logggg", "button1 clicked + ${txtEditTextBodyBody.isFocusable}  ${txtEditTextBodyBodyLink.isClickable}")
            } else {

                sectionBodyBody1.visibility = View.GONE

                txtEditTextBodyBody!!.isClickable = false
                txtEditTextBodyBodyLink!!.isClickable = false

                txtEditTextBodyBody!!.isFocusableInTouchMode = false
                txtEditTextBodyBodyLink!!.isFocusableInTouchMode = false

                txtEditTextBodyBody!!.background = requireContext().getDrawable(R.drawable.cornerclickablefalse)
                txtEditTextBodyBodyLink!!.background = requireContext().getDrawable(R.drawable.cornerclickablefalse)

//                Log.d("logggg", "button1 unclicked + ${txtEditTextBodyBody.isFocusable}  ${txtEditTextBodyBodyLink.isClickable}")
            }
        }
        val checkboxButton11 = mview?.findViewById<CheckBox>(R.id.checkboxButton11)
        checkboxButton11?.setOnClickListener {
            (activity as CreateTextActivity).button2Checked = checkboxButton11.isChecked

            if (!checkboxButton11.isChecked) {

                txtEditTextBodyBody1!!.isClickable = false
                txtEditTextBodyBodyLink1!!.isClickable = false

                txtEditTextBodyBody1!!.isFocusableInTouchMode = false
                txtEditTextBodyBodyLink1!!.isFocusableInTouchMode = false

                txtEditTextBodyBody1!!.background = requireContext().getDrawable(R.drawable.cornerclickablefalse)
                txtEditTextBodyBodyLink1!!.background = requireContext().getDrawable(R.drawable.cornerclickablefalse)

                Log.d("logggg", "button2 unclicked + ${txtEditTextBodyBody1.isFocusable}  ${txtEditTextBodyBodyLink1.isClickable}")
            } else {

                txtEditTextBodyBody1!!.isClickable = true
                txtEditTextBodyBodyLink1!!.isClickable = true

                txtEditTextBodyBody1!!.isFocusableInTouchMode = true
                txtEditTextBodyBodyLink1!!.isFocusableInTouchMode = true

                txtEditTextBodyBody1!!.background = requireContext().getDrawable(R.drawable.cornerswhite)
                txtEditTextBodyBodyLink1!!.background = requireContext().getDrawable(R.drawable.cornerswhite)

                Log.d("logggg", "button2 clicked + ${txtEditTextBodyBody1.isFocusable}  ${txtEditTextBodyBodyLink1.isClickable}")
            }

        }


        return mview
    }


    //퍼미션 체크 및 권한 요청 함수
    private fun checkPermissions() {
        //거절되었거나 아직 수락하지 않은 권한(퍼미션)을 저장할 문자열 배열 리스트
        var rejectedPermissionList = ArrayList<String>()

        //필요한 퍼미션들을 하나씩 끄집어내서 현재 권한을 받았는지 체크
        for(permission in requiredPermissions){
            if(ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                //만약 권한이 없다면 rejectedPermissionList에 추가
                rejectedPermissionList.add(permission)
            }
        }
        //거절된 퍼미션이 있다면...
        if(rejectedPermissionList.isNotEmpty()){
            //권한 요청!
            val array = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(requireActivity(), rejectedPermissionList.toArray(array), multiplePermissionsCode)
        }
    }

    //권한 요청 결과 함수
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            multiplePermissionsCode -> {
                if(grantResults.isNotEmpty()) {
                    for((i, permission) in permissions.withIndex()) {
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            //권한 획득 실패
//                            Toasty.info(requireContext(), "권한 설정 후 카메라 이용이 가능합니다.\n 재 실행 후 권한을 설정해 주세요.").show()
                        }
                    }
                }
            }
        }
    }
    // 카메라 원본이미지 Uri를 저장할 변
    var photoURI: Uri? = null

    private fun dispatchTakePictureIntent() {

        checkPermissions()

        // 카메라 인텐트 생성
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        createImageUri("aa", "image/jpg")?.let { uri ->
            photoURI = uri
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    fun loadBitmapFromMediaStoreBy(photoUri: Uri): Bitmap? {
        var image: Bitmap? = null
        try {
            image = if (Build.VERSION.SDK_INT > 27) { // Api 버전별 이미지 처리
                val source: ImageDecoder.Source =
                    ImageDecoder.createSource(requireContext().contentResolver, photoUri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, photoUri)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return image
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (photoURI != null) {
                val imageBitmap = loadBitmapFromMediaStoreBy(photoURI!!)

                (activity as CreateTextActivity).imageBitmap = imageBitmap
                (activity as CreateTextActivity).imageURL = null

                photoImg?.visibility = View.VISIBLE
                photoImgRemoveBtn?.visibility = View.VISIBLE
                photoImg?.setImageBitmap(imageBitmap)

                photoURI = null // 사용 후 null 처리
            }
        }

        if (requestCode === GET_GALLERY_IMAGE && resultCode === RESULT_OK && attr.data != null && data != null) {
            val selectedImageUri: Uri? = data?.data

            (activity as CreateTextActivity).imageBitmap = null
            (activity as CreateTextActivity).imageURL = selectedImageUri

            photoImg?.visibility = View.VISIBLE
            photoImgRemoveBtn?.visibility = View.VISIBLE
            photoImg?.setImageURI(selectedImageUri)
        }
    }

    fun createImageUri(filename: String, mimeType: String): Uri? {
        var values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        return requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }

}