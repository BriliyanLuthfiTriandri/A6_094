package com.example.uasdatabase.ui.viewmodel.vendor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasdatabase.model.Vendor
import com.example.uasdatabase.repository.VendorRepository
import kotlinx.coroutines.launch

class DetailVendorViewModel(private val vendorRepository: VendorRepository) : ViewModel() {

    var uiState by mutableStateOf(DetailVendorUiState())
        private set

    fun fetchDetailVendor(idVendor: Int) {
        viewModelScope.launch {
            uiState = DetailVendorUiState(isLoading = true)
            try {
                val vendor = vendorRepository.getVendorById(idVendor)
                uiState = DetailVendorUiState(detailUiEvent = vendor.data.toDetailVendorUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = DetailVendorUiState(
                    isError = true,
                    errorMessage = "Failed to fetch vendor details: ${e.message}"
                )
            }
        }
    }
}

data class DetailVendorUiState(
    val detailUiEvent: InsertVendorUiEvent = InsertVendorUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != InsertVendorUiEvent()
}

fun Vendor.toDetailVendorUiEvent(): InsertVendorUiEvent {
    return InsertVendorUiEvent(
        idVendor = idVendor,
        namaVendor = namaVendor,
        jenisVendor = jenisVendor,
        emailVendor = emailVendor,
        notlpVendor = notlpVendor
    )
}
