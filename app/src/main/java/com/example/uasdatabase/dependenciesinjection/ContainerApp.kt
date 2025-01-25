package com.example.uasdatabase.dependenciesinjection

import com.example.uasdatabase.repository.AcaraRepository
import com.example.uasdatabase.repository.KlienRepository
import com.example.uasdatabase.repository.LokasiRepository
import com.example.uasdatabase.repository.NetworkAcaraRepository
import com.example.uasdatabase.repository.NetworkKlienRepository
import com.example.uasdatabase.repository.NetworkLokasiRepository
import com.example.uasdatabase.repository.NetworkVendorRepository
import com.example.uasdatabase.repository.VendorRepository
import com.example.uasdatabase.serviceAPI.AcaraService
import com.example.uasdatabase.serviceAPI.KlienService
import com.example.uasdatabase.serviceAPI.LokasiService
import com.example.uasdatabase.serviceAPI.VendorService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {

    val AcaraRepository : AcaraRepository
    val KlienRepository: KlienRepository
    val LokasiRepository: LokasiRepository
    val VendorRepository: VendorRepository
}


