package com.mindiii.lasross.firebase_notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.firebase_notification.model.NotificationModel
import com.mindiii.lasross.module.home.HomeActivity


class MyFirebaseMessagingServices : FirebaseMessagingService() {
    private val CHANNEL_ID = "com.bang"// The id of the channel.
    internal var name: CharSequence = "Abc"// The user-visible name of the channel.
    internal var title: String? = null
    internal var body: String? = null
    private var notificationModel: NotificationModel? = null
    private var fireBaseToken = ""
    private lateinit var session: Session

    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
        getCurrentFirebaseToken()
    }

    internal fun getCurrentFirebaseToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("firebaseToken", "getInstanceId failed", task.exception)
                return@OnCompleteListener
            }

            fireBaseToken = task.result!!.token
            Log.d("firebaseToken", fireBaseToken)
            val session = Session(applicationContext)
            if (session.deviceToke.isNullOrEmpty()) {
                session.deviceToke = fireBaseToken
            }


        })
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        session = Session(this)
        Log.i(TAG, "" + remoteMessage.data.toString())

        val reference_id = remoteMessage.data.get("reference_id")
        val type = remoteMessage.data.get("type")
        val body = remoteMessage.data.get("body")
        val title = remoteMessage.data.get("title")
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("reference_id", reference_id)
        intent.putExtra("type", type)
        intent.putExtra("body", body)
        intent.putExtra("title", title)
        sendNotification(reference_id!!, type!!, body!!, title!!, intent)
    }

    private fun sendNotification(reference_id: String, type: String, body: String, title: String, intent: Intent) {
        assert(intent != null)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val iUniqueId = (System.currentTimeMillis() and 0xfffffff).toInt()
        val pendingIntent = PendingIntent.getActivity(applicationContext, iUniqueId, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var importance = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_HIGH
        }
        val mChannel: NotificationChannel
        val notificationBuilder: NotificationCompat.Builder
        notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(body)
                .setContentText(title)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setPriority(Notification.PRIORITY_HIGH)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(com.mindiii.lasross.R.drawable.app_ico)
            notificationBuilder.color = ContextCompat.getColor(applicationContext, com.mindiii.lasross.R.color.colorPrimary)
        } else {
            notificationBuilder.setSmallIcon(com.mindiii.lasross.R.drawable.app_ico)
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            assert(notificationManager != null)
            notificationManager.createNotificationChannel(mChannel)
        }
        assert(notificationManager != null)
        notificationManager.notify(iUniqueId, notificationBuilder.build())
    }

    companion object {
        private val TAG = "notification4254"
    }


    private fun isAppOnForeground(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val appProcesses = activityManager.runningAppProcesses ?: return false
        val packageName = context.packageName
        for (appProcess in appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName == packageName) {
                return true
            }
        }
        return false
    }
}