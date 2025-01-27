package com.example.uasdatabase.ui.viewmodel.klien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasdatabase.model.Klien
import com.example.uasdatabase.repository.KlienRepository
import kotlinx.coroutines.launch



fun Klien.toInsertKlienUiEvent(): InsertKlienUiEvent = InsertKlienUiEvent(
    id_klien = 0,
    nama_klien = nama_klien,
    email_klien = email_klien,
    notlp_klien = notlp_klien
)
