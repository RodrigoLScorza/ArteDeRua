package br.com.rodrigoscorza.artederua.util.messagingfirebase

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import br.com.rodrigoscorza.artederua.R
import br.com.rodrigoscorza.artederua.ui.LoginOuCadastroActivity
import br.com.rodrigoscorza.artederua.util.Constantes
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.orhanobut.hawk.Hawk

class NotificationMessagingFire : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)

        if (!p0?.data?.isEmpty()!!) {

            val data = p0?.data
            val notication = p0?.notification
            sendNotification(notication?.body)
        }
    }


    private fun sendNotification(messageBody: String?) {
        val intent = Intent(this, LoginOuCadastroActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this@NotificationMessagingFire)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Nova Arte Localizada!")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        Hawk.put("latitude", -23.5641085)
        Hawk.put("longitude", -46.6524089)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }

}
