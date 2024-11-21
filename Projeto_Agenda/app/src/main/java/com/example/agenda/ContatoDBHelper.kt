package com.example.agenda

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues

class ContatoDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "contatos.db"
        private const val TABLE_NAME = "contato"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOME = "nome"
        private const val COLUMN_FONE = "fone"
        private const val COLUMN_ENDERECO = "endereco"
        private const val COLUMN_CIDADE = "cidade"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NOME TEXT," +
                "$COLUMN_FONE TEXT," +
                "$COLUMN_ENDERECO TEXT," +
                "$COLUMN_CIDADE TEXT)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addContato(contato: Contato): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOME, contato.nome)
            put(COLUMN_FONE, contato.fone)
            put(COLUMN_ENDERECO, contato.endereco)
            put(COLUMN_CIDADE, contato.cidade)
        }
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result
    }

    fun getAllContatos(): List<Contato> {
        val contatoList = mutableListOf<Contato>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val contato = Contato(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME)),
                    fone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FONE)),
                    endereco = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ENDERECO)),
                    cidade = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CIDADE))
                )
                contatoList.add(contato)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return contatoList
    }

    fun updateContato(contato: Contato): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOME, contato.nome)
            put(COLUMN_FONE, contato.fone)
            put(COLUMN_ENDERECO, contato.endereco)
            put(COLUMN_CIDADE, contato.cidade)
        }
        return db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(contato.id.toString()))
    }

    fun deleteContato(id: Long) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }
}
