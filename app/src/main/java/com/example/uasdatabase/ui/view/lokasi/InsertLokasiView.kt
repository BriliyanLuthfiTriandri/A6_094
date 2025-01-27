package com.example.uasdatabase.ui.view.lokasi

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uasdatabase.ui.customwidget.CostumeTopAppBar
import com.example.uasdatabase.ui.navigation.AlamatNavigasi
import com.example.uasdatabase.ui.viewmodel.PenyediaViewModel
import com.example.uasdatabase.ui.viewmodel.lokasi.InsertLokasiUiEvent
import com.example.uasdatabase.ui.viewmodel.lokasi.InsertLokasiUiState
import com.example.uasdatabase.ui.viewmodel.lokasi.InsertLokasiViewModel
import kotlinx.coroutines.launch


fun validateForm(insertUiEvent: InsertLokasiUiEvent): Boolean {
    return insertUiEvent.nama_lokasi.isNotEmpty() &&
            insertUiEvent.alamat_lokasi.isNotEmpty() &&
            insertUiEvent.kapasitas.isNotEmpty()
}

@Composable
fun EntryLokasiBody(
    insertUiState: InsertLokasiUiState,
    onLokasiValueChange: (InsertLokasiUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var formValid by remember { mutableStateOf(true) }
    var showValidationMessage by remember { mutableStateOf(false) }

    val isFormValid = validateForm(insertUiState.insertUiEvent)

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp), modifier = modifier.padding(12.dp)
    ) {
        FormLokasiInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onLokasiValueChange,
            modifier = Modifier.fillMaxWidth(),
            isFormValid = isFormValid,
            showValidationMessage = showValidationMessage
        )

        Button(
            onClick = {
                if (isFormValid) {
                    onSaveClick()
                } else {
                    formValid = false
                    showValidationMessage = true
                }
            },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF91A4),
                contentColor = Color.White
            )
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormLokasiInput(
    insertUiEvent: InsertLokasiUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertLokasiUiEvent) -> Unit = {},
    enabled: Boolean = true,
    isFormValid: Boolean,
    showValidationMessage: Boolean
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val isNamaLokasiValid = insertUiEvent.nama_lokasi.isNotEmpty()
        val isAlamatLokasiValid = insertUiEvent.alamat_lokasi.isNotEmpty()
        val isKapasitasValid = insertUiEvent.kapasitas.isNotEmpty()

        OutlinedTextField(
            value = insertUiEvent.nama_lokasi,
            onValueChange = { onValueChange(insertUiEvent.copy(nama_lokasi = it)) },
            label = { Text("Nama Lokasi") },
            placeholder = { Text("Masukan Nama Lokasi") },
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = " ") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = !isNamaLokasiValid && showValidationMessage,
            supportingText = {
                if (!isNamaLokasiValid && showValidationMessage) {
                    Text(text = "Nama Lokasi tidak boleh kosong", color = Color.Red)
                }
            }
        )

        OutlinedTextField(
            value = insertUiEvent.alamat_lokasi,
            onValueChange = { onValueChange(insertUiEvent.copy(alamat_lokasi = it)) },
            leadingIcon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = " ") },
            label = { Text("Alamat Lokasi") },
            placeholder = { Text("Masukan Alamat Lokasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = !isAlamatLokasiValid && showValidationMessage,
            supportingText = {
                if (!isAlamatLokasiValid && showValidationMessage) {
                    Text(text = "Alamat Lokasi tidak boleh kosong", color = Color.Red)
                }
            }
        )

        OutlinedTextField(
            value = insertUiEvent.kapasitas,
            onValueChange = { onValueChange(insertUiEvent.copy(kapasitas = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            leadingIcon = { Icon(imageVector = Icons.Default.Info, contentDescription = " ") },
            label = { Text("Kapasitas Pada Lokasi") },
            placeholder = { Text("Masukan Jumlah Kapasitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = !isKapasitasValid && showValidationMessage,
            supportingText = {
                if (!isKapasitasValid && showValidationMessage) {
                    Text(text = "Kapasitas tidak boleh kosong", color = Color.Red)
                }
            }
        )

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
