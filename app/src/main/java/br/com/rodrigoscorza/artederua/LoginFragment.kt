package br.com.rodrigoscorza.artederua


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_login.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import android.content.Intent
import com.facebook.*
import com.google.firebase.auth.FirebaseAuth
import com.orhanobut.hawk.Hawk
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.AuthCredential


class LoginFragment : Fragment(), View.OnClickListener, OnCompleteListener<AuthResult> {


    lateinit var loginButton: LoginButton
    lateinit var btLogin: Button
    lateinit var callbackManager: CallbackManager
    lateinit var mAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        mAuth = FirebaseAuth.getInstance()
        Hawk.init(activity).build()


        FacebookSdk.sdkInitialize(activity?.applicationContext)
        AppEventsLogger.activateApp(activity)

        callbackManager = CallbackManager.Factory.create()

        loginButton = view.findViewById(R.id.login_button)
        btLogin = view.findViewById(R.id.btLogin)

        loginButton.setReadPermissions("email")
        // If using in a fragment
        loginButton.setFragment(this)

        // Callback registration
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                // App code
                val credential = FacebookAuthProvider.getCredential(loginResult.accessToken.token)
                mAuth.signInWithCredential(credential).addOnCompleteListener(this@LoginFragment)
            }

            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                // App code
            }
        })

        btLogin.setOnClickListener(this)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvAqui.setOnClickListener { (activity as LoginOuCadastroActivity).changeFragment(CadastroFragment()) }
    }


    override fun onComplete(p0: Task<AuthResult>) {
        if (p0.isSuccessful) {
            mudarActivity()
        } else {
            Toast.makeText(activity, "Não foi possivel logar!", Toast.LENGTH_LONG).show()
        }
    }

    private fun mudarActivity() {
        activity?.startActivity(Intent(activity, PrincipalActivity::class.java))
        activity?.finish()
    }


    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser != null) {
            mudarActivity()
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btLogin -> {

            }
            else -> {

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

}
