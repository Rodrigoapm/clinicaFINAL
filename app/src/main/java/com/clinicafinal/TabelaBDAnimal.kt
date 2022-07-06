package com.clinicafinal

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaBDAnimal(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_Raca TEXT NOT NULL, $CAMPO_TIPO TEXT NOT NULL, $CAMPO_ANIMAL_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_ANIMAL_ID) REFERENCES ${TabelaBDRaca.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    override fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor {
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = "$NOME INNER JOIN ${TabelaBDRaca.NOME} ON ${TabelaBDRaca.CAMPO_ID} = $CAMPO_ANIMAL_ID"

        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy)
    }


    companion object {
        const val NOME = "animal"

        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val CAMPO_Raca = "raca"
        const val CAMPO_TIPO = "tipo"
        const val CAMPO_ANIMAL_ID = "animalId"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_Raca, CAMPO_TIPO, CAMPO_ANIMAL_ID, TabelaBDRaca.CAMPO_NOME)
}
}
