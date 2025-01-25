package com.example.uasdatabase.serviceAPI

import com.example.uasdatabase.model.Lokasi
import com.example.uasdatabase.model.LokasiResponse
import com.example.uasdatabase.model.LokasiResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface LokasiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("lokasi")
    suspend fun getLokasi(): LokasiResponse

    @POST("lokasi/lokasi")
    suspend fun insertLokasi(@Body lokasi: Lokasi)

    @PUT("lokasi/{id}")
    suspend fun updateLokasi(@Path("id") id_lokasi: Int, @Body lokasi: Lokasi)

    @DELETE("lokasi/{id}")
    suspend fun deleteLokasi(@Path("id") id_lokasi: Int): Response<Unit>

    @GET("lokasi/{id}")
    suspend fun getLokasiById(@Path("id") id_lokasi: Int): LokasiResponseDetail
}
