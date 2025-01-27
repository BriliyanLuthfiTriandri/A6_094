package com.example.uasdatabase.ui.viewmodel.lokasi


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasdatabase.model.Lokasi
import com.example.uasdatabase.repository.LokasiRepository
import kotlinx.coroutines.launch


fun Lokasi.toDetailLokasiUiEvent(): InsertLokasiUiEvent {
    return InsertLokasiUiEvent(
        id_lokasi = id_lokasi,
        nama_lokasi = nama_lokasi,
        alamat_lokasi = alamat_lokasi,
        kapasitas = kapasitas
    )
}
