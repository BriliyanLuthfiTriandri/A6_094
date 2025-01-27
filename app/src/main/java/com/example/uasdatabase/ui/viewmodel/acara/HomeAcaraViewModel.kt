package com.example.uasdatabase.ui.viewmodel.acara

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasdatabase.model.Acara
import com.example.uasdatabase.repository.AcaraRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeUiStateAcara {
    data class Success(val acara: List<Acara>) : HomeUiStateAcara()
    object Error : HomeUiStateAcara()
    object Loading : HomeUiStateAcara()
}

class HomeAcaraViewModel(private val acaraRepository: AcaraRepository) : ViewModel() {
    var acaraUIState: HomeUiStateAcara by mutableStateOf(HomeUiStateAcara.Loading)
        private set

    init {
        getAcara()
    }

    fun getAcara() {
        viewModelScope.launch {
            acaraUIState = HomeUiStateAcara.Loading
            acaraUIState = try {
                HomeUiStateAcara.Success(acaraRepository.getAcara().data)
            } catch (e: IOException) {
                HomeUiStateAcara.Error
            } catch (e: HttpException) {
                HomeUiStateAcara.Error
            }
        }
    }

    fun deleteAcara(idAcara: Int) {
        viewModelScope.launch {
            try {
                acaraRepository.deleteAcara(idAcara)
                getAcara()
            } catch (e: IOException) {
                acaraUIState = HomeUiStateAcara.Error
            } catch (e: HttpException) {
                acaraUIState = HomeUiStateAcara.Error
            }
        }
    }
}
