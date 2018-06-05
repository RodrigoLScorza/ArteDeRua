package br.com.rodrigoscorza.artederua.ui.viewmodel

import android.arch.lifecycle.ViewModel
import android.support.v4.app.Fragment
import br.com.rodrigoscorza.artederua.ui.fragments.MFragment

class PrincipalActivityViewModel : ViewModel(){
    var fragmentAtual: Fragment

    init {
        fragmentAtual = MFragment()
    }
}