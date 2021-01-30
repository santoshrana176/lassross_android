package com.mindiii.lasross.base

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import com.mindiii.lasross.helper.Constant
import com.mindiii.lasross.helper.Util
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

open class BaseKotlinFragment : Fragment() {

    var mContext: Context? = null
    lateinit var activity: LasrossParentKotlinActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
        if (context is LasrossParentKotlinActivity) {
            activity = (context as LasrossParentKotlinActivity?)!!
        }
    }

    protected fun numberformate(myNumber: String): String {
        var numericString = Util.removeNonnumeric(myNumber)
        val stringLength = numericString.length
        val startsWithOne = numericString.startsWith("1")
        numericString = numericString.substring(0, Math.min(stringLength, 10 + if (startsWithOne) 1 else 0))
        val lastHyphenIndex = 6 + if (startsWithOne) 1 else 0
        val secondToLastHyphenIndex = 3 + if (startsWithOne) 1 else 0

        if (stringLength >= lastHyphenIndex) {
            numericString = numericString.substring(0, lastHyphenIndex) + "-" + numericString.substring(lastHyphenIndex, numericString.length)
        }
        if (stringLength >= secondToLastHyphenIndex) {
            numericString = numericString.substring(0, secondToLastHyphenIndex) + "-" + numericString.substring(secondToLastHyphenIndex, numericString.length)
        }
        if (numericString.startsWith("1")) {
            numericString = numericString.substring(0, 1) + "-" + numericString.substring(1, numericString.length)
        }
        return numericString
    }


    protected fun showSetIntroVideoDialog(context: Context) {

        val options = arrayOf<CharSequence>("Take Video", "Choose from Gallery")

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Add Video")
        builder.setItems(options) { dialog, which ->
            if (options[which] == "Take Video") {

                val intentCaptureVideo = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                if (intentCaptureVideo.resolveActivity(context.packageManager) != null) {
                    val maxVideoSize = (12 * 1024 * 1024).toLong() // 12 MB
                    intentCaptureVideo.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 60)//120 sec = 2min  //10000sec //10 min
                    intentCaptureVideo.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0)
                    intentCaptureVideo.putExtra(MediaStore.EXTRA_SIZE_LIMIT, maxVideoSize)
                    startActivityForResult(intentCaptureVideo, Constant.REQUEST_VIDEO_CAPTURE)
                }
            } else if (options[which] == "Choose from Gallery") {
                val intent: Intent
                if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                    intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                } else {
                    intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI)
                }
                intent.type = "video/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(intent, Constant.SELECT_VIDEO_REQUEST)
            }
        }
        builder.show()
    }


    protected fun getCurrentDateString(): String {
        @SuppressLint("SimpleDateFormat") val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        return dateFormat.format(date)
    }

    // from bitmap to file creater"""""""""""
    protected fun savebitmap(mContext: Context, bitmap: Bitmap, name: String): File? {
        val filesDir = mContext.applicationContext.filesDir
        val imageFile = File(filesDir, name)

        val os: OutputStream
        try {
            os = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, os)
            os.flush()
            os.close()
            return imageFile
        } catch (e: Exception) {
            Log.e(mContext.javaClass.simpleName, "Error writing bitmap", e)
        }

        return null
    }

    // simple date formate
    fun DateFormatChange(input: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        val outputFormat = SimpleDateFormat("dd MMM yyyy ");
        System.out.println(outputFormat.format(inputFormat.parse(input)))
        return outputFormat.format(inputFormat.parse(input))
    }

    // date formate with suffix and time
    fun getCurrentDateInSpecificFormat(input: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val outputFormat1 = SimpleDateFormat("dd MMM yyyy KK:mm a")
        val outputDateFormat = SimpleDateFormat("dd")

        val date1 = outputDateFormat.format(inputFormat.parse(input))

        val dayNumberSuffix = getDayNumberSuffix(date1.toInt())
        val dateFormat = SimpleDateFormat("dd'$dayNumberSuffix' MMM yyyy - KK:mm a")
        return dateFormat.format(inputFormat.parse(input))
    }

    // add suffix in date
    private fun getDayNumberSuffix(day: Int): String {
        if (day >= 11 && day <= 13) {
            return "th"
        }
        when (day % 10) {
            1 -> return "st"
            2 -> return "nd"
            3 -> return "rd"
            else -> return "th"
        }
    }


}