package com.example.uasdatabase.ui.viewmodel.vendor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasdatabase.model.Vendor
import com.example.uasdatabase.repository.VendorRepository
import kotlinx.coroutines.launch


data class InsertVendorUiState(
    val insertUiEvent: InsertVendorUiEvent = InsertVendorUiEvent(),
)

data class InsertVendorUiEvent(
    val idVendor: Int = 0,
    val namaVendor: String = "",
    val jenisVendor: String = "",
    val emailVendor: String = "",
    val notlpVendor: String = ""
)

fun InsertVendorUiEvent.toVendor(): Vendor = Vendor(
    idVendor = idVendor, // Memastikan idVendor diteruskan dengan benar
    namaVendor = namaVendor,
    jenisVendor = jenisVendor,
    emailVendor = emailVendor,
    notlpVendor = notlpVendor
)

fun Vendor.toUiStateVendor(): InsertVendorUiState = InsertVendorUiState(
    insertUiEvent = toInsertVendorUiEvent()
)

fun Vendor.toInsertVendorUiEvent(): InsertVendorUiEvent = InsertVendorUiEvent(
    idVendor = idVendor, // Memastikan idVendor diteruskan dengan benar
    namaVendor = namaVendor,
    jenisVendor = jenisVendor,
    emailVendor = emailVendor,
    notlpVendor = notlpVendor
)
