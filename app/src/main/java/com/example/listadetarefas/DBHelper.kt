package com.example.listadetarefas

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private val DB_NAME = "lista_db"

class DBHelper (context: Context): SQLiteOpenHelper(context, DB_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        //executa os comandos em sql da classe create_table
       db.execSQL(ListaItemModel.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //se existir ele apafa a tabela e cria dnv
        db.execSQL("DROP TABLE IF EXISTS" + ListaItemModel.LISTA_TABLE_NAME)

        onCreate(db)
    }

    fun insertListaItem(listaItem: String): Long{
        //pegando o banco de dados e setando como editavel
        val db = this.writableDatabase

        //vaiavel usada para inserir no banco de dados no banco, nesse caso a hora e id sao inseridos altomaticamente
        val values = ContentValues()
        values.put(ListaItemModel.LISTA_TEXT_COLUMN, listaItem)

        val item = db.insert(ListaItemModel.LISTA_TABLE_NAME, null, values)

        db.close()
        return item
    }
}