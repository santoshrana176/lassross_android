package com.mindiii.lasross.module.contactus

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindiii.lasross.R
import com.mindiii.lasross.base.LasrossParentKotlinActivity
import com.mindiii.lasross.module.contactus.adapter.ContactUsAdapter
import com.mindiii.lasross.module.contactus.model.ContactUsModel
import kotlinx.android.synthetic.main.contact_us_activty_29.*

class ContactUsActivity : LasrossParentKotlinActivity() {

    lateinit private var list: ArrayList<ContactUsModel>
    lateinit private var contactUsAdapter: ContactUsAdapter

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_us_activty_29)

        val window = this.window
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

        contactUsAdapter = ContactUsAdapter(list, this)
        rvContactUs.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvContactUs.adapter = contactUsAdapter
    }
}