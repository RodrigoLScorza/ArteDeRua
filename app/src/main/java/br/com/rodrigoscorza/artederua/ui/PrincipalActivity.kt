package br.com.rodrigoscorza.artederua.ui

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_principal.*
import br.com.rodrigoscorza.artederua.ui.fragments.LFragment
import br.com.rodrigoscorza.artederua.ui.fragments.MFragment
import br.com.rodrigoscorza.artederua.util.PermissionUtils
import br.com.rodrigoscorza.artederua.R
import br.com.rodrigoscorza.artederua.ui.viewmodel.PrincipalActivityViewModel


class PrincipalActivity : AppCompatActivity() {
    private val tag: String = "Atual"
    private lateinit var viewModel: PrincipalActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)


        viewModel = ViewModelProviders.of(this@PrincipalActivity).get(PrincipalActivityViewModel::class.java)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onPause() {
        super.onPause()
        viewModel.fragmentAtual = supportFragmentManager.findFragmentByTag(tag) as Fragment
    }

    override fun onStart() {
        super.onStart()
        changeFragment(viewModel.fragmentAtual)
    }

    fun changeFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.container, fragment, tag)
                .commit()

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_map -> {
                changeFragment(MFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navegation_list -> {
                changeFragment(LFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_exit -> {
                logOut()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        LoginManager.getInstance().logOut()
        startActivity(Intent(this@PrincipalActivity, LoginOuCadastroActivity::class.java))
        finish()
    }

    fun mandaToast(id: Int) {
        Toast.makeText(this@PrincipalActivity, resources.getText(id), Toast.LENGTH_SHORT).show()
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


    fun checkpermission(): Boolean {
        return PermissionUtils.validate(this@PrincipalActivity, 1, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)
    }

    fun permissoesDoApp() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }
}
