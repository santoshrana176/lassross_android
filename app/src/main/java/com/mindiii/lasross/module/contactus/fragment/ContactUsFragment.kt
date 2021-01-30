package com.mindiii.lasross.module.contactus.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindiii.lasross.R
import com.mindiii.lasross.base.BaseKotlinFragment
import com.mindiii.lasross.module.contactus.adapter.ContactUsAdapter
import com.mindiii.lasross.module.contactus.model.ContactUsModel
import kotlinx.android.synthetic.main.contact_us_activty_29.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ContactUsFragment : BaseKotlinFragment(), View.OnClickListener {

    lateinit private var list: ArrayList<ContactUsModel>
    lateinit private var contactUsAdapter: ContactUsAdapter
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contact_us, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val window = getActivity()!!.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = this.resources.getColor(R.color.home_header_bg1)

        list = ArrayList()

        val contactUsModel1 = ContactUsModel()
        contactUsModel1.headerName = "Email"
        contactUsModel1.name = "lasross@gmail.com"
        list.add(contactUsModel1)

        val contactUsModel2 = ContactUsModel()
        contactUsModel2.headerName = "Phone"
        contactUsModel2.name = "(123) 14523 4569"
        list.add(contactUsModel2)

        contactUsAdapter = ContactUsAdapter(list, view.context)
        rvContactUs.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        rvContactUs.adapter = contactUsAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                ContactUsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onClick(p0: View?) {

    }


}