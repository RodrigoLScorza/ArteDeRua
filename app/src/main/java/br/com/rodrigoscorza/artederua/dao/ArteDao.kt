package br.com.rodrigoscorza.artederua.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import br.com.rodrigoscorza.artederua.entities.Arte

@Dao
interface ArteDao {



    @Insert
    fun inserir(arte: Arte)

    @Query("SELECT * FROM Arte")
    fun pegarArtes(): LiveData<MutableList<Arte>>

    @Query("SELECT * FROM Arte WHERE ID = :id")
    fun pegarArtePorId(id: Int): Arte

    @Update()
    fun atualizar(arte: Arte)

    @Delete()
    fun apagar(arte: Arte)
}