package com.e.kakaocustommessage.CreateText

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.view.menu.MenuBuilder
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.e.kakaocustommessage.R
import com.github.zawadz88.materialpopupmenu.popupMenu
import com.rengwuxian.materialedittext.MaterialEditText
import kotlin.text.Typography.section

class TextParamFragment : Fragment() {

    var mview: View? = null


    @SuppressLint("RestrictedApi")
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

        (activity as CreateTextActivity).contentMaterialEditText = mview?.findViewById<MaterialEditText>(
            R.id.contentMaterialEditText
        )
        (activity as CreateTextActivity).buttonMaterialEditText = mview?.findViewById<MaterialEditText>(
            R.id.buttonMaterialEditText
        )
        (activity as CreateTextActivity).buttonLinkMaterialEditText = mview?.findViewById<MaterialEditText>(
            R.id.buttonLinkMaterialEditText
        )

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


        val btn1linkSite = mview?.findViewById<TextView>(R.id.btn1LinkSite)
        btn1linkSite?.setOnClickListener {

            val popupMenu = popupMenu {
                section {
                    item {
                        label = "네이버"
                        icon = R.drawable.abc_ic_menu_copy_mtrl_am_alpha //optional
                        callback = { //optional
                            btn1linkSite.text = "네이버"
                            (activity as CreateTextActivity).btn1linkSitetype = 0
                        }
                    }
                    item {
                        label = "구글"
                        iconDrawable = ContextCompat.getDrawable(
                            mview!!.context,
                            R.drawable.abc_ic_menu_paste_mtrl_am_alpha
                        ) //optional
                        callback = { //optional
                            btn1linkSite.text = "구글"
                            (activity as CreateTextActivity).btn1linkSitetype = 1
                        }
                    }
                    item {
                        label = "다음"
                        icon = R.drawable.abc_ic_menu_selectall_mtrl_alpha //optional
                        callback = {
                            btn1linkSite.text = "다음"
                            (activity as CreateTextActivity).btn1linkSitetype = 2
                        }
                    }
                    item {
                        label = "네이버 지도"
                        icon = R.drawable.abc_ic_menu_selectall_mtrl_alpha //optional
                        callback = {
                            btn1linkSite.text = "네이버 지도"
                            (activity as CreateTextActivity).btn1linkSitetype = 3
                        }
                    }
                    item {
                        label = "구글 지도"
                        icon = R.drawable.abc_ic_menu_selectall_mtrl_alpha //optional
                        callback = {
                            btn1linkSite.text = "구글 지도"
                            (activity as CreateTextActivity).btn1linkSitetype = 4
                        }
                    }
                }
            }

            popupMenu.show(mview!!.context, btn1linkSite)
        }

        return mview
    }


}