package br.com.rodrigoscorza.artederua.ui.adapter

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import br.com.rodrigoscorza.artederua.dao.DBArte
import br.com.rodrigoscorza.artederua.entities.Arte

class ArteAdapterViewModel(application: Application) : AndroidViewModel(application) {


    lateinit var artes: LiveData<MutableList<Arte>>
    private val db: DBArte = DBArte.getDB(application.applicationContext)!!

    init {
        buscaDados()
    }

    private fun buscaDados() {
        artes = db.arteDao().pegarArtes()
    }


    fun guardaArte(arte: Arte, activity: Activity) {
        DBAsyncTask(activity).execute(arte)
    }

    private inner class DBAsyncTask(activity: Activity? = null) : AsyncTask<Arte, Void, Arte?>() {
        val ac = activity

        override fun doInBackground(vararg params: Arte): Arte? {
            if (ac != null) {
                if (params[0].id == 0) {
                    db.arteDao().inserir(params[0])
                } else {
                    db.arteDao().atualizar(params[0])
                }
                return null
            } else {
                db.arteDao().apagar(params[0])
                return params[0]
            }
        }

        override fun onPostExecute(arte: Arte?) {
            super.onPostExecute(arte)
            if (ac != null) {
                ac.finish()
            } else {
                artes.value?.remove(arte)
            }
        }
    }



    fun deleteArte(arte: Arte) {
        arte.let {
            if (it != null) {
                DBAsyncTask().execute(arte)
            }
        }
    }

    fun pegaArte(id: Int): Arte {
        return AsyncGetArte().execute(id).get()!!
    }

    private inner class AsyncGetArte() : AsyncTask<Int, Void, Arte?>() {


        override fun doInBackground(vararg params: Int?): Arte? {
            return db.arteDao().pegarArtePorId(params[0]!!)
        }
    }

}



