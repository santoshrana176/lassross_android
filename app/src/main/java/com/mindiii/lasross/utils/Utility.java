package com.mindiii.lasross.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;

import com.mindiii.lasross.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Utility {
    public static boolean isConnectingToInternet(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            for (NetworkInfo anInfo : info)
                if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
        }


        return false;
    }

    public static boolean isGPSEnabled(Context context) {

        boolean result = true;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            result = false;
        }
        return result;
    }

    /***
     * @param activity
     * @param color
     */

    public static void setStatusBarColor(Activity activity, int color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (color == 0)
                activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.colorPrimary));
            else activity.getWindow().setStatusBarColor(activity.getResources().getColor(color));
        }

    }

    public static boolean hasSoftKeys(WindowManager windowManager, Activity activity) {
        boolean hasSoftwareKeys = true;


        Display d = windowManager.getDefaultDisplay();

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        d.getRealMetrics(realDisplayMetrics);

        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);

        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;

        hasSoftwareKeys = (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
        return hasSoftwareKeys;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getChatTime(Long time) {
        if (time == null) return "";
        Date date = new Date(time);
        if (isOlder(date)) return new SimpleDateFormat("dd MMM yyyy ").format(date);
        return new SimpleDateFormat(" h:mm a ").format(date);
    }

    private static boolean isOlder(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return !format.format(date).equals(format.format(new Date()));
    }


    public static MultipartBody.Part prepareFilePart(Context context, String partName, Uri uri) {
        File file = new File(getRealPathFromURI(context, uri));
        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(Objects.requireNonNull(context.getContentResolver().getType(uri))),
                        file);
        return MultipartBody.Part.createFormData(partName, "Image.jpg", requestFile);
    }

    private static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }


    }

    public static void printBigLogcat(String TAG, String response) {
       /* int maxLogSize = 1000;
        for (int i = 0; i <= response.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > response.length() ? response.length() : end;
            Util.e(TAG, response.substring(start, end));
        }*/
    }

    public static void e(String tag, String response) {
        // Log.e(tag, response);
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertDate(String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = format.parse(date);
            assert date1 != null;
            return new SimpleDateFormat("dd MMM yyyy ").format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }

    }

    public static Date getDate(String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    @SuppressLint("SimpleDateFormat")
    public static String convertDay(String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = format.parse(date);
            assert date1 != null;
            return new SimpleDateFormat("dd").format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }

    }

    @SuppressLint("SimpleDateFormat")
    public static String convertYear(String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = format.parse(date);
            assert date1 != null;
            return new SimpleDateFormat("MMM yyyy").format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }

    }

    @SuppressLint("SimpleDateFormat")
    public static String convertTime(String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        try {
            Date date1 = format.parse(date);
            assert date1 != null;
            return new SimpleDateFormat("h:mm a").format(date1);

        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }

    }

    private static Date getTime(String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return null;
        }

    }

    @SuppressLint("SetTextI18n")
    public static void setTimeinFormat(String time, TextView tv_timeselection) {
        Date d = getTime(time);
        assert d != null;
        int hour = d.getHours();
        int minute = d.getMinutes();
        int i = hour > 11 ? hour - 12 : hour;
        String last = hour > 11 ? " PM" : " AM";
        tv_timeselection.setText(((i < 10) ? (i == 0 ? 12 : "0" + i) : i) + ":" + ((minute < 10) ? "0" + minute : minute).toString() + last);
    }

    public static String intToString(int number, int stringLength) {
        int numberOfDigits = String.valueOf(number).length();
        int numberOfLeadingZeroes = stringLength - numberOfDigits;
        StringBuilder sb = new StringBuilder();
        if (numberOfLeadingZeroes > 0) {
            for (int i = 0; i < numberOfLeadingZeroes; i++) {
                sb.append("0");
            }
        }
        sb.append(number);

        return sb.toString();
    }

    @SuppressLint("DefaultLocale")
    public static String getFormattedString(int num) {
        return String.format("%07d", num);
    }

    @SuppressLint("SetTextI18n")
    public static void setTimeinFormat(int hour, int minute, TextView tv_timeselection) {
        int i = hour > 11 ? hour - 12 : hour;
        String last = hour > 11 ? " PM" : " AM";
        tv_timeselection.setText(((i < 10) ? (i == 0 ? 12 : "0" + i) : i) + ":" + ((minute < 10) ? "0" + minute : minute).toString() + last);
    }

    public static String getTime_diffrence(String time) {
        Date d1 = new Date();
        Date d2 = new Date(System.currentTimeMillis());
        //Date d2 = new Date(System.currentTimeMillis()+((new Date().getTimezoneOffset())*60000));
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            d1 = format.parse(time);
        } catch (Exception e) {
            e("error", e.toString());
        }
        e("Times", "time " + time + " : " + d1 + " : " + d2);
        assert d1 != null;
        long diff = d2.getTime() - d1.getTime();
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        System.out.println("Time in seconds: " + diffSeconds + " seconds.");
        System.out.println("Time in minutes: " + diffMinutes + " minutes.");
        System.out.println("Time in hours: " + diffHours + " hours.");

        return (diffMinutes < 60L ? diffMinutes + " min ago" : diffHours + " hr ago");
    }

    public static void setTypeface(TextView tv, Context context, int fontres) {
        tv.setTypeface(ResourcesCompat.getFont(context, fontres));
    }

    public static Typeface getTypeface(Context context, int fontres) {
        return ResourcesCompat.getFont(context, fontres);
    }


    /**
     * @param context
     * @param filename like "currency.json"
     * @return
     */
    public static String loadJSONFromAsset(Context context, String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


}
