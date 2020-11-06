package com.example.listadetarefas

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {
    private var coordinatorLayout: CoordinatorLayout? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.setTitleTextColor(Color.WHITE)
        //setSupportActionBar(toolbar)

        controle()
    }

    private fun controle() {
        coordinatorLayout = findViewById(R.id.layout_main)
        recyclerView =findViewById(R.id.rv_main)
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton

        fab.setOnClickListener{ showDialog(false, null, -1)}
    }

    private fun showDialog(isUpdate: Boolean, nothing: Nothing?, position: Int) {
        val layoutInflaterAndroid = LayoutInflater.from(applicationContext)
        val view = layoutInflaterAndroid.inflate(R.layout.lista_dialog, null)

        val userInput = AlertDialog.Builder(this@MainActivity)
        userInput.setView(view)

        val input = view.findViewById<EditText>(R.id.et_dialogText)
        val titulo = view.findViewById<TextView>(R.id.tv_dialogTitle)
        titulo.text = if(!isUpdate)  getString(R.string.novo)  else getString(R.string.editar)

        userInput
            .setCancelable(false)
            .setPositiveButton(if(isUpdate) getString(R.string.atualizar) else getString(R.string.salvar)) {dialogBox, id ->}
            .setNegativeButton(getString(R.string.cancelar)) {dialogBox, id -> dialogBox.cancel()}

        val alertDialog = userInput.create()
        alertDialog.show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener { View.OnClickListener {
            if(TextUtils.isEmpty(input.text.toString())){
                Toast.makeText(this@MainActivity, getString(R.string.toastTarefa), Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }else{
                alertDialog.dismiss()
            }
        } }
    }
}