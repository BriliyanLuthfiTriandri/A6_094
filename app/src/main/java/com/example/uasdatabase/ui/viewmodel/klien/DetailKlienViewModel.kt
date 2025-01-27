package com.example.uasdatabase.ui.viewmodel.klien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasdatabase.model.Klien
import com.example.uasdatabase.repository.KlienRepository
import kotlinx.coroutines.launch

class DetailKlienViewModel(private val klienRepository: KlienRepository) : ViewModel() {

    var uiState by mutableStateOf(DetailKlienUiState())
        private set

    fun fetchDetailKlien(idKlien: Int) {
        viewModelScope.launch {
            uiState = DetailKlienUiState(isLoading = true)
            try {
                val klienResponse = klienRepository.getKlienById(idKlien)
                uiState = DetailKlienUiState(detailUiEvent = klienResponse.data.toDetailKlienUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = DetailKlienUiState(
                    isError = true,
                    errorMessage = "Failed to fetch klien details: ${e.message}"
                )
            }
        }
    }
}

data class DetailKlienUiState(
    val detailUiEvent: InsertKlienUiEvent = InsertKlienUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != InsertKlienUiEvent()
}

fun Klien.toDetailKlienUiEvent(): InsertKlienUiEvent {
    return InsertKlienUiEvent(
        id_klien = id_klien,
        nama_klien = nama_klien,
        email_klien = email_klien,
        notlp_klien = notlp_klien
    )
}
