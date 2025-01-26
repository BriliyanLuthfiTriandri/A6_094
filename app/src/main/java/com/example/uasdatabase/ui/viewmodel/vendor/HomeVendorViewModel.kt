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

class HomeVendorViewModel(private val vendorRepository: VendorRepository) : ViewModel() {
    var vendorUIState: HomeUiStateVendor by mutableStateOf(HomeUiStateVendor.Loading)
        private set

    init {
        getVendor()
    }

    fun getVendor() {
        viewModelScope.launch {
            vendorUIState = HomeUiStateVendor.Loading
            vendorUIState = try {
                HomeUiStateVendor.Success(vendorRepository.getVendor().data)
            } catch (e: IOException) {
                HomeUiStateVendor.Error
            } catch (e: HttpException) {
                HomeUiStateVendor.Error
            }
        }
    }

    fun deleteVendor(idVendor: Int) {
        viewModelScope.launch {
            try {
                vendorRepository.deleteVendor(idVendor)
                getVendor()
            } catch (e: IOException) {
                vendorUIState = HomeUiStateVendor.Error
            } catch (e: HttpException) {
                vendorUIState = HomeUiStateVendor.Error
            }
        }
    }
}
