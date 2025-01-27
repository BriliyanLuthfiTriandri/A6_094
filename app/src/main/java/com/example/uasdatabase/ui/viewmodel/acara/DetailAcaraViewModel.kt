package com.example.uasdatabase.ui.viewmodel.acara

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasdatabase.model.Acara
import com.example.uasdatabase.repository.AcaraRepository
import kotlinx.coroutines.launch

class DetailAcaraViewModel(private val acaraRepository: AcaraRepository) : ViewModel() {

    var uiState by mutableStateOf(DetailAcaraUiState())
        private set

    fun fetchDetailAcara(idAcara: Int) {
        viewModelScope.launch {
            uiState = DetailAcaraUiState(isLoading = true)
            try {
                val acaraResponse = acaraRepository.getAcaraById(idAcara)
                uiState =
                    DetailAcaraUiState(detailUiEvent = acaraResponse.data.toDetailAcaraUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = DetailAcaraUiState(
                    isError = true,
                    errorMessage = "Failed to fetch acara details: ${e.message}"
                )
            }
        }
    }

}

data class DetailAcaraUiState(
    val detailUiEvent: InsertAcaraUiEvent = InsertAcaraUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != InsertAcaraUiEvent()
}

fun Acara.toDetailAcaraUiEvent(): InsertAcaraUiEvent {
    return InsertAcaraUiEvent(
        id_acara = id_acara,
        id_lokasi = id_lokasi,
        id_klien = id_klien,
        nama_acara = nama_acara,
        deskripsi_acara = deskripsi_acara,
        tanggal_mulai = tanggal_mulai,
        tanggal_berakhir = tanggal_berakhir,
        status_acara = status_acara,
    )
}
