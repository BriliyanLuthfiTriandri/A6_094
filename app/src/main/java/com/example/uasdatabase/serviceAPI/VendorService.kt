package com.example.uasdatabase.serviceAPI

import com.example.uasdatabase.model.Vendor
import com.example.uasdatabase.model.VendorResponse
import com.example.uasdatabase.model.VendorResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface VendorService {
    @Headers(
        "Accpet: application/json",
        "Content-Type: application/json",
    )
    @GET("vendor")
    suspend fun getVendor(): VendorResponse

    @POST("vendor/vendor")
    suspend fun insertVendor(@Body vendor: Vendor)

    @PUT("vendor/{id}")
    suspend fun updateVendor(@Path("id") idVendor: Int, @Body vendor: Vendor)

    @DELETE("vendor/{id}")
    suspend fun deleteVendor(@Path("id") idVendor: Int): Response<Unit>

    @GET("vendor/{id}")
    suspend fun getVendorById(@Path("id") idVendor: Int): VendorResponseDetail
}
