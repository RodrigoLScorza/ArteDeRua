package br.com.rodrigoscorza.artederua.ui

import android.arch.lifecycle.ViewModel

class LoginFragmentViewModel : ViewModel() {
    var email: String
    var senha: String


    init {
        email = ""
        senha = ""
    }

    fun setLoginStatus(email: String, senha: String) {
        this.email = email
        this.senha = senha
    }
}