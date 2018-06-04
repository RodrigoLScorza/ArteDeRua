package br.com.rodrigoscorza.artederua

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import br.com.rodrigoscorza.artederua.ui.LoginOuCadastroActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        iv_logo.visibility = View.GONE


    }

    override fun onStart() {
        super.onStart()


        val animacao = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        iv_logo.clearAnimation()
        iv_logo.animation = animacao
        iv_logo.startAnimation(animacao)
        iv_logo.visibility = View.VISIBLE

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, LoginOuCadastroActivity::class.java))
            finish()
        }, 3000)

    }
}
