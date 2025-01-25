package com.example.uasdatabase.model

import kotlinx.serialization.Serializable

@Serializable
data class Lokasi(
    val id_lokasi: Int = 0,
    val nama_lokasi: String,
    val alamat_lokasi: String,
    val kapasitas: String
)

@Serializable
data class LokasiResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Lokasi
)
