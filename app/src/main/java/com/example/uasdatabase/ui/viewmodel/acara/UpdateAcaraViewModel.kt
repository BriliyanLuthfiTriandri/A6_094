package com.example.uasdatabase.ui.viewmodel.acara

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasdatabase.repository.AcaraRepository
import com.example.uasdatabase.ui.view.acara.DestinasiUpdateAcara
import kotlinx.coroutines.launch

class UpdateAcaraViewModel(
    savedStateHandle: SavedStateHandle,
    private val acaraRepository: AcaraRepository
) : ViewModel() {

    var updateUiState by mutableStateOf(InsertAcaraUiState())
        private set

    private val _idAcara: Int = checkNotNull(savedStateHandle[DestinasiUpdateAcara.ID_ACARA])

    init {
        viewModelScope.launch {
            try {
                val acara = acaraRepository.getAcaraById(_idAcara)
                updateUiState = acara.data.toUiStateAcara()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertAcaraState(insertAcaraUiEvent: InsertAcaraUiEvent) {
        updateUiState = InsertAcaraUiState(insertUiEvent = insertAcaraUiEvent)
    }

    fun updateAcara() {
        viewModelScope.launch {
            try {
                acaraRepository.updateAcara(
                    _idAcara,
                    updateUiState.insertUiEvent.toAcara()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
