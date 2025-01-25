package com.example.uasdatabase.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Vendor(
    @SerialName("id_vendor")
    val idVendor: Int = 0,
    @SerialName("nama_vendor")
    val namaVendor: String,
    @SerialName("jenis_vendor")
    val jenisVendor: String,
    @SerialName("email_vendor")
    val emailVendor: String,
    @SerialName("notlp_vendor")
    val notlpVendor: String
)

@Serializable
data class VendorResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Vendor
)


