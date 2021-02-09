package com.mindiii.lasross.module.profile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.mindiii.lasross.R
import com.mindiii.lasross.base.LasrossParentKotlinActivity
import com.mindiii.lasross.module.profile.fragment.ProfileFragment
import com.mindiii.lasross.module.profile.fragment.UpdateProfileFragment

class ProfileActivity : LasrossParentKotlinActivity() {

    var from: String = ""
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = this.resources.getColor(R.color.home_header_bg1)
        }
        if (intent.getStringExtra("from") != null) {
            from = intent.getStringExtra("from")
        }
        if (from.isNotEmpty() && from.equals("Settings")) {
            addFragment(UpdateProfileFragment.newInstance(""), false, R.id.profileFrame)
        } else {
            addFragment(ProfileFragment.newInstance(), false, R.id.profileFrame)
        }
    }

    override fun onPostResume() {
        super.onPostResume()
        val fragment = supportFragmentManager.findFragmentById(R.id.profileFrame)
        if (fragment is ProfileFragment) {
            val profileFragment = fragment
            profileFragment.apiCalling()
            profileFragment.getAddressListApi()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = supportFragmentManager.findFragmentById(R.id.profileFrame)
        if (fragment is UpdateProfileFragment) {
            val updateProfileFragment = fragment
            updateProfileFragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    /*override fun onBackPressed() {
        super.onBackPressed()
        val fragment = supportFragmentManager.findFragmentById(R.id.profileFrame)
        if (fragment is ProfileFragment) {
            startActivity(Intent(this, HomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            finish()
        }
    }*/
}
