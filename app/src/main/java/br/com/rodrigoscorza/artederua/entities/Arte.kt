package br.com.rodrigoscorza.artederua.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity
class Arte {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var longitude: Double = 0.0
    var latitude: Double = 0.0
    var foto: String = ""
    var nome: String = ""
    var nota: Float = 0F


    constructor() {}

    constructor(latitude: Double, longitude: Double, foto: String, nome: String, nota: Float) {
        this.longitude = longitude
        this.latitude = latitude
        this.foto = foto
        this.nome = nome
        this.nota = nota
    }

}