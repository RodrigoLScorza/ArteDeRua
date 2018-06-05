package br.com.rodrigoscorza.artederua.util

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import br.com.rodrigoscorza.artederua.entities.Arte
import com.frosquivel.magicalcamera.MagicalCamera
import com.frosquivel.magicalcamera.Utilities.ConvertSimpleImage
import com.google.android.gms.maps.model.LatLng

class Constantes {

    companion object {
        lateinit var latLng: LatLng
        val ARTE = "ARTE"

        fun converteBitmap(foto: String): Bitmap {
            return ConvertSimpleImage.bytesToBitmap(ConvertSimpleImage.stringBase64ToBytes(foto))
        }

        fun fotoBase64(iv: ImageView): String {
            return ConvertSimpleImage.bytesToStringBase64(ConvertSimpleImage.bitmapToBytes(
                    (iv.drawable as BitmapDrawable).getBitmap(), MagicalCamera.PNG))
        }

    }

}