package com.example.uasdatabase.ui.viewmodel.klien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasdatabase.model.Klien
import com.example.uasdatabase.repository.KlienRepository
import kotlinx.coroutines.launch



data class InsertKlienUiState(
    val insertUiEvent: InsertKlienUiEvent = InsertKlienUiEvent(),
)

data class InsertKlienUiEvent(
    val id_klien: Int = 0,
    val nama_klien: String = "",
    val email_klien: String = "",
    val notlp_klien: String = ""
)

fun InsertKlienUiEvent.toKlien(): Klien = Klien(
    id_klien = 0,
    nama_klien = nama_klien,
    email_klien = email_klien,
    notlp_klien = notlp_klien
)

fun Klien.toUiStateKlien(): InsertKlienUiState = InsertKlienUiState(
    insertUiEvent = toInsertKlienUiEvent()
)

fun Klien.toInsertKlienUiEvent(): InsertKlienUiEvent = InsertKlienUiEvent(
    id_klien = 0,
    nama_klien = nama_klien,
    email_klien = email_klien,
    notlp_klien = notlp_klien
)
