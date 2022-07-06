package com.clinicafinal

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDRaca(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME TEXT NOT NULL)")
    }

    companion object {
        const val NOME = "RACA"
        const val CAMPO_NOME = "nome"

        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, CAMPO_NOME)
    }
}