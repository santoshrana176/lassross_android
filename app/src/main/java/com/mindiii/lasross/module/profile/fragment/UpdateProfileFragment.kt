package com.mindiii.lasross.module.profile.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.BaseKotlinFragment
import com.mindiii.lasross.helper.Constant
import com.mindiii.lasross.module.address.activity.MyAddressesActivity
import com.mindiii.lasross.module.loginregistration.model.LoginResponse
import com.mindiii.lasross.module.profile.ProfileActivity
import com.mindiii.lasross.module.profile.presenter.UpdateProfilePresenter
import com.mindiii.lasross.picker.ImagePicker
import com.mindiii.lasross.picker.ImageRotator
import com.mindiii.lasross.utils.CommonUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_update.*
import java.io.IOException


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"

class UpdateProfileFragment : BaseKotlinFragment(), ApiCallback.UpdateProfileCallback, View.OnClickListener {

    private var param1: String? = null
    private var from: String? = null
    private var imageUri: Uri? = null
    private lateinit var address: String
    lateinit var session: Session
    private lateinit var profileImageBitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        session = Session(mContext)
        setOnClicks(tvUpdate, edit_pencil, etAddress, btnBackToProfile)
        setData()
    }

    private fun setData() {
        val session = Session(mContext).registration
//        imageUri = Uri.parse(session.profile_photo)
        etName.setText(session.full_name)

        if (session.profile_photo.isNotEmpty())
            Picasso.with(mContext).load(session.profile_photo).into(ivProfileImage)
        else
            Picasso.with(mContext).load(R.drawable.placeholder_image).into(ivProfileImage)
    }

    private fun apiCalling() {
        if (imageUri == null) {
            mContext?.let {
                UpdateProfilePresenter(this, it).callUpdateProfileApi(etName.text.toString(),
                        "", "", etAddress.text.toString(), "", "", "")
            }
        } else {
            mContext?.let {
                UpdateProfilePresenter(this, it).callUpdateProfileApi(etName.text.toString(),
                        imageUri.toString(), "", etAddress.text.toString(), "", "", "")
            }
        }
    }

    private fun setOnClicks(vararg views: View) {
        for (view in views) view.setOnClickListener(this)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                UpdateProfileFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                    }
                }
    }


    override fun onSuccessUpdateProfile(loginResponse: LoginResponse?) {
        startActivity(Intent(requireContext(), ProfileActivity::class.java))
        activity.finish()
        //activity.showOnBackPressed(requireContext(), loginResponse!!.message)
        Toast.makeText(mContext, loginResponse?.message, Toast.LENGTH_SHORT).show()
        if (loginResponse!!.status.equals("success", ignoreCase = true)) {
            session.createRegistration(loginResponse.data?.userDetail)
            session.setUserLoggedIn()
            //  session.authToken=loginResponse.data?.userDetail!!.auth_token
        }
    }

    override fun onHideBaseLoader() {
        activity.hideLoader()
    }

    override fun onTokenChangeError(errorMessage: String?) {
        mContext?.let { activity.showDialog(it) }
    }

    override fun onShowBaseLoader() {
        activity.showLoader()
    }

    override fun onError(errorMessage: String?) {
        errorMessage?.let { activity.toastMessage(it) }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tvUpdate -> {
                if (etName.text.toString().trim().isNotEmpty())
                    apiCalling()
                else
                    CommonUtils.showCustomAlert(activity, getString(R.string.Please_full_name))

            }
            R.id.edit_pencil -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (mContext?.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), Constant.MY_PERMISSIONS_REQUEST_CAMERA)
                    } else {
                        ImagePicker.pickImage(getActivity())
                    }
                } else {
                    ImagePicker.pickImage(getActivity())
                }
            }
            R.id.etAddress -> {
                startActivity(Intent(mContext, MyAddressesActivity::class.java)
                        .putExtra("CheckValue", "CheckFound"))
            }
            R.id.btnBackToProfile -> {
                activity.onBackPressed()
            }

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == 234) {    // Image Picker
                imageUri = ImagePicker.getImageURIFromResult(mContext, requestCode, resultCode, data)
                try {
                    val tempBitmap = MediaStore.Images.Media.getBitmap(getActivity()!!.contentResolver, imageUri)
                    val orientation = ImageRotator.getRotation(mContext, imageUri, true)
                    profileImageBitmap = ImageRotator.rotate(tempBitmap, orientation)

                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (error: OutOfMemoryError) {
                    activity.toastMessage(resources.getString(R.string.alertOutOfMemory))
                }
                Glide.with(this).load(profileImageBitmap).apply(RequestOptions().placeholder(mContext?.let { ContextCompat.getDrawable(it, R.drawable.default_profile_img) })).into(ivProfileImage)
            }
        }
    }
}
