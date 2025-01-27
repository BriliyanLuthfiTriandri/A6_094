package com.example.uasdatabase.model

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uasdatabase.ui.viewmodel.PenyediaViewModel
import com.example.uasdatabase.ui.viewmodel.klien.HomeKlienViewModel
import com.example.uasdatabase.ui.viewmodel.klien.HomeUiStateKlien
import com.example.uasdatabase.ui.viewmodel.lokasi.HomeLokasiViewModel
import com.example.uasdatabase.ui.viewmodel.lokasi.HomeUiStateLokasi

object ListKL {
    @Composable
    fun DataKlien(
        klienHome: HomeKlienViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ): List<Pair<Int, String>> {
        val klienState = klienHome.klienUIState

        return when (klienState) {
            is HomeUiStateKlien.Success -> {
                klienState.klien.map { it.id_klien to it.nama_klien }
            }
            else -> {
                emptyList()
            }
        }
    }

    @Composable
    fun DataLokasi(
        lokasiHome: HomeLokasiViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ): List<Pair<Int, String>> {
        val lokasiState = lokasiHome.lokasiUIState

        return when (lokasiState) {
            is HomeUiStateLokasi.Success -> {
                lokasiState.lokasi.map { it.id_lokasi to it.nama_lokasi }
            }
            else -> {
                emptyList()
            }
        }
    }
}


