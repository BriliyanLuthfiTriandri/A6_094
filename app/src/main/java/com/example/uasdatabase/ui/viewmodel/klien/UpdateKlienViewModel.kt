package com.example.uasdatabase.ui.viewmodel.klien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasdatabase.repository.KlienRepository
import com.example.uasdatabase.ui.view.klien.DestinasiUpdateKlien
import kotlinx.coroutines.launch

class UpdateKlienViewModel(
    savedStateHandle: SavedStateHandle,
    private val klienRepository: KlienRepository
) : ViewModel() {

    var updateUiState by mutableStateOf(InsertKlienUiState())
        private set

    private val _idKlien: Int = checkNotNull(savedStateHandle[DestinasiUpdateKlien.ID_KLIEN])

    init {
        viewModelScope.launch {
            try {
                val klien = klienRepository.getKlienById(_idKlien)
                updateUiState = klien.data.toUiStateKlien()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertKlienState(insertKlienUiEvent: InsertKlienUiEvent) {
        updateUiState = InsertKlienUiState(insertUiEvent = insertKlienUiEvent)
    }

    fun updateKlien() {
        viewModelScope.launch {
            try {
                klienRepository.updateKlien(
                    _idKlien,
                    updateUiState.insertUiEvent.toKlien()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
