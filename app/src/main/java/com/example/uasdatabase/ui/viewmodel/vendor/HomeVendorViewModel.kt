package com.example.uasdatabase.ui.viewmodel.vendor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasdatabase.model.Vendor
import com.example.uasdatabase.repository.VendorRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeUiStateVendor {
    data class Success(val vendors: List<Vendor>) : HomeUiStateVendor()
    object Error : HomeUiStateVendor()
    object Loading : HomeUiStateVendor()
}

