package com.example.uasdatabase.ui.viewmodel.vendor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasdatabase.model.Vendor
import com.example.uasdatabase.repository.VendorRepository
import kotlinx.coroutines.launch

class InsertVendorViewModel(private val vendorRepository: VendorRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertVendorUiState())
        private set

    fun updateInsertVendorState(insertUiEvent: InsertVendorUiEvent) {
        uiState = InsertVendorUiState(
            insertUiEvent = insertUiEvent
        )
    }

    fun insertVendor() {
        viewModelScope.launch {
            try {
                vendorRepository.insertVendor(uiState.insertUiEvent.toVendor())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

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
    idVendor = idVendor,
    namaVendor = namaVendor,
    jenisVendor = jenisVendor,
    emailVendor = emailVendor,
    notlpVendor = notlpVendor
)

fun Vendor.toUiStateVendor(): InsertVendorUiState = InsertVendorUiState(
    insertUiEvent = toInsertVendorUiEvent()
)

fun Vendor.toInsertVendorUiEvent(): InsertVendorUiEvent = InsertVendorUiEvent(
    idVendor = idVendor,
    namaVendor = namaVendor,
    jenisVendor = jenisVendor,
    emailVendor = emailVendor,
    notlpVendor = notlpVendor
)
