package br.com.rodrigoscorza.artederua.ui


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import br.com.rodrigoscorza.artederua.R
import br.com.rodrigoscorza.artederua.util.validEmail
import br.com.rodrigoscorza.artederua.util.value
import kotlinx.android.synthetic.main.fragment_cadastro.*


/**
 * A simple [Fragment] subclass.
 *
 */
class CadastroFragment : Fragment(), TextWatcher {

    lateinit var loginOuCadastroActivity: LoginOuCadastroActivity
    lateinit var viewModel: CadastroFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        loginOuCadastroActivity = (activity as LoginOuCadastroActivity)
        viewModel = ViewModelProviders.of(loginOuCadastroActivity).get(CadastroFragmentViewModel::class.java)

        return inflater.inflate(R.layout.fragment_cadastro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        input_nome.requestFocus()

        btCadastrar.setOnClickListener {
            if (validaCampo(input_nome, R.string.nomeVazio) && validaCampo(input_email, R.string.emailVazio) &&
                    validaCampo(input_password, R.string.passwordVazio) && validaCampo(input_senhaC, R.string.passwordVazio)) {
                if (input_email.validEmail()) {
                    if (input_password.value().equals(input_senhaC.value())) {
                        loginOuCadastroActivity.cadastraNovoUsuario(input_email.value(), input_password.value(), input_nome.value())
                    } else {
                        mandaToastEfoca(input_senhaC, R.string.senhaInvalida)
                    }
                } else {
                    mandaToastEfoca(input_email, R.string.emailInvalido)
                }
            }

        }

        input_senhaC.addTextChangedListener(this)
        tvAqui.setOnClickListener { loginOuCadastroActivity.changeFragment(LoginFragment()) }
    }

    override fun onStart() {
        super.onStart()
        input_nome.setText(viewModel.nome)
        input_email.setText(viewModel.email)
        input_senhaC.setText(viewModel.senhac)
        input_password.setText(viewModel.senha)
    }

    override fun onPause() {
        super.onPause()
        viewModel.setStatusCadastro(input_nome.value(), input_email.value(), input_password.value(), input_senhaC.value())
    }

    private fun mandaToastEfoca(edt: EditText?, id: Int) {
        loginOuCadastroActivity.mandaToast(id)
        edt?.requestFocus()
    }


    private fun validaCampo(edt: EditText?, id: Int): Boolean {
        if (edt?.text?.isNullOrEmpty()!! && edt?.text?.isNullOrBlank()!!) {
            mandaToastEfoca(edt, id)
            return false
        }
        return true
    }

    override fun afterTextChanged(s: Editable?) {
        if (s?.length == 0) {
            setDrawableRight(input_password)
            setDrawableRight(input_senhaC)
            input_password.isEnabled = true
            return
        }


        if (s?.toString().equals(input_password.value())) {
            setDrawableRight(input_password, R.drawable.ic_ok)
            setDrawableRight(input_senhaC, R.drawable.ic_ok)
        } else {
            setDrawableRight(input_password, R.drawable.ic_error)
            setDrawableRight(input_senhaC, R.drawable.ic_error)
        }
        input_password.isEnabled = false
    }

    private fun setDrawableRight(editText: EditText?, id: Int = 0) {
        editText?.setCompoundDrawablesWithIntrinsicBounds(0, 0, id, 0);
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }


}
