package com.example.uasdatabase.ui.viewmodel.lokasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasdatabase.repository.LokasiRepository
import com.example.uasdatabase.ui.view.lokasi.DestinasiUpdateLokasi
import kotlinx.coroutines.launch

class UpdateLokasiViewModel(
    savedStateHandle: SavedStateHandle,
    private val lokasiRepository: LokasiRepository
) : ViewModel() {

    var updateUiState by mutableStateOf(InsertLokasiUiState())
        private set

    private val _idLokasi: Int = checkNotNull(savedStateHandle[DestinasiUpdateLokasi.ID_LOKASI])

    init {
        viewModelScope.launch {
            try {
                val lokasi = lokasiRepository.getLokasiById(_idLokasi)
                updateUiState = lokasi.data.toUiStateLokasi()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertLokasiState(insertLokasiUiEvent: InsertLokasiUiEvent) {
        updateUiState = InsertLokasiUiState(insertUiEvent = insertLokasiUiEvent)
    }

    fun updateLokasi() {
        viewModelScope.launch {
            try {
                lokasiRepository.updateLokasi(
                    _idLokasi,
                    updateUiState.insertUiEvent.toLokasi()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}