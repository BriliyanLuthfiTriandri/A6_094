package com.example.uasdatabase.model

import kotlinx.serialization.Serializable

@Serializable
data class Klien(
    val id_klien: Int = 0,
    val nama_klien: String,
    val email_klien: String,
    val notlp_klien: String
)

