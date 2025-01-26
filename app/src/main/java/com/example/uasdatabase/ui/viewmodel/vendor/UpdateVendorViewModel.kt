package com.example.uasdatabase.ui.viewmodel.vendor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasdatabase.model.Vendor
import com.example.uasdatabase.repository.VendorRepository
import com.example.uasdatabase.ui.view.vendor.DestinasiUpdateVendor
import kotlinx.coroutines.launch

class UpdateVendorViewModel(
    savedStateHandle: SavedStateHandle,
    private val vendorRepository: VendorRepository
) : ViewModel() {

    var updateUiState by mutableStateOf(InsertVendorUiState())
        private set

    private val _idVendor: Int = checkNotNull(savedStateHandle[DestinasiUpdateVendor.ID_VENDOR])

    init {
        viewModelScope.launch {
            try {
                val vendor = vendorRepository.getVendorById(_idVendor)
                updateUiState = vendor.data.toUiStateVendor()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertVendorState(insertVendorUiEvent: InsertVendorUiEvent) {
        updateUiState = InsertVendorUiState(insertUiEvent = insertVendorUiEvent)
    }

    fun updateVendor() {
        viewModelScope.launch {
            try {
                vendorRepository.updateVendor(
                    _idVendor,
                    updateUiState.insertUiEvent.toVendor()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
