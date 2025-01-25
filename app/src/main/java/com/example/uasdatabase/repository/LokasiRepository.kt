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

class NetworkLokasiRepository(
    private val lokasiApiService: LokasiService
) : LokasiRepository {

    override suspend fun insertLokasi(lokasi: Lokasi) {
        lokasiApiService.insertLokasi(lokasi)
    }

    override suspend fun updateLokasi(id_lokasi: Int, lokasi: Lokasi) {
        lokasiApiService.updateLokasi(id_lokasi, lokasi)
    }

    override suspend fun deleteLokasi(id_lokasi: Int) {
        try {
            val response = lokasiApiService.deleteLokasi(id_lokasi)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Vendor. HTTP Status code: " +
                            "${response.code()}"
                )
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getLokasi(): LokasiResponse {
        return lokasiApiService.getLokasi()
    }

    override suspend fun getLokasiById(id_lokasi: Int): LokasiResponseDetail {
        return lokasiApiService.getLokasiById(id_lokasi)
    }
}
