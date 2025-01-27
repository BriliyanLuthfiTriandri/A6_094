package com.example.uasdatabase.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uasdatabase.AcaraApplication
import com.example.uasdatabase.ui.viewmodel.acara.DetailAcaraViewModel
import com.example.uasdatabase.ui.viewmodel.acara.HomeAcaraViewModel
import com.example.uasdatabase.ui.viewmodel.acara.InsertAcaraViewModel
import com.example.uasdatabase.ui.viewmodel.acara.UpdateAcaraViewModel
import com.example.uasdatabase.ui.viewmodel.klien.DetailKlienViewModel
import com.example.uasdatabase.ui.viewmodel.klien.HomeKlienViewModel
import com.example.uasdatabase.ui.viewmodel.klien.InsertKlienViewModel
import com.example.uasdatabase.ui.viewmodel.klien.UpdateKlienViewModel
import com.example.uasdatabase.ui.viewmodel.lokasi.DetailLokasiViewModel
import com.example.uasdatabase.ui.viewmodel.lokasi.HomeLokasiViewModel
import com.example.uasdatabase.ui.viewmodel.lokasi.InsertLokasiViewModel
import com.example.uasdatabase.ui.viewmodel.lokasi.UpdateLokasiViewModel
import com.example.uasdatabase.ui.viewmodel.vendor.DetailVendorViewModel
import com.example.uasdatabase.ui.viewmodel.vendor.HomeVendorViewModel
import com.example.uasdatabase.ui.viewmodel.vendor.InsertVendorViewModel
import com.example.uasdatabase.ui.viewmodel.vendor.UpdateVendorViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {

        // Vendor
        initializer { HomeVendorViewModel(aplikasiAcara().container.VendorRepository) }
        initializer { InsertVendorViewModel(aplikasiAcara().container.VendorRepository) }
        initializer { DetailVendorViewModel(aplikasiAcara().container.VendorRepository) }
        initializer { UpdateVendorViewModel(createSavedStateHandle(),aplikasiAcara().container.VendorRepository) }

        // Lokasi
        initializer { HomeLokasiViewModel(aplikasiAcara().container.LokasiRepository) }
        initializer { InsertLokasiViewModel(aplikasiAcara().container.LokasiRepository) }
        initializer { DetailLokasiViewModel(aplikasiAcara().container.LokasiRepository) }
        initializer { UpdateLokasiViewModel(createSavedStateHandle(),aplikasiAcara().container.LokasiRepository) }


        // Klien
        initializer { HomeKlienViewModel(aplikasiAcara().container.KlienRepository) }
        initializer { InsertKlienViewModel(aplikasiAcara().container.KlienRepository) }
        initializer { DetailKlienViewModel(aplikasiAcara().container.KlienRepository) }
        initializer { UpdateKlienViewModel(createSavedStateHandle(),aplikasiAcara().container.KlienRepository) }

        //Acara
        initializer { HomeAcaraViewModel(aplikasiAcara().container.AcaraRepository) }
        initializer { InsertAcaraViewModel(aplikasiAcara().container.AcaraRepository)}
        initializer { DetailAcaraViewModel(aplikasiAcara().container.AcaraRepository) }
        initializer { UpdateAcaraViewModel(createSavedStateHandle(),aplikasiAcara().container.AcaraRepository) }

    }
}

fun CreationExtras.aplikasiAcara(): AcaraApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AcaraApplication)