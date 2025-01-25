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

class ContainerApp : AppContainer {

    private val baseUrl = "http://10.0.2.2:3000/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val vendorService: VendorService by lazy {
        retrofit.create(VendorService::class.java)
    }

    private val klienService: KlienService by lazy {
        retrofit.create(KlienService::class.java)
    }

    private val lokasiService: LokasiService by lazy {
        retrofit.create(LokasiService::class.java)
    }

    private val acaraService: AcaraService by lazy {
        retrofit.create(AcaraService::class.java)
    }

    override val AcaraRepository: AcaraRepository by lazy {
        NetworkAcaraRepository(acaraService)
    }

    override val VendorRepository: VendorRepository by lazy {
        NetworkVendorRepository(vendorService)
    }

    override val LokasiRepository: LokasiRepository by lazy {
        NetworkLokasiRepository(lokasiService)
    }

    override val KlienRepository: KlienRepository by lazy {
        NetworkKlienRepository(klienService)
    }
}
