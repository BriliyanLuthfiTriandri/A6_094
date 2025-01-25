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

class NetworkKlienRepository(
    private val klienApiService: KlienService
) : KlienRepository {

    override suspend fun insertKlien(klien: Klien) {
        klienApiService.insertKlien(klien)
    }

    override suspend fun updateKlien(id_klien: Int, klien: Klien) {
        klienApiService.updateKlien(id_klien, klien)
    }

    override suspend fun deleteKlien(id_klien: Int) {
        try {
            val response = klienApiService.deleteKlien(id_klien)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Klien. HTTP Status code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getKlien(): KlienResponse {
        return klienApiService.getKlien()
    }

    override suspend fun getKlienById(id_klien: Int): KlienResponseDetail {
        return klienApiService.getKlienById(id_klien)
    }
}
