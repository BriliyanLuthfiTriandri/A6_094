package com.example.uasdatabase.ui.viewmodel.acara

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasdatabase.model.Acara
import com.example.uasdatabase.repository.AcaraRepository
import kotlinx.coroutines.launch

class InsertAcaraViewModel(private val acaraRepository: AcaraRepository
) : ViewModel() {
    var uiState by mutableStateOf(InsertAcaraUiState())
        private set

    fun updateInsertAcaraState(insertUiEvent: InsertAcaraUiEvent) {
        uiState = InsertAcaraUiState(
            insertUiEvent = insertUiEvent
        )
    }

    fun insertAcara() {
        viewModelScope.launch {
            try {
                acaraRepository.insertAcara(uiState.insertUiEvent.toAcara())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertAcaraUiState(
    val insertUiEvent: InsertAcaraUiEvent = InsertAcaraUiEvent(),
)

data class InsertAcaraUiEvent(
    val id_acara: Int = 0,
    val id_lokasi: Int = 0,
    val id_klien: Int = 0,
    val nama_acara: String = "",
    val deskripsi_acara: String = "",
    val tanggal_mulai: String = "",
    val tanggal_berakhir: String = "",
    val status_acara: String = "",

)

fun InsertAcaraUiEvent.toAcara(): Acara = Acara(
    id_acara = id_acara,
    id_lokasi = id_lokasi,
    id_klien = id_klien,
    nama_acara = nama_acara,
    deskripsi_acara = deskripsi_acara,
    tanggal_mulai = tanggal_mulai,
    tanggal_berakhir = tanggal_berakhir,
    status_acara = status_acara,

)

fun Acara.toUiStateAcara(): InsertAcaraUiState = InsertAcaraUiState(
    insertUiEvent = toInsertAcaraUiEvent()
)

fun Acara.toInsertAcaraUiEvent(): InsertAcaraUiEvent = InsertAcaraUiEvent(
    id_acara = id_acara,
    id_lokasi = id_lokasi,
    id_klien = id_klien,
    nama_acara = nama_acara,
    deskripsi_acara = deskripsi_acara,
    tanggal_mulai = tanggal_mulai,
    tanggal_berakhir = tanggal_berakhir,
    status_acara = status_acara,

)
