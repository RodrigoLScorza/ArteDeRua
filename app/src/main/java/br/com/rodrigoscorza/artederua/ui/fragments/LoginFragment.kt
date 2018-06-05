package br.com.rodrigoscorza.artederua.ui.fragments


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import br.com.rodrigoscorza.artederua.R
import br.com.rodrigoscorza.artederua.ui.viewmodel.LoginFragmentViewModel
import br.com.rodrigoscorza.artederua.ui.LoginOuCadastroActivity
import br.com.rodrigoscorza.artederua.util.TypeFace
import br.com.rodrigoscorza.artederua.util.value
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {

    lateinit var loginOuCadastroActivity: LoginOuCadastroActivity
    lateinit var viewModel: LoginFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        loginOuCadastroActivity = (activity as LoginOuCadastroActivity)
        viewModel = ViewModelProviders.of(loginOuCadastroActivity).get(LoginFragmentViewModel::class.java)


        return inflater.inflate(R.layout.fragment_login, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login_button.setReadPermissions("email")
        // If using in a fragment
        //login_button.setFragment(this@LoginFragment)
        // Callback registration
        loginOuCadastroActivity.registerCall(login_button)

       // input_email.TypeFace(context!!)
        //input_password.TypeFace(context!!)

        btLogin.setOnClickListener {
            if (validaCampo(input_email, R.string.emailVazio) && validaCampo(input_password, R.string.passwordVazio)) {
                loginOuCadastroActivity.loginEmailPassword(input_email.value(), input_password.value())
            }

        }

        tvAqui.setOnClickListener { loginOuCadastroActivity.changeFragment(CadastroFragment()) }
    }

    private fun validaCampo(edt: EditText?, id: Int): Boolean {
        if (edt?.text?.isNullOrEmpty()!! && edt?.text?.isNullOrBlank()!!) {
            loginOuCadastroActivity.mandaToast(id)
            edt.requestFocus()
            return false
        }
        return true
    }


    override fun onStart() {
        super.onStart()

        input_email.setText(viewModel.email)
        input_password.setText(viewModel.senha)

    }

    override fun onPause() {
        super.onPause()
        viewModel.setLoginStatus(input_email.value(), input_password.value())
    }



}


