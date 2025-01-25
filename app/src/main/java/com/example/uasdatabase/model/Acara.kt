package com.example.uasdatabase.model

import kotlinx.serialization.Serializable

@Serializable
data class Acara(
    val id_acara: Int = 0,
    val id_lokasi: Int = 0,
    val id_klien: Int = 0,
    val nama_acara: String,
    val deskripsi_acara: String,
    val tanggal_mulai: String,
    val tanggal_berakhir: String,
    val status_acara: String,
)



