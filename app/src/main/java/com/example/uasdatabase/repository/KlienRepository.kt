package com.example.uasdatabase.repository

import com.example.uasdatabase.model.Klien
import com.example.uasdatabase.model.KlienResponse
import com.example.uasdatabase.model.KlienResponseDetail
import com.example.uasdatabase.serviceAPI.KlienService
import okio.IOException

interface KlienRepository {

    suspend fun getKlien(): KlienResponse

    suspend fun insertKlien(klien: Klien)

    suspend fun updateKlien(id_klien: Int, klien: Klien)

    suspend fun deleteKlien(id_klien: Int)

    suspend fun getKlienById(id_klien: Int): KlienResponseDetail
}

