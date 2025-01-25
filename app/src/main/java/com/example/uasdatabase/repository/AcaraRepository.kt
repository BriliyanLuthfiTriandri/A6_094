package com.example.uasdatabase.repository

import com.example.uasdatabase.model.Acara
import com.example.uasdatabase.model.AcaraResponse
import com.example.uasdatabase.model.AcaraResponseDetail
import com.example.uasdatabase.serviceAPI.AcaraService
import okio.IOException

interface AcaraRepository {

    suspend fun getAcara(): AcaraResponse

    suspend fun insertAcara(acara: Acara)

    suspend fun updateAcara(id_acara: Int, acara: Acara)

    suspend fun deleteAcara(id_acara: Int)

    suspend fun getAcaraById(id_acara: Int): AcaraResponseDetail
}

class NetworkAcaraRepository(
    private val acaraApiService: AcaraService
) : AcaraRepository {

    override suspend fun insertAcara(acara: Acara) {
        acaraApiService.insertAcara(acara)
    }

    override suspend fun updateAcara(id_acara: Int, acara: Acara) {
        acaraApiService.updateAcara(id_acara, acara)
    }

    override suspend fun deleteAcara(id_acara: Int) {
        try {
            val response = acaraApiService.deleteAcara(id_acara)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Acara. HTTP Status code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAcara(): AcaraResponse {
        return acaraApiService.getAcara()
    }

    override suspend fun getAcaraById(id_acara: Int): AcaraResponseDetail {
        return acaraApiService.getAcaraById(id_acara)
    }
}