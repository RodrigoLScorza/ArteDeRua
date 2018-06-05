package br.com.rodrigoscorza.artederua.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.LocationManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import br.com.rodrigoscorza.artederua.util.PermissionUtils
import br.com.rodrigoscorza.artederua.R
import br.com.rodrigoscorza.artederua.entities.Arte
import br.com.rodrigoscorza.artederua.ui.adapter.ArteAdapterViewModel
import br.com.rodrigoscorza.artederua.util.Constantes
import br.com.rodrigoscorza.artederua.util.value
import com.frosquivel.magicalcamera.MagicalCamera
import com.frosquivel.magicalcamera.MagicalPermissions
import com.frosquivel.magicalcamera.Utilities.ConvertSimpleImage
import kotlinx.android.synthetic.main.activity_arte.*
import br.com.rodrigoscorza.artederua.util.TypeFace
import java.io.File
import android.R.attr.data
import android.provider.MediaStore
import android.widget.ImageView


class ArteActivity : AppCompatActivity() {

    private var magicalCamera: MagicalCamera? = null
    private var arte: Arte? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arte)

        arte = Arte()


        //edtNomeArte.TypeFace(this@ArteActivity)

        btCamera.setOnClickListener {
            if (enableGPS()) {
                val ok = PermissionUtils.validate(this@ArteActivity, 21, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)

                if (ok) {
                    magicalCamera = MagicalCamera(this@ArteActivity, 30, MagicalPermissions(
                            this@ArteActivity, arrayOf(
                            Manifest.permission.CAMERA
                    )))

                    magicalCamera?.takePhoto()

                }
            }
        }



        btSalvar.setOnClickListener {
            if (edtNomeArte.value().trim().length == 0) {
                Toast.makeText(this@ArteActivity, resources.getText(R.string.nomeArteVazio), Toast.LENGTH_LONG).show()
                edtNomeArte.requestFocus()
                return@setOnClickListener
            }

            if (nota.rating == 0f) {
                Toast.makeText(this@ArteActivity, resources.getText(R.string.definaUmaNota), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if ((ivArte.drawable as BitmapDrawable).getBitmap() == null) {
                Toast.makeText(this@ArteActivity, resources.getText(R.string.fotoArte), Toast.LENGTH_LONG).show()
                edtNomeArte.requestFocus()
                return@setOnClickListener
            }


            arte?.latitude = Constantes.latLng.latitude
            arte?.longitude = Constantes.latLng.longitude
            arte?.foto = Constantes.fotoBase64(ivArte)
            arte?.nome = edtNomeArte.value()
            arte?.nota = nota.rating

                ArteAdapterViewModel(application).guardaArte(arte!!, this@ArteActivity)
        }

        pegaArte()

    }

    fun pegaArte() {
        if (intent.getIntExtra(Constantes.ARTE, 0) != 0) {
            var it = ArteAdapterViewModel(this@ArteActivity.application).pegaArte(intent.getIntExtra(Constantes.ARTE, 0))
            edtNomeArte.setText(it?.nome)
            ivArte.setImageBitmap(Constantes.converteBitmap(it!!.foto))
            nota.rating = it.nota
            arte?.id = it.id
        }
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            magicalCamera?.resultPhoto(requestCode, resultCode, data)
            ivArte.setImageBitmap(magicalCamera?.photo)
        }
    }





    fun enableGPS(): Boolean {
        val service = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val enable = service.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!enable) {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
        return enable
    }
}
