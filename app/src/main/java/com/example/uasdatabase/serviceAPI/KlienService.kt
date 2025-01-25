package com.example.uasdatabase.serviceAPI

import com.example.uasdatabase.model.Klien
import com.example.uasdatabase.model.KlienResponse
import com.example.uasdatabase.model.KlienResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KlienService {
    @Headers(
        "Accpet: application/json",
        "Content-Type: application/json",
    )
    @GET("klien")
    suspend fun getKlien(): KlienResponse

    @POST("klien/klien")
    suspend fun insertKlien(@Body klien: Klien)

    @PUT("klien/{id}")
    suspend fun updateKlien(@Path("id") id_klien: Int, @Body klien: Klien)

    @DELETE("klien/{id}")
    suspend fun deleteKlien(@Path("id") id_klien: Int): Response<Unit>

    @GET("klien/{id}")
    suspend fun getKlienById(@Path("id") id_klien: Int): KlienResponseDetail
}
