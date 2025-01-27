package com.example.uasdatabase.ui.view.acara

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uasdatabase.model.ListKL
import com.example.uasdatabase.ui.customwidget.CostumeTopAppBar
import com.example.uasdatabase.ui.customwidget.Dropdown
import com.example.uasdatabase.ui.customwidget.DropdownTextField
import com.example.uasdatabase.ui.customwidget.IconType
import com.example.uasdatabase.ui.navigation.AlamatNavigasi
import com.example.uasdatabase.ui.viewmodel.PenyediaViewModel
import com.example.uasdatabase.ui.viewmodel.acara.InsertAcaraUiEvent
import com.example.uasdatabase.ui.viewmodel.acara.InsertAcaraUiState
import com.example.uasdatabase.ui.viewmodel.acara.InsertAcaraViewModel
import kotlinx.coroutines.launch

object DestinasiEntryAcara : AlamatNavigasi {
    override val route = "itemacara"
    override val titleRes = "Entry Acara"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryAcaraScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertAcaraViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val uiState = viewModel.uiState

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Entry Acara",
                canNavigateBack = true,
                showMenu = false,
                showRefresh = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryAcaraBody(
            insertUiState = uiState,
            onAcaraValueChange = viewModel::updateInsertAcaraState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertAcara()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
        )
    }
}

@Composable
fun EntryAcaraBody(
    insertUiState: InsertAcaraUiState,
    onAcaraValueChange: (InsertAcaraUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var formValid by remember { mutableStateOf(true) }
    var showValidationMessage by remember { mutableStateOf(false) }

    val isFormValid = validateFormAcara(insertUiState.insertUiEvent)

    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormAcaraInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onAcaraValueChange,
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

fun validateFormAcara(insertUiEvent: InsertAcaraUiEvent): Boolean {
    return insertUiEvent.nama_acara.isNotEmpty() &&
            insertUiEvent.deskripsi_acara.isNotEmpty() &&
            insertUiEvent.tanggal_mulai.isNotEmpty() &&
            insertUiEvent.tanggal_berakhir.isNotEmpty() &&
            insertUiEvent.id_klien > 0 &&
            insertUiEvent.id_lokasi > 0 &&
            insertUiEvent.status_acara.isNotEmpty()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormAcaraInput(
    insertUiEvent: InsertAcaraUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertAcaraUiEvent) -> Unit = {},
    enabled: Boolean = true,
    isFormValid: Boolean,
    showValidationMessage: Boolean
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        val statusOptions = listOf("Direncanakan", "Berlangsung", "Selesai")
        var expanded by remember { mutableStateOf(false) }

        val calendarMulai = Calendar.getInstance()
        val calendarBerakhir = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())

        var tanggalMulai by remember { mutableStateOf(insertUiEvent.tanggal_mulai) }
        var tanggalBerakhir by remember { mutableStateOf(insertUiEvent.tanggal_berakhir) }

        val context = LocalContext.current

        val datePickerMulaiDialog = android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendarMulai.set(year, month, dayOfMonth)
                tanggalMulai = dateFormat.format(calendarMulai.time)
                onValueChange(insertUiEvent.copy(tanggal_mulai = tanggalMulai))
            },
            calendarMulai.get(Calendar.YEAR),
            calendarMulai.get(Calendar.MONTH),
            calendarMulai.get(Calendar.DAY_OF_MONTH)
        )

        val datePickerBerakhirDialog = android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendarBerakhir.set(year, month, dayOfMonth)
                tanggalBerakhir = dateFormat.format(calendarBerakhir.time)
                onValueChange(insertUiEvent.copy(tanggal_berakhir = tanggalBerakhir))
            },
            calendarBerakhir.get(Calendar.YEAR),
            calendarBerakhir.get(Calendar.MONTH),
            calendarBerakhir.get(Calendar.DAY_OF_MONTH)
        )

        val isNamaAcaraValid = insertUiEvent.nama_acara.isNotEmpty()
        val isDeskripsiAcaraValid = insertUiEvent.deskripsi_acara.isNotEmpty()
        val isTanggalMulaiValid = insertUiEvent.tanggal_mulai.isNotEmpty()
        val isTanggalBerakhirValid = insertUiEvent.tanggal_berakhir.isNotEmpty()
        val isKlienValid = insertUiEvent.id_klien > 0
        val isLokasiValid = insertUiEvent.id_lokasi > 0
        val isStatusValid = insertUiEvent.status_acara.isNotEmpty()


        OutlinedTextField(
            value = insertUiEvent.nama_acara,
            onValueChange = { onValueChange(insertUiEvent.copy(nama_acara = it)) },
            label = { Text("Masukan Nama Acara") },
            placeholder = { Text("Masukan Nama Acara") },
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = !isNamaAcaraValid && showValidationMessage,
            supportingText = {
                if (!isNamaAcaraValid && showValidationMessage) {
                    Text(text = "Nama acara tidak boleh kosong", color = Color.Red)
                }
            }
        )

        OutlinedTextField(
            value = insertUiEvent.deskripsi_acara,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsi_acara = it)) },
            label = { Text("Masukan Deskripsi Acara") },
            placeholder = { Text("Masukan Deskripsi Acara") },
            leadingIcon = { Icon(imageVector = Icons.Default.Info, contentDescription = " ") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false,
            isError = !isDeskripsiAcaraValid && showValidationMessage,
            supportingText = {
                if (!isDeskripsiAcaraValid && showValidationMessage) {
                    Text(text = "Deskripsi acara tidak boleh kosong", color = Color.Red)
                }
            }
        )

        OutlinedTextField(
            value = tanggalMulai,
            onValueChange = { tanggalMulai = it },
            label = { Text("Masukan Tanggal Mulai") },
            placeholder = { Text("YYYY-MM-DD") },
            leadingIcon = { Icon(imageVector = Icons.Default.DateRange, contentDescription = " ") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerMulaiDialog.show() },
            enabled = enabled,
            singleLine = true,
            isError = !isTanggalMulaiValid && showValidationMessage,
            supportingText = {
                if (!isTanggalMulaiValid && showValidationMessage) {
                    Text(text = "Tanggal mulai tidak boleh kosong", color = Color.Red)
                }
            }
        )

        OutlinedTextField(
            value = tanggalBerakhir,
            onValueChange = { tanggalBerakhir = it },
            label = { Text("Masukan Tanggal Berakhir") },
            placeholder = { Text("YYYY-MM-DD") },
            leadingIcon = { Icon(imageVector = Icons.Default.DateRange, contentDescription = " ") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerBerakhirDialog.show() },
            enabled = enabled,
            singleLine = true,
            isError = !isTanggalBerakhirValid && showValidationMessage,
            supportingText = {
                if (!isTanggalBerakhirValid && showValidationMessage) {
                    Text(text = "Tanggal berakhir tidak boleh kosong", color = Color.Red)
                }
            }
        )

        DropdownTextField(
            modifier = Modifier,
            selectedValue = ListKL.DataKlien().find { it.first == insertUiEvent.id_klien }?.second ?: "",
            options = ListKL.DataKlien().map { Dropdown(it.first, it.second) },
            label = "Nama Klien",
            leadingIconType = IconType.FACE,
            isError = !isKlienValid && showValidationMessage,
            onValueChangedEvent = { selectedKlienId ->
                onValueChange(insertUiEvent.copy(id_klien = selectedKlienId))
            }
        )
        if (!isKlienValid && showValidationMessage) {
            Text(text = "Klien tidak boleh kosong", color = Color.Red, modifier = Modifier.padding(start = 8.dp))
        }

        DropdownTextField(
            modifier = Modifier,
            selectedValue = ListKL.DataLokasi().find { it.first == insertUiEvent.id_lokasi }?.second ?: "",
            options = ListKL.DataLokasi().map { Dropdown(it.first, it.second) },
            label = "Nama Lokasi",
            leadingIconType = IconType.PLACE,
            isError = !isLokasiValid && showValidationMessage,
            onValueChangedEvent = { selectedLokasiId ->
                onValueChange(insertUiEvent.copy(id_lokasi = selectedLokasiId))
            }
        )
        if (!isLokasiValid && showValidationMessage) {
            Text(text = "Lokasi tidak boleh kosong", color = Color.Red, modifier = Modifier.padding(start = 8.dp))
        }


        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = insertUiEvent.status_acara,
                onValueChange = { onValueChange(insertUiEvent.copy(status_acara = it)) },
                label = { Text("Status Acara") },
                leadingIcon = { Icon(imageVector = Icons.Default.Done, contentDescription = null) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                enabled = enabled,
                readOnly = true,
                singleLine = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                placeholder = { Text("Pilih Status Acara") },
                isError = !isStatusValid && showValidationMessage
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                statusOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            onValueChange(insertUiEvent.copy(status_acara = selectionOption))
                            expanded = false
                        }
                    )
                }
            }
        }

        if (!isStatusValid && showValidationMessage) {
            Text(text = "Status acara tidak boleh kosong", color = Color.Red, modifier = Modifier.padding(start = 8.dp))
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
