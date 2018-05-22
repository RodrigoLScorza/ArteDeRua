package br.com.rodrigoscorza.artederua

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_principal.*

class PrincipalActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }



    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
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
    }
}
