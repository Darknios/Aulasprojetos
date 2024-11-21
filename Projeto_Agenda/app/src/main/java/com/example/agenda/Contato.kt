package com.example.agenda

data class Contato(
    val id: Long = 0,
    val nome: String,
    val fone: String,
    val endereco: String = "",
    val cidade: String = ""
)

