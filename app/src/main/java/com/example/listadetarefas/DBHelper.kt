package com.example.listadetarefas

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private val DB_NAME = "lista_db"

class DBHelper (context: Context): SQLiteOpenHelper(context, DB_NAME, null, 1) {

    val ItensList: List<ListaItemModel>
    get() {
        val listaItens = ArrayList<ListaItemModel>()
        val selectQuerry = "SELECT * FROM " + ListaItemModel.LISTA_TABLE_NAME +
                " ORDER BY " + ListaItemModel.LISTA_DATA_COLUMN + " DESC"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuerry, null)

        if(cursor.moveToFirst()){
            do{
                val listaItem = ListaItemModel()
                listaItem.listaId = cursor.getInt(cursor.getColumnIndex(ListaItemModel.LISTA_ID_COLUMN))
                listaItem.listaTexto = cursor.getString(cursor.getColumnIndex(ListaItemModel.LISTA_TEXT_COLUMN))
                listaItem.listaData = cursor.getString(cursor.getColumnIndex(ListaItemModel.LISTA_DATA_COLUMN))

                listaItens.add(listaItem)
            }while (cursor.moveToNext())
        }

        db.close()
        return listaItens
    }

    override fun onCreate(db: SQLiteDatabase) {
        //executa os comandos em sql da classe create_table
       db.execSQL(ListaItemModel.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //se existir ele apafa a tabela e cria dnv
        db.execSQL("DROP TABLE IF EXISTS " + ListaItemModel.LISTA_TABLE_NAME)

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

    fun getListaItem(item: Long): ListaItemModel{
        val db = this.readableDatabase
        val cursor = db.query(ListaItemModel.LISTA_TABLE_NAME, arrayOf(ListaItemModel.LISTA_ID_COLUMN,
            ListaItemModel.LISTA_TEXT_COLUMN, ListaItemModel.LISTA_DATA_COLUMN),
            ListaItemModel.LISTA_ID_COLUMN + "=?", arrayOf(item.toString()),null,null,null,null)
        cursor?.moveToFirst()

        val listaItem = ListaItemModel(
            cursor!!.getInt(cursor.getColumnIndex(ListaItemModel.LISTA_ID_COLUMN)),
            cursor.getString(cursor.getColumnIndex(ListaItemModel.LISTA_TEXT_COLUMN)),
            cursor.getString(cursor.getColumnIndex(ListaItemModel.LISTA_DATA_COLUMN))
        )
        cursor.close()
        return listaItem
    }



}