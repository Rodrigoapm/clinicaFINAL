package com.clinicafinal

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

class Animal (
    var id: Long,
    var raca : String,
    var tipo: String,
    var raca: raca,
    var id: Long = -1
) : Serializable {
fun toContentValues() : ContentValues {
    val valores = ContentValues()

    valores.put(TabelaBDAnimal.CAMPO_Raca, raca)
    valores.put(TabelaBDAnimal.CAMPO_TIPO, tipo)
    valores.put(TabelaBDAnimal.CAMPO_ANIMAL_ID, Raca.id)

    return valores
}


companion object {
    fun fromCursor(cursor: Cursor): Animal {
        val posId = cursor.getColumnIndex(BaseColumns._ID)
        val posRaca = cursor.getColumnIndex(TabelaBDAnimal.CAMPO_Raca)
        val posTipo = cursor.getColumnIndex(TabelaBDAnimal.CAMPO_TIPO)
        val posIdAnim = cursor.getColumnIndex(TabelaBDAnimal.CAMPO_ANIMAL_ID)
        val posNomeRac =  cursor.getColumnIndex(TabelaBDRaca.CAMPO_NOME)

        val id = cursor.getLong(posId)
        val nome = cursor.getString(posRaca)
        val tipo = cursor.getString(posTipo)
        val idRaca = cursor.getLong(posIdAnim)
        val nomeRaca = cursor.getString(posNomeRac)
        val Raca = Raca(nomeRaca, idRaca)


        return Animal(id, Raca, nome, tipo, nomeRaca, idRaca)
    }
}
}