package com.example.uasdatabase.ui.view.lokasi

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
import com.example.uasdatabase.ui.customwidget.CostumeTopAppBar
import com.example.uasdatabase.ui.navigation.AlamatNavigasi
import com.example.uasdatabase.ui.viewmodel.PenyediaViewModel
import com.example.uasdatabase.ui.viewmodel.lokasi.DetailLokasiViewModel

object DestinasiDetailLokasi : AlamatNavigasi {
    override val route = "detaillokasi"
    override val titleRes = "Detail Lokasi"
    const val ID_LOKASI = "id_lokasi"
    val routeWithArg = "$route/{$ID_LOKASI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailLokasiScreen(
    onBackClick: () -> Unit = { },
    idLokasi: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailLokasiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val lokasi = viewModel.uiState.detailUiEvent
    val isLoading = viewModel.uiState.isLoading
    val isError = viewModel.uiState.isError
    val errorMessage = viewModel.uiState.errorMessage
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idLokasi) {
        viewModel.fetchDetailLokasi(idLokasi)
    }

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailLokasi.titleRes,
                canNavigateBack = true,
                showMenu = false,
                showRefresh = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.fetchDetailLokasi(idLokasi)
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
                                DetailRow(label = "ID Lokasi", value = lokasi.id_lokasi.toString())
                                DetailRow(label = "Nama Lokasi", value = lokasi.nama_lokasi)
                                DetailRow(label = "Alamat Lokasi", value = lokasi.alamat_lokasi)
                                DetailRow(label = "Kapasitas", value = lokasi.kapasitas)
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
