package com.example.uasdatabase.ui.viewmodel.klien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasdatabase.model.Klien
import com.example.uasdatabase.repository.KlienRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeUiStateKlien {
    data class Success(val klien: List<Klien>) : HomeUiStateKlien()
    object Error : HomeUiStateKlien()
    object Loading : HomeUiStateKlien()
}

class HomeKlienViewModel(private val klienRepository: KlienRepository) : ViewModel() {
    var klienUIState: HomeUiStateKlien by mutableStateOf(HomeUiStateKlien.Loading)
        private set

    init {
        getKlien()
    }

    fun getKlien() {
        viewModelScope.launch {
            klienUIState = HomeUiStateKlien.Loading
            klienUIState = try {
                HomeUiStateKlien.Success(klienRepository.getKlien().data)
            } catch (e: IOException) {
                HomeUiStateKlien.Error
            } catch (e: HttpException) {
                HomeUiStateKlien.Error
            }
        }
    }

    fun deleteKlien(id_Klien: Int) {
        viewModelScope.launch {
            try {
                klienRepository.deleteKlien(id_Klien)
                getKlien()
            } catch (e: IOException) {
                klienUIState = HomeUiStateKlien.Error
            } catch (e: HttpException) {
                klienUIState = HomeUiStateKlien.Error
            }
        }
    }
}
