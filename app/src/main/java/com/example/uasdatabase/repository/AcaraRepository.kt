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

