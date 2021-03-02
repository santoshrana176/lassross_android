package com.mindiii.lasross.module.profile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.mindiii.lasross.R
import com.mindiii.lasross.base.LasrossParentKotlinActivity
import com.mindiii.lasross.module.home.HomeActivity
import com.mindiii.lasross.module.profile.fragment.ProfileFragment
import com.mindiii.lasross.module.profile.fragment.UpdateProfileFragment
import com.mindiii.lasross.module.settings.SettingActivity

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
            from = intent.getStringExtra("from")!!
        }
        if (from.isNotEmpty() && from.equals("Settings")) {
            addFragment(UpdateProfileFragment.newInstance(""), true, R.id.profileFrame)
        } else {
            addFragment(ProfileFragment.newInstance(), true, R.id.profileFrame)
        }
    }

    override fun onPostResume() {
        super.onPostResume()
        val fragment = supportFragmentManager.findFragmentById(R.id.profileFrame)
        if (SettingActivity.isScreenRefres) {
            SettingActivity.isScreenRefres = false
            if (fragment is ProfileFragment) {
                supportFragmentManager.beginTransaction().detach(fragment).attach(fragment).commit()
            }
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

    override fun onBackPressed() {
        //  super.onBackPressed()
        val fragment = supportFragmentManager.findFragmentById(R.id.profileFrame)
        val count = supportFragmentManager.backStackEntryCount

        if (fragment is ProfileFragment) {
            startActivity(Intent(this, HomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            finish()
        }
        if (fragment is UpdateProfileFragment) {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStackImmediate()
                if (from.equals("Settings"))
                    super.onBackPressed()
            }
        }
    }

}
