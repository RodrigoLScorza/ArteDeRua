package br.com.rodrigoscorza.artederua.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Arte(@PrimaryKey(autoGenerate = true) var id: Int,
                var logitude: Long,
                var altitude: Long,
                var foto: Array<Byte>,
                var nota: Int) {


}