package com.example.uasdatabase.model

import kotlinx.serialization.Serializable

@Serializable
data class Klien(
    val id_klien: Int = 0,
    val nama_klien: String,
    val email_klien: String,
    val notlp_klien: String
)

@Serializable
data class KlienResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Klien
)

@Serializable
data class KlienResponse(
    val status: Boolean,
    val message: String,
    val data: List<Klien>
)
