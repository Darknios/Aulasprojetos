package com.example.agenda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class ContatoAdapter(
    private var contatos: List<Contato>,
    private val onEditClick: (Contato) -> Unit,
    private val onDeleteClick: (Contato) -> Unit
) : RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>() {

    inner class ContatoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeTextView: TextView = itemView.findViewById(R.id.text_nome)
        val foneTextView: TextView = itemView.findViewById(R.id.text_fone)
        val enderecoTextView: TextView = itemView.findViewById(R.id.text_endereco)
        val cidadeTextView: TextView = itemView.findViewById(R.id.text_cidade)
        val editButton: Button = itemView.findViewById(R.id.button_edit)
        val deleteButton: Button = itemView.findViewById(R.id.button_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_contato, parent, false)
        return ContatoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        val contato = contatos[position]
        holder.nomeTextView.text = contato.nome
        holder.foneTextView.text = contato.fone
        holder.enderecoTextView.text = contato.endereco
        holder.cidadeTextView.text = contato.cidade
        holder.editButton.setOnClickListener { onEditClick(contato) }
        holder.deleteButton.setOnClickListener { onDeleteClick(contato) }
    }

    override fun getItemCount(): Int = contatos.size

    fun updateList(newList: List<Contato>) {
        contatos = newList
        notifyDataSetChanged()
    }
}
