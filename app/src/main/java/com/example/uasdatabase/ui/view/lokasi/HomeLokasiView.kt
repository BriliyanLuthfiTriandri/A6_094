package com.example.uasdatabase.ui.view.lokasi

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uasdatabase.R
import com.example.uasdatabase.ui.customwidget.CostumeTopAppBar
import com.example.uasdatabase.model.Lokasi
import com.example.uasdatabase.ui.navigation.AlamatNavigasi
import com.example.uasdatabase.ui.viewmodel.PenyediaViewModel
import com.example.uasdatabase.ui.viewmodel.lokasi.HomeUiStateLokasi
import com.example.uasdatabase.ui.viewmodel.lokasi.HomeLokasiViewModel

object DestinasiHomeLokasi : AlamatNavigasi {
    override val route = "homelokasi"
    override val titleRes = " Home Lokasi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenLokasi(
    navigateToItemEntryLokasi: () -> Unit,
    navigateBack: () -> Unit,
    navigateToEditLokasi: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onDetailLokasi: (Int) -> Unit,
    viewModel: HomeLokasiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeLokasi.titleRes,
                canNavigateBack = true,
                showMenu = false,
                showRefresh = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getLokasi()
                },
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntryLokasi,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = Color(0xFFFF91A4),
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Lokasi")
            }
        },
    ) { innerPadding ->
        HomeLokasiStatus(
            lokasiUiState = viewModel.lokasiUIState,
            retryAction = { viewModel.getLokasi() },
            modifier = Modifier.padding(innerPadding),
            onDetailLokasi = onDetailLokasi,
            onDeleteLokasi = { lokasiId ->
                viewModel.deleteLokasi(lokasiId)
                viewModel.getLokasi()
            },
            onEditLokasi = navigateToEditLokasi
        )
    }
}



@Composable
fun HomeLokasiStatus(
    lokasiUiState: HomeUiStateLokasi,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteLokasi: (Int) -> Unit,
    onEditLokasi: (Int) -> Unit,
    onDetailLokasi: (Int) -> Unit
) {
    when (lokasiUiState) {
        is HomeUiStateLokasi.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiStateLokasi.Success -> {
            if (lokasiUiState.lokasi.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada data Lokasi")
                }
            } else {
                LokasiLayout(
                    lokasi = lokasiUiState.lokasi,
                    modifier = modifier.fillMaxWidth(),
                    onDetailLokasi = onDetailLokasi,
                    onDeleteClick = onDeleteLokasi,
                    onEditLokasi = onEditLokasi
                )
            }
        }

        is HomeUiStateLokasi.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}


@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = "Loading"
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error), contentDescription = ""
        )
        Text(
            text = "Failed to load data",
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text("Retry")
        }
    }
}

@Composable
fun LokasiLayout(
    lokasi: List<Lokasi>,
    modifier: Modifier = Modifier,
    onDetailLokasi: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onEditLokasi: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(lokasi) { lokasi ->
            LokasiCard(
                lokasi = lokasi,
                onDeleteLokasi = onDeleteClick,
                onEditLokasi = onEditLokasi,
                onDetailLokasi = onDetailLokasi,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun LokasiCard(
    lokasi: Lokasi,
    onDeleteLokasi: (Int) -> Unit,
    onEditLokasi: (Int) -> Unit,
    onDetailLokasi: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Konfirmasi Hapus") },
            text = { Text("Apakah Anda yakin ingin menghapus data Lokasi ini?") },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        onDeleteLokasi(lokasi.id_lokasi)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8D6E63),
                        contentColor = Color.White
                    )
                ) {
                    Text("Ya")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB0B0B0),
                        contentColor = Color.White
                    )
                ) {
                    Text("Batal")
                }
            }
        )
    }

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = lokasi.nama_lokasi,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = "Alamat: ${lokasi.alamat_lokasi}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Kapasitas: ${lokasi.kapasitas}",
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = { onDetailLokasi(lokasi.id_lokasi) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF91A4),
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Filled.Info, contentDescription = "Detail Lokasi")
                }
                Button(
                    onClick = { onEditLokasi(lokasi.id_lokasi) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB0B0B0),
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit Lokasi")
                }
                Button(
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8D6E63),
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Hapus Lokasi")
                }
            }
        }
    }
}