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

