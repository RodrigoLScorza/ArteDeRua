package br.com.rodrigoscorza.artederua.dao

import android.arch.persistence.room.*
import android.content.Context
import br.com.rodrigoscorza.artederua.entities.Arte

@Database(entities = arrayOf(Arte::class), version = 1)
abstract class DBArte : RoomDatabase() {

    abstract fun arteDao(): ArteDao

    companion object {

        var instance: DBArte? = null

        fun getDB(context: Context): DBArte? {

            if(instance == null){
                instance = Room.databaseBuilder(context.applicationContext,
                        DBArte::class.java,
                        "artedb")
                        .build()
            }
            return instance
        }
    }

}