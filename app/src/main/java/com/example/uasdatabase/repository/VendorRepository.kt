package com.example.uasdatabase.repository

import com.example.uasdatabase.model.Vendor
import com.example.uasdatabase.model.VendorResponse
import com.example.uasdatabase.model.VendorResponseDetail
import com.example.uasdatabase.serviceAPI.VendorService
import okio.IOException

interface VendorRepository {

    suspend fun getVendor(): VendorResponse

    suspend fun insertVendor(vendor: Vendor)

    suspend fun updateVendor(idVendor: Int, vendor: Vendor)

    suspend fun deleteVendor(idVendor: Int)

    suspend fun getVendorById(idVendor: Int): VendorResponseDetail
}

class NetworkVendorRepository(
    private val vendorApiService: VendorService
) : VendorRepository {

    override suspend fun insertVendor(vendor: Vendor) {
        vendorApiService.insertVendor(vendor)
    }

    override suspend fun updateVendor(idVendor: Int, vendor: Vendor) {
        vendorApiService.updateVendor(idVendor, vendor)
    }

    override suspend fun deleteVendor(idVendor: Int) {
        try {
            val response = vendorApiService.deleteVendor(idVendor)
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

    override suspend fun getVendor(): VendorResponse {
        return vendorApiService.getVendor()
    }

    override suspend fun getVendorById(idVendor: Int): VendorResponseDetail {
        return vendorApiService.getVendorById(idVendor)
    }
}
