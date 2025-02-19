package com.example.uasdatabase.ui.view.klien

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
import com.example.uasdatabase.model.Klien
import com.example.uasdatabase.ui.navigation.AlamatNavigasi
import com.example.uasdatabase.ui.viewmodel.PenyediaViewModel
import com.example.uasdatabase.ui.viewmodel.klien.HomeUiStateKlien
import com.example.uasdatabase.ui.viewmodel.klien.HomeKlienViewModel

object DestinasiHomeKlien : AlamatNavigasi {
    override val route = "homeklien"
    override val titleRes = " Home Klien"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenKlien(
    navigateToItemEntryKlien: () -> Unit,
    navigateBack: () -> Unit,
    navigateToItemDetailKlien: (Int) -> Unit,
    navigateToEditKlien: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeKlienViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeKlien.titleRes,
                canNavigateBack = true,
                showMenu = false,
                showRefresh = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getKlien()
                },
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntryKlien,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = Color(0xFFFF91A4),
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Klien")
            }
        },
    ) { innerPadding ->
        HomeKlienStatus(
            klienUiState = viewModel.klienUIState,
            retryAction = { viewModel.getKlien() },
            modifier = Modifier.padding(innerPadding),
            onDetailKlien = { klienId -> navigateToItemDetailKlien(klienId) },
            onDeleteKlien = { klienId -> viewModel.deleteKlien(klienId) },
            onEditKlien = { id_klien -> navigateToEditKlien(id_klien) }
        )
    }
}


@Composable
fun HomeKlienStatus(
    klienUiState: HomeUiStateKlien,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteKlien: (Int) -> Unit,
    onEditKlien: (Int) -> Unit,
    onDetailKlien: (Int) -> Unit
) {
    when (klienUiState) {
        is HomeUiStateKlien.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiStateKlien.Success -> {
            if (klienUiState.klien.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada data Klien")
                }
            } else {
                KlienLayout(
                    klien = klienUiState.klien,
                    modifier = modifier.fillMaxWidth(),
                    onDetailKlien = onDetailKlien,
                    onDeleteKlien = onDeleteKlien,
                    onEditKlien = onEditKlien
                )
            }
        }
        is HomeUiStateKlien.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
fun KlienLayout(
    klien: List<Klien>,
    modifier: Modifier = Modifier,
    onDetailKlien: (Int) -> Unit,
    onDeleteKlien: (Int) -> Unit,
    onEditKlien: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(klien) { klien ->
            KlienCard(
                klien = klien,
                onDeleteKlien = onDeleteKlien,
                onEditKlien = onEditKlien,
                onDetailKlien = onDetailKlien,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun KlienCard(
    klien: Klien,
    onDeleteKlien: (Int) -> Unit,
    onEditKlien: (Int) -> Unit,
    onDetailKlien: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Konfirmasi Hapus") },
            text = { Text("Apakah Anda yakin ingin menghapus data klien ini?") },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        onDeleteKlien(klien.id_klien)
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = klien.nama_klien,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            Text(
                text = "Email: ${klien.email_klien}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Nomor Telepon: ${klien.notlp_klien}",
                style = MaterialTheme.typography.titleMedium
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = { onDetailKlien(klien.id_klien) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF91A4),
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Filled.Info, contentDescription = "Detail Klien")
                }
                Button(
                    onClick = { onEditKlien(klien.id_klien) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB0B0B0),
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit Klien")
                }
                Button(
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8D6E63),
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Hapus Klien")
                }
            }
        }
    }
}

