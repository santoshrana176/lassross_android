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
import java.text.ParseException
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
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        val outputFormat = SimpleDateFormat("dd MMM yyyy ", Locale.getDefault());
        System.out.println(outputFormat.format(inputFormat.parse(input)))
        return outputFormat.format(inputFormat.parse(input))
    }

    // date formate with suffix and time
    fun getCurrentDateInSpecificFormat(input: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat1 = SimpleDateFormat("dd MMM yyyy KK:mm a", Locale.getDefault())
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date = df.parse(input)
        df.timeZone = TimeZone.getDefault()
        val df1 = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.ENGLISH).format(date!!)
        return df1
        //  Log.e("date---",""+df1)
        //   inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        //  val outputDateFormat = SimpleDateFormat("dd")

        // val date1 = outputDateFormat.format(inputFormat.parse(input))

        //  val dayNumberSuffix = getDayNumberSuffix(date1.toInt())
        //val dateFormat = SimpleDateFormat("dd'$dayNumberSuffix' MMM yyyy - KK:mm a",Locale.getDefault())
        //return dateFormat.format(inputFormat.parse(input))
    }


    fun getDayDifference(departDateTime: String): String {
        var curruntTime = ""
        var returnDay = ""

        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss a", Locale.ENGLISH)
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date = df.parse(departDateTime)
        df.timeZone = TimeZone.getDefault()
        val df1 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss a", Locale.ENGLISH).format(date!!)

        val dateInMillis = System.currentTimeMillis()
        val format = "yyyy-MM-dd HH:mm:ss a"
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        curruntTime = sdf.format(Date(dateInMillis))
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss a", Locale.ENGLISH)


        try {
            val startDate = simpleDateFormat.parse(df1)
            val endDate = simpleDateFormat.parse(curruntTime)
            var different = endDate!!.time - startDate!!.time
            val secondsInMilli: Long = 1000
            val minutesInMilli = secondsInMilli * 60
            val hoursInMilli = minutesInMilli * 60
            val daysInMilli = hoursInMilli * 24
            val weekInMilli = daysInMilli * 7
            val monthInMilli = weekInMilli * 4
            val yearInMilli = monthInMilli * 12


            val elapsedYear = different / yearInMilli
            different = different % yearInMilli

            val elapsedMonth = different / monthInMilli
            different = different % monthInMilli

            val elapsedWeek = different / weekInMilli
            different = different % weekInMilli

            val elapsedDays = different / daysInMilli
            different = different % daysInMilli

            val elapsedHours = different / hoursInMilli
            different = different % hoursInMilli

            val elapsedMinutes = different / minutesInMilli
            different = different % minutesInMilli

            val elapsedSeconds = different / secondsInMilli

            if (elapsedYear >= 1) {
                returnDay = "$elapsedYear year ago"
            } else if (elapsedMonth >= 1) {
                returnDay = "$elapsedMonth month ago"
            } else if (elapsedWeek >= 1) {
                if (elapsedWeek == 1L) {
                    returnDay = "$elapsedWeek week ago"
                } else returnDay = "$elapsedWeek weeks ago"
            } else if (elapsedDays == 1L) {
                //  returnDay =  elapsedDays+"" +  "1 day ago"
            } else if (elapsedDays > 1 && elapsedDays < 7) {
                returnDay = "$elapsedDays days ago"
            } else if (elapsedDays == 0L) {
                if (elapsedHours == 0L) {
                    if (elapsedMinutes == 0L) {
                        //returnDay = elapsedSeconds + " Just now"
                    } else {
                        returnDay = "$elapsedMinutes mins ago"
                    }
                } else if (elapsedHours == 1L) {
                    returnDay = "$elapsedHours hour ago"
                } else {
                    returnDay = "$elapsedHours hours ago"
                }
            } else {
                returnDay = "$elapsedDays days ago"
            }
        } catch (e: ParseException) {
            Log.d("day diffrence", e.message)
        }
        if (returnDay.startsWith("-")) {
            // returnDay = removeFirstChar(returnDay)!!
        }

        return returnDay
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