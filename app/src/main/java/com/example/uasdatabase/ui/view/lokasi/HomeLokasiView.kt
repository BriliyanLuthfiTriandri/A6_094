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