package br.com.rodrigoscorza.artederua.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import br.com.rodrigoscorza.artederua.entities.Arte

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