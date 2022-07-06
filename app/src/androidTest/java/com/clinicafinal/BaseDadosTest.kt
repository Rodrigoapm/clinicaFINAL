package com.clinicafinal

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BaseDadosTest {
    fun appContext() =
        InstrumentationRegistry.getInstrumentation().targetContext

    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BDclinicaOpenHelper(appContext())
        return openHelper.writableDatabase
    }


    private fun insereRACA(db: SQLiteDatabase, raca: Raca) {
        raca.id = TabelaBDRaca(db).insert(raca.toContentValues())
        assertNotEquals(-1, raca.id)
    }

    private fun insereAnimal(db: SQLiteDatabase, animal: Animal) {
        animal.id = TabelaBDAnimal(db).insert(animal.toContentValues())
        assertNotEquals(-1, animal.id)
    }


    @Before
    fun apagaBaseDados() {
        appContext().deleteDatabase(BDclinicaOpenHelper.NOME)
    }


    @Test
    fun consegueAbrirBaseDados() {
        val openHelper = BDclinicaOpenHelper(appContext())
        val db = openHelper.readableDatabase

        assertTrue(db.isOpen)

        db.close()
    }

    @Test
    fun consegueInserirRACA() {
        val db = getWritableDatabase()

        insereRACA(db, RACA("Pastor Alemao"))

        db.close()
    }

    @Test
    fun consegueInserirAnimal() {
        val db = getWritableDatabase()

        val raca = RACA("Pinscher")
        insereRACA(db, raca)

        val animal = Animal(" Bobby, Pastor Alemao, Cao ", raca.id)
        animal.id = TabelaBDAnimal(db).insert(animal.toContentValues())

        assertNotEquals(-1, animal.id)

            db.close()
    }

    @Test
    fun consegueAlterarRaca() {
        val db = getWritableDatabase()

        val raca = TabelaBDRaca("Teste")
        insereRACA(db, raca)

        raca.nome = "Bulldog"

        val registosAlterados = TabelaBDRaca(db).update(
            raca.toContentValues(),
            "${TabelaBDRaca.CAMPO_ID}=?",
            arrayOf("${raca.id}"))

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test
    fun consegueAlterarAnimal() {
        val db = getWritableDatabase()

        val racaBulldog = Raca("Bulldog")
        insereRACA(db, racaBulldog)

        val RacaPastorBelga = Raca("Pastor Belga")
        insereRACA(db, RacaPastorBelga)

        val animal = Animal("Teste", "Teste", racaPastorBelga.id)
        insereAnimal(db, animal)

        animal.raca = "Pastor Belga"
        animal.tipo = "Cao"
        animal.idRaca = racaPastorBelga.id

        val registosAlterados = TabelaBDAnimal(db).update(
            animal.toContentValues(),
            "${TabelaBDAnimal.CAMPO_ANIMAL_ID}=?",
            arrayOf("${animal.id}"))


        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test
    fun consegueEliminarRaca() {
        val db = getWritableDatabase()

        val raca = Raca("Teste")
        insereRACA(db, raca)

        val registosEliminados = TabelaBDRaca(db).delete(
            "${BaseColumns._ID}=?",
            arrayOf("${raca.id}"))

        assertEquals(1, registosEliminados)

        db.close()
    }


    @Test
    fun consegueEliminarAnimal() {
        val db = getWritableDatabase()

        val raca = Raca("Chiaua")
        insereRACA(db, raca)

        val animal = Animal("Teste", "Teste", raca.id)
        insereAnimal(db, animal)

        val registosEliminados = TabelaBDAnimal(db).delete(
            "${TabelaBDAnimal.CAMPO_ANIMAL_ID}=?",
            arrayOf("${animal.id}"))

        assertEquals(1, registosEliminados)

        db.close()
    }


    @Test
    fun consegueLerRaca() {
        val db = getWritableDatabase()

        val raca = Raca("Pastor Alemao")
        insereRACA(db, raca)

        val cursor = TabelaBDRaca(db).query(
            TabelaBDRaca.TODAS_COLUNAS,
            "${TabelaBDRaca.CAMPO_ID}=?",
            arrayOf("${raca.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val racBD = Raca.fromCursor(cursor)

        assertEquals(Raca, racBD)
        db.close()
    }

    @Test
    fun consegueLerAnimal() {
        val db = getWritableDatabase()

        val raca = Raca ("Rafeiro")
        insereRACA(db, raca)

        val animal = Animal("", "Rafeiro", raca.id)
        insereAnimal(db, animal)

        val cursor = TabelaBDAnimal(db).query(
            TabelaBDAnimal.TODAS_COLUNAS,
            "${TabelaBDAnimal.CAMPO_ANIMAL_ID}=?",
            arrayOf("${animal.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val animalBD = Animal.fromCursor(cursor)

        assertEquals(animal, animalBD)

        db.close()
}