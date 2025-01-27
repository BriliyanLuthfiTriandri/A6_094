package com.example.uasdatabase.ui.view.vendor

import androidx.compose.foundation.Image
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uasdatabase.R
import com.example.uasdatabase.ui.customwidget.CostumeTopAppBar
import com.example.uasdatabase.model.Vendor
import com.example.uasdatabase.ui.navigation.AlamatNavigasi
import com.example.uasdatabase.ui.viewmodel.PenyediaViewModel
import com.example.uasdatabase.ui.viewmodel.vendor.HomeUiStateVendor
import com.example.uasdatabase.ui.viewmodel.vendor.HomeVendorViewModel


@Composable
fun VendorLayout(
    vendors: List<Vendor>,
    modifier: Modifier = Modifier,
    onDetailVendor: (Int) -> Unit,
    onDeleteVendor: (Int) -> Unit,
    onEditVendor: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(vendors) { vendor ->
            VendorCard(
                vendor = vendor,
                onDeleteVendor = { onDeleteVendor(vendor.idVendor) },
                onDetailVendor = { onDetailVendor(vendor.idVendor) },
                onEditVendor = { onEditVendor(vendor.idVendor) }
            )
        }
    }
}




@Composable
fun VendorCard(
    vendor: Vendor,
    onDetailVendor: (Int) -> Unit,
    onDeleteVendor: (Int) -> Unit,
    onEditVendor: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Konfirmasi Hapus") },
            text = { Text("Apakah Anda yakin ingin menghapus data Vendor ini?") },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        onDeleteVendor(vendor.idVendor)
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
                text = vendor.namaVendor,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Email: ${vendor.emailVendor}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Nomor Telepon: ${vendor.notlpVendor}",
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = { onDetailVendor(vendor.idVendor) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF91A4),
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Filled.Info, contentDescription = "Detail Vendor")
                }
                Button(
                    onClick = { onEditVendor(vendor.idVendor) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB0B0B0),
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit Vendor")
                }
                Button(
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8D6E63),
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Hapus Vendor")
                }
            }
        }
    }
}

