package com.e.kakaocustommessage.CreateText

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.e.kakaocustommessage.MainActivity
import com.e.kakaocustommessage.R
import com.e.kakaocustommessage.SelectActivity
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_text_param.*
import kotlinx.android.synthetic.main.fragment_text_preview.*


class TextParamFragment : Fragment() {

    var mview : View? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mview = inflater.inflate(R.layout.fragment_text_param, container, false)

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
                sectionBodyBody1.visibility = View.INVISIBLE
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
}