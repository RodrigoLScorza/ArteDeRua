package br.com.rodrigoscorza.artederua.ui.viewmodel

import android.arch.lifecycle.ViewModel
import android.support.v4.app.Fragment
import br.com.rodrigoscorza.artederua.ui.fragments.LoginFragment

class LoginOuCadastroActivityViewModel : ViewModel() {

    var fragmentAtual: Fragment
    var dialogInfo: Boolean

    init {
        fragmentAtual = LoginFragment()
        dialogInfo = false
    }

    fun setFragment(fragment: Fragment){
        this.fragmentAtual = fragment
    }



}