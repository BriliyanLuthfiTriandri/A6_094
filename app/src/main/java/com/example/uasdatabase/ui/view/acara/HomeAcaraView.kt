package com.example.uasdatabase.ui.view.acara

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uasdatabase.R
import com.example.uasdatabase.ui.customwidget.CostumeTopAppBar
import com.example.uasdatabase.model.Acara
import com.example.uasdatabase.model.ListKL
import com.example.uasdatabase.ui.navigation.AlamatNavigasi
import com.example.uasdatabase.ui.viewmodel.PenyediaViewModel
import com.example.uasdatabase.ui.viewmodel.acara.HomeUiStateAcara
import com.example.uasdatabase.ui.viewmodel.acara.HomeAcaraViewModel


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
fun AcaraLayout(
    acara: List<Acara>,
    modifier: Modifier = Modifier,
    onDetailAcara: (Int) -> Unit,
    onDeleteAcara: (Int) -> Unit,
    onEditAcara: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(acara) { acara ->
            AcaraCard(
                acara = acara,
                onDeleteAcara = onDeleteAcara,
                onEditAcara = onEditAcara,
                onDetailAcara = onDetailAcara,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun AcaraCard(
    acara: Acara,
    onDeleteAcara: (Int) -> Unit,
    onEditAcara: (Int) -> Unit,
    onDetailAcara: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    val lokasiList = ListKL.DataLokasi()
    val namaLokasi = lokasiList.find { it.first == acara.id_lokasi }?.second ?: "Unknown Location"

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Konfirmasi Hapus") },
            text = { Text("Apakah Anda yakin ingin menghapus data acara ini?") },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        onDeleteAcara(acara.id_acara)
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.conffeti),
                contentDescription = null,
                tint = Color(0xFF90A4AE),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(50.dp)
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = acara.nama_acara,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = "Deskripsi: ${acara.deskripsi_acara}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Lokasi: $namaLokasi",
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = { onDetailAcara(acara.id_acara) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF91A4),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(Icons.Filled.Info, contentDescription = "Detail Acara")
                    }
                    Button(
                        onClick = { onEditAcara(acara.id_acara) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFB0B0B0),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit Acara")
                    }
                    Button(
                        onClick = { showDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF8D6E63),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Hapus Acara")
                    }
                }
            }
        }
    }
}