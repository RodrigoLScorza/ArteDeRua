package br.com.rodrigoscorza.artederua.dao

import android.arch.persistence.db.SupportSQLiteOpenHelper
import android.arch.persistence.room.*
import android.content.Context
import br.com.rodrigoscorza.artederua.model.Arte

@Database(entities = arrayOf(Arte::class), version = 1)
abstract class DBArte : RoomDatabase() {

    abstract fun arteDao(): ArteDao

    companion object {
        val arteDB: String = "ArteDB"

        var instance: DBArte? = null

        fun getDB(context: Context): DBArte? {

            if(instance == null){
                instance = Room.databaseBuilder(context.applicationContext, DBArte::class.java, arteDB).build()
            }
            return instance
        }
    }

}