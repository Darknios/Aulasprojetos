package com.example.agenda

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.agenda.databinding.ActivityEditContatoBinding

class EditContatoActivity : ComponentActivity() {

    private lateinit var binding: ActivityEditContatoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditContatoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recuperando os dados do Intent
        val contatoId = intent.getLongExtra("id", -1)
        val nome = intent.getStringExtra("nome") ?: ""
        val fone = intent.getStringExtra("fone") ?: ""
        val endereco = intent.getStringExtra("endereco") ?: ""
        val cidade = intent.getStringExtra("cidade") ?: ""

        // Preenchendo os campos com os dados recebidos
        binding.editTextNome.setText(nome)
        binding.editTextFone.setText(fone)
        binding.editTextEndereco.setText(endereco)
        binding.editTextCidade.setText(cidade)

        // Salvando as alterações no botão de salvar
        binding.buttonSave.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("id", contatoId)
                putExtra("nome", binding.editTextNome.text.toString())
                putExtra("fone", binding.editTextFone.text.toString())
                putExtra("endereco", binding.editTextEndereco.text.toString())
                putExtra("cidade", binding.editTextCidade.text.toString())
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
