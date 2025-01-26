package com.example.uasdatabase.ui.viewmodel.vendor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasdatabase.model.Vendor
import com.example.uasdatabase.repository.VendorRepository
import kotlinx.coroutines.launch



fun Vendor.toDetailVendorUiEvent(): InsertVendorUiEvent {
    return InsertVendorUiEvent(
        idVendor = idVendor,
        namaVendor = namaVendor,
        jenisVendor = jenisVendor,
        emailVendor = emailVendor,
        notlpVendor = notlpVendor
    )
}
