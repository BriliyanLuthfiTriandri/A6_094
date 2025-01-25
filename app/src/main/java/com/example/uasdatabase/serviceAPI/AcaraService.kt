package com.example.uasdatabase.serviceAPI

import com.example.uasdatabase.model.Acara
import com.example.uasdatabase.model.AcaraResponse
import com.example.uasdatabase.model.AcaraResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AcaraService {
    @Headers(
        "Accpet: application/json",
        "Content-Type: application/json",
    )
    @GET("acara")
    suspend fun getAcara(): AcaraResponse

    @POST("acara/acara")
    suspend fun insertAcara(@Body acara: Acara)

    @PUT("acara/{id}")
    suspend fun updateAcara(@Path("id") id_acara: Int, @Body acara: Acara)

    @DELETE("acara/{id}")
    suspend fun deleteAcara(@Path("id") id_acara: Int): Response<Unit>

    @GET("acara/{id}")
    suspend fun getAcaraById(@Path("id") id_acara: Int): AcaraResponseDetail
}
