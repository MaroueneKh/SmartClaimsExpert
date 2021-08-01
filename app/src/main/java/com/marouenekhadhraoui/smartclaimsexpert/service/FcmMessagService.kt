package com.marouenekhadhraoui.smartclaimsexpert.service;

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.data.local.Datapreferences
import com.marouenekhadhraoui.smartclaimsexpert.ui.main.MainActivity
import com.marouenekhadhraoui.smartclaimsexpert.ui.main.VisioActivity2
import com.marouenekhadhraoui.smartclaimsexpert.utils.NOTIFICATION_CHANNEL_ID
import com.marouenekhadhraoui.smartclaimsexpert.utils.TO_SIGNIN_OR_SIGNUP
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class FcmMessageService : FirebaseMessagingService() {


    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var dataPref: Datapreferences

    val job = SupervisorJob()

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.data.isNotEmpty()) {
            logger.log("notif")
            logger.log(remoteMessage.data.get("notif").toString())
            if (remoteMessage.data["notif"].equals("Facture ajouté")) {
                sendNotification2("Une facture a été ajouté .", 24)
            } else if (remoteMessage.data["notif"].equals("on")) {

                val intent = Intent(this, VisioActivity2::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                intent.action = TO_SIGNIN_OR_SIGNUP
                startActivity(intent)
            }
            else if (remoteMessage.data["notif"].equals("off")) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                intent.action = TO_SIGNIN_OR_SIGNUP
                startActivity(intent)
            }
        }

    }
    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */

    @RequiresApi(Build.VERSION_CODES.M)
    private fun sendNotification2(messageBody: String,id:Int) {
        CoroutineScope(job).launch {
            dataPref.setnotif(applicationContext,1)
            // Your Stuff here
        }

        val chronoText = messageBody
        val intent: Intent
        val pendingIntent: PendingIntent
        var notificationBuilder: NotificationCompat.Builder? = null
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val name = "name"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                name,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = packageName
            notificationManager.createNotificationChannel(channel)
            if (notificationBuilder == null) {
                notificationBuilder =
                    NotificationCompat.Builder(application, NOTIFICATION_CHANNEL_ID)
            }
        }
        val customView =
            RemoteViews(packageName, R.layout.ic_notifications_expanded2)


        customView.setTextViewText(
            R.id.notif_txt_timeclock_chrono2,
            chronoText
        )

        val notif = notificationBuilder!!
            .setSmallIcon(R.drawable.icon_notif)
            .setShowWhen(false)
            .setVibrate(longArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0))
            .setSmallIcon(R.drawable.icon_notif)
            .setContentTitle("Changement d'état")
            .setContentText("")
            .setContentIntent(pendingIntent)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(chronoText)
            )
            .build()
        notif.flags = Notification.FLAG_ONGOING_EVENT
        startForeground(1 /* ID of notification */, notif)
        // showNotificationPointeuseIfNotShown()


    }

}
