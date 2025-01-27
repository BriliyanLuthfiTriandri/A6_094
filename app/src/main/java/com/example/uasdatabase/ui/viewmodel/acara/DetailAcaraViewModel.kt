package com.example.uasdatabase.ui.viewmodel.acara

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasdatabase.model.Acara
import com.example.uasdatabase.repository.AcaraRepository
import kotlinx.coroutines.launch



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
