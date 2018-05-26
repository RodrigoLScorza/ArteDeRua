package br.com.rodrigoscorza.artederua.ui

import android.arch.lifecycle.ViewModel

class CadastroFragmentViewModel : ViewModel() {
    var nome: String
    var email: String
    var senha: String
    var senhac: String

    init {
        nome = ""
        email = ""
        senha = ""
        senhac = ""
    }


    fun setStatusCadastro(nome: String, email: String, senha: String, senhac: String) {
        this.nome = nome
        this.email = email
        this.senha = senha
        this.senhac = senhac
    }

}