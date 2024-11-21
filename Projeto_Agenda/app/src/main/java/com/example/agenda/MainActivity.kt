package com.example.agenda

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agenda.databinding.ActivityMainBinding
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: ContatoDBHelper
    private lateinit var contatoAdapter: ContatoAdapter
    private var contatosList = mutableListOf<Contato>()

    private val addContactLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null) {
                val nome = data.getStringExtra("nome") ?: ""
                val fone = data.getStringExtra("fone") ?: ""
                val endereco = data.getStringExtra("endereco") ?: ""
                val cidade = data.getStringExtra("cidade") ?: ""
                val novoContato = Contato(nome = nome, fone = fone, endereco = endereco, cidade = cidade)
                dbHelper.addContato(novoContato)
                atualizarLista()
            }
        }
    }

    private val editContactLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null) {
                val id = data.getLongExtra("id", -1)
                val nome = data.getStringExtra("nome") ?: ""
                val fone = data.getStringExtra("fone") ?: ""
                val endereco = data.getStringExtra("endereco") ?: ""
                val cidade = data.getStringExtra("cidade") ?: ""
                if (id != -1L) {
                    val contatoEditado = Contato(id = id, nome = nome, fone = fone, endereco = endereco, cidade = cidade)
                    dbHelper.updateContato(contatoEditado)
                    atualizarLista()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = ContatoDBHelper(this)

        contatoAdapter = ContatoAdapter(contatosList, { contato ->
            editContato(contato)
        }, { contato ->
            deleteContato(contato)
        })

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = contatoAdapter

        atualizarLista()

        binding.buttonAdd.setOnClickListener {
            val intent = Intent(this, EditContatoActivity::class.java)
            addContactLauncher.launch(intent)
        }

        binding.searchContato.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = contatosList.filter {
                    it.nome.contains(newText ?: "", ignoreCase = true)
                }
                contatoAdapter.updateList(filteredList)
                return true
            }
        })
    }

    private fun atualizarLista() {
        contatosList = dbHelper.getAllContatos().sortedBy { it.nome }.toMutableList()
        contatoAdapter.updateList(contatosList)
    }

    private fun deleteContato(contato: Contato) {
        dbHelper.deleteContato(contato.id)
        atualizarLista()
    }

    private fun editContato(contato: Contato) {
        val intent = Intent(this, EditContatoActivity::class.java).apply {
            putExtra("id", contato.id)
            putExtra("nome", contato.nome)
            putExtra("fone", contato.fone)
            putExtra("endereco", contato.endereco)
            putExtra("cidade", contato.cidade)
        }
        editContactLauncher.launch(intent)
    }
}


