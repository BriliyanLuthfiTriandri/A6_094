package com.example.uasdatabase.ui.viewmodel.lokasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasdatabase.model.Lokasi
import com.example.uasdatabase.repository.LokasiRepository
import kotlinx.coroutines.launch

class InsertLokasiViewModel(private val lokasiRepository: LokasiRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertLokasiUiState())
        private set

    fun updateInsertLokasiState(insertUiEvent: InsertLokasiUiEvent) {
        uiState = InsertLokasiUiState(
            insertUiEvent = insertUiEvent
        )
    }

    fun insertLokasi() {
        viewModelScope.launch {
            try {
                lokasiRepository.insertLokasi(uiState.insertUiEvent.toLokasi())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertLokasiUiState(
    val insertUiEvent: InsertLokasiUiEvent = InsertLokasiUiEvent(),
)

data class InsertLokasiUiEvent(
    val id_lokasi: Int = 0,
    val nama_lokasi: String = "",
    val alamat_lokasi: String = "",
    val kapasitas: String = ""
)

fun InsertLokasiUiEvent.toLokasi(): Lokasi = Lokasi(
    id_lokasi = 0,
    nama_lokasi = nama_lokasi,
    alamat_lokasi = alamat_lokasi,
    kapasitas = kapasitas
)

fun Lokasi.toUiStateLokasi(): InsertLokasiUiState = InsertLokasiUiState(
    insertUiEvent = toInsertLokasiUiEvent()
)

fun Lokasi.toInsertLokasiUiEvent(): InsertLokasiUiEvent = InsertLokasiUiEvent(
    id_lokasi = 0,
    nama_lokasi = nama_lokasi,
    alamat_lokasi = alamat_lokasi,
    kapasitas = kapasitas
)
