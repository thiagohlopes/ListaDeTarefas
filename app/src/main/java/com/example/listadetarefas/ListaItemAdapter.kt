package com.example.listadetarefas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.ParseException
import java.text.SimpleDateFormat

class ListaItemAdapter(private val context: Context, private val itensList: List<ListaItemModel>) :RecyclerView.Adapter<ListaItemAdapter.ViewHolder>(){
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var listaTexto : TextView = view.findViewById(R.id.tv_texto)
        var listaData : TextView = view.findViewById(R.id.tv_dataAtual)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_layout,parent,false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        //é usado para pegar o numero de posição
        return itensList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = itensList[position]

        //lincou o listItem do banco com o listaTexto do layout
        holder.listaTexto.setText(listItem.listaTexto)
        //lincou o listData do banco com o listaData do layout, usou !! para nao aceitar null
        holder.listaData.text = formatadata(listItem.listaData!!)
    }

    private fun formatadata(data: String): String{
        try {
            val formatoIni = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val recebedata = formatoIni.parse(data)
            val formatoFim = SimpleDateFormat("d MMM, YYYY")
            return formatoFim.format(recebedata)
        } catch (e: ParseException){
            return ""
        }
    }
}