package com.clinicafinal

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Raca(var nome: String = "", var id: Long = -1) : Serializable {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaBDRaca.CAMPO_NOME, nome)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Raca {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDRaca.CAMPO_NOME)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)

            return Raca(nome, id)
        }
    }

}
