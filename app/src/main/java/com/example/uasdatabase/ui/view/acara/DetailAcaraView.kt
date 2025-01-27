package com.example.uasdatabase.ui.view.acara

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uasdatabase.model.ListKL
import com.example.uasdatabase.ui.customwidget.CostumeTopAppBar
import com.example.uasdatabase.ui.navigation.AlamatNavigasi
import com.example.uasdatabase.ui.viewmodel.PenyediaViewModel
import com.example.uasdatabase.ui.viewmodel.acara.DetailAcaraViewModel

object DestinasiDetailAcara : AlamatNavigasi {
    override val route = "detailacara"
    override val titleRes = "Detail Acara"
    const val ID_ACARA = "id_acara"
    val routeWithArg = "$route/{$ID_ACARA}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAcaraScreen(
    onBackClick: () -> Unit = { },
    idAcara: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailAcaraViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val acara = viewModel.uiState.detailUiEvent
    val isLoading = viewModel.uiState.isLoading
    val isError = viewModel.uiState.isError
    val errorMessage = viewModel.uiState.errorMessage
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val klienList = ListKL.DataKlien()
    val lokasiList = ListKL.DataLokasi()

    val namaKlien = klienList.find { it.first == acara.id_klien }?.second ?: "Unknown Client"
    val namaLokasi = lokasiList.find { it.first == acara.id_lokasi }?.second ?: "Unknown Location"

    LaunchedEffect(idAcara) {
        viewModel.fetchDetailAcara(idAcara)
    }

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailAcara.titleRes,
                canNavigateBack = true,
                showMenu = false,
                showRefresh = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.fetchDetailAcara(idAcara)
                },
                navigateUp = onBackClick
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (isError) {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else if (viewModel.uiState.isUiEventNotEmpty) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                DetailRow(label = "ID Acara", value = acara.id_acara.toString())
                                DetailRow(label = "Nama Acara", value = acara.nama_acara)
                                DetailRow(label = "Deskripsi", value = acara.deskripsi_acara)
                                DetailRow(label = "Tgl Mulai", value = acara.tanggal_mulai)
                                DetailRow(label = "Tgl Berakhir", value = acara.tanggal_berakhir)
                                DetailRow(label = "Nama Klien", value = namaKlien)
                                DetailRow(label = "Nama Lokasi", value = namaLokasi)
                                DetailRow(label = "Status Acara", value = acara.status_acara)
                            }
                        }
                    }
                }
            }
        }
    )
}


@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = ": $value",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(2f)
        )
    }
}
