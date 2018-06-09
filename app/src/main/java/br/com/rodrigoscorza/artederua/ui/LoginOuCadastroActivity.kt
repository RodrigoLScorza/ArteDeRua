package br.com.rodrigoscorza.artederua.ui

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import br.com.rodrigoscorza.artederua.util.PermissionUtils
import br.com.rodrigoscorza.artederua.R
import br.com.rodrigoscorza.artederua.ui.dialog.DialogInfo
import br.com.rodrigoscorza.artederua.ui.viewmodel.LoginOuCadastroActivityViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.orhanobut.hawk.Hawk
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_login.*


class LoginOuCadastroActivity : AppCompatActivity(), OnCompleteListener<AuthResult> {


    lateinit var callbackManager: CallbackManager
    lateinit var mAuth: FirebaseAuth
    var nome: String? = null
    lateinit var viewModel: LoginOuCadastroActivityViewModel
    var tag: String = "Atual"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var bar = includeToolbar as Toolbar
        setSupportActionBar(bar)


        PermissionUtils.validate(this@LoginOuCadastroActivity, 1, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE)

        //FirebaseApp.initializeApp(baseContext)
        viewModel = ViewModelProviders.of(this@LoginOuCadastroActivity).get(LoginOuCadastroActivityViewModel::class.java)

        mAuth = FirebaseAuth.getInstance()
        Hawk.init(this@LoginOuCadastroActivity).build()
        callbackManager = CallbackManager.Factory.create()

        FacebookSdk.sdkInitialize(this@LoginOuCadastroActivity.applicationContext)
        AppEventsLogger.activateApp(this@LoginOuCadastroActivity)


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.getItemId()) {
            R.id.info -> {
                chamaDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun chamaDialog() {
        DialogInfo(this@LoginOuCadastroActivity, viewModel).show()
    }


    fun changeFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.container, fragment, tag)
                .commit()

    }

    override fun onPause() {
        super.onPause()
        viewModel.fragmentAtual = supportFragmentManager.findFragmentByTag(tag) as Fragment
    }

    fun mandaToast(id: Int) {
        Toast.makeText(this@LoginOuCadastroActivity, resources.getText(id), Toast.LENGTH_LONG).show()
    }

    fun registerCall(loginButton: LoginButton?) {
        loginButton?.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                // App code
                val credential = FacebookAuthProvider.getCredential(loginResult.accessToken.token)
                mAuth.signInWithCredential(credential).addOnCompleteListener(this@LoginOuCadastroActivity)
            }

            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                // App code
            }
        })
    }

    override fun onComplete(p0: Task<AuthResult>) {
        if (p0.isSuccessful) {
            var user = mAuth.currentUser

            if (nome == null) {
                nome = user?.displayName.toString()
            }
            Toast.makeText(this@LoginOuCadastroActivity,
                    "${resources.getText(R.string.bemVindo)} ${nome}", Toast.LENGTH_SHORT).show()

            val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(nome).build()
            user?.updateProfile(profileUpdates)

            mudarActivity()
        } else {
            mandaToast(R.string.usuarioNaoLocalizado)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser != null) {
            mudarActivity()
        }

        changeFragment(viewModel.fragmentAtual)

        if(viewModel.dialogInfo == true){
            chamaDialog()
        }

    }

    private fun mudarActivity() {
        startActivity(Intent(this@LoginOuCadastroActivity, PrincipalActivity::class.java))
        finish()
    }

    fun loginEmailPassword(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this@LoginOuCadastroActivity)
    }


    fun cadastraNovoUsuario(email: String, senha: String, nome: String) {
        this.nome = nome
        mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this@LoginOuCadastroActivity)
    }
}
