package br.com.rodrigoscorza.artederua.ui

import android.arch.lifecycle.ViewModel
import android.support.v4.app.Fragment

class LoginOuCadastroActivityViewModel : ViewModel() {

    var fragmentAtual: Fragment

    init {
        fragmentAtual = LoginFragment()
    }

    fun setFragment(fragment: Fragment){
        this.fragmentAtual = fragment
    }



}