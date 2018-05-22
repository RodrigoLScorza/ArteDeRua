package br.com.rodrigoscorza.artederua

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment


class LoginOuCadastroActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        changeFragment(LoginFragment())

    }


    fun changeFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.container, fragment)
                .commit()
    }
}
