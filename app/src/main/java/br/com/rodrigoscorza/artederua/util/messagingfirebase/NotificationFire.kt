package br.com.rodrigoscorza.artederua.util.messagingfirebase

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceIdService
import com.google.firebase.iid.FirebaseInstanceId
import com.orhanobut.hawk.Hawk


class NotificationFire : FirebaseInstanceIdService() {

    val TOKEN = "token"


    init {
        Hawk.init(this@NotificationFire).build()
    }

    override fun onTokenRefresh() {
        super.onTokenRefresh()
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Token Atual: " + refreshedToken!!)

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        Hawk.put(TOKEN, refreshedToken)
    }
}