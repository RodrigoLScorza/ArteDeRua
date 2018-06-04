package br.com.rodrigoscorza.artederua.util

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import java.util.ArrayList

/**
 * Created by Rodrigo on 01/03/2018.
 */
object PermissionUtils {
    //Solicita as permissoes

    fun validate(activity: Activity, requestCode: Int, vararg permissions: String): Boolean {
        val list = ArrayList<String>()
        for (permission in permissions) {
            //valida permissão
            val ok = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
            if (!ok) {
                list.add(permission)
            }
        }
        if (list.isEmpty()) {
            //Todas Permissoes estão ok
            return true
        }

        //lista de permissoes que ainda falta acesso
        val newPermission = arrayOfNulls<String>(list.size)
        list.toArray(newPermission)
        //Solicita permissao
        ActivityCompat.requestPermissions(activity, newPermission, 1)
        return false
    }

}