package com.example.uasdatabase.repository

import com.example.uasdatabase.model.Lokasi
import com.example.uasdatabase.model.LokasiResponse
import com.example.uasdatabase.model.LokasiResponseDetail
import com.example.uasdatabase.serviceAPI.LokasiService
import okio.IOException

interface LokasiRepository {

    suspend fun getLokasi(): LokasiResponse

    suspend fun insertLokasi(lokasi: Lokasi)

    suspend fun updateLokasi(id_lokasi: Int, lokasi: Lokasi)

    suspend fun deleteLokasi(id_lokasi: Int)

    suspend fun getLokasiById(id_lokasi: Int): LokasiResponseDetail
}


