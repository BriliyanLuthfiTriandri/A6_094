package com.example.uasdatabase.ui.viewmodel.lokasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasdatabase.model.Lokasi
import com.example.uasdatabase.repository.LokasiRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeUiStateLokasi {
    data class Success(val lokasi: List<Lokasi>) : HomeUiStateLokasi()
    object Error : HomeUiStateLokasi()
    object Loading : HomeUiStateLokasi()
}

class HomeLokasiViewModel(private val lokasiRepository: LokasiRepository) : ViewModel() {

    var lokasiUIState: HomeUiStateLokasi by mutableStateOf(HomeUiStateLokasi.Loading)
        private set

    init {
        getLokasi()
    }

    fun getLokasi() {
        viewModelScope.launch {
            lokasiUIState = HomeUiStateLokasi.Loading
            lokasiUIState = try {
                HomeUiStateLokasi.Success(lokasiRepository.getLokasi().data)
            } catch (e: IOException) {
                HomeUiStateLokasi.Error
            } catch (e: HttpException) {
                HomeUiStateLokasi.Error
            }
        }
    }

    fun deleteLokasi(id_Lokasi: Int) {
        viewModelScope.launch {
            try {
                lokasiRepository.deleteLokasi(id_Lokasi)
                getLokasi()
            } catch (e: IOException) {
                lokasiUIState = HomeUiStateLokasi.Error
            } catch (e: HttpException) {
                lokasiUIState = HomeUiStateLokasi.Error
            }
        }
    }
}
