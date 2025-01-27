package com.example.uasdatabase.ui.view.vendor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uasdatabase.ui.customwidget.CostumeTopAppBar
import com.example.uasdatabase.ui.navigation.AlamatNavigasi
import com.example.uasdatabase.ui.viewmodel.PenyediaViewModel
import com.example.uasdatabase.ui.viewmodel.vendor.InsertVendorUiEvent
import com.example.uasdatabase.ui.viewmodel.vendor.InsertVendorUiState
import com.example.uasdatabase.ui.viewmodel.vendor.InsertVendorViewModel
import kotlinx.coroutines.launch


@Composable
fun EntryVendorBody(
    insertUiState: InsertVendorUiState,
    onVendorValueChange: (InsertVendorUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var formValid by remember { mutableStateOf(true) }
    var showValidationMessage by remember { mutableStateOf(false) }

    val isFormValid = validateForm(insertUiState.insertUiEvent)

    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormVendorInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onVendorValueChange,
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
fun FormVendorInput(
    insertUiEvent: InsertVendorUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertVendorUiEvent) -> Unit = {},
    enabled: Boolean = true,
    isFormValid: Boolean,
    showValidationMessage: Boolean
) {
    val options = listOf("Katering", "Dekorasi", "Audio Visual", "Lainnya")
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
    ) {
        val isNamaVendorValid = insertUiEvent.namaVendor.isNotEmpty()
        val isJenisVendorValid = insertUiEvent.jenisVendor.isNotEmpty()
        val isEmailVendorValid = insertUiEvent.emailVendor.isNotEmpty()
        val isNotlpVendorValid = insertUiEvent.notlpVendor.isNotEmpty()

        OutlinedTextField(
            value = insertUiEvent.namaVendor,
            onValueChange = { onValueChange(insertUiEvent.copy(namaVendor = it)) },
            label = { Text("Nama Vendor") },
            placeholder = { Text("Masukan Nama Vendor") },
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = " ") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = !isNamaVendorValid && showValidationMessage,
            supportingText = {
                if (!isNamaVendorValid && showValidationMessage) {
                    Text(text = "Nama Vendor tidak boleh kosong", color = Color.Red)
                }
            }
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = insertUiEvent.jenisVendor,
                onValueChange = { onValueChange(insertUiEvent.copy(jenisVendor = it)) },
                label = { Text("Jenis Vendor") },
                leadingIcon = { Icon(imageVector = Icons.Default.Build, contentDescription = " ") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                enabled = enabled,
                readOnly = true,
                singleLine = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                placeholder = { Text("Pilih atau tulis jenis vendor") },
                isError = !insertUiEvent.jenisVendor.isNotEmpty() && showValidationMessage,
                supportingText = {
                    if (!insertUiEvent.jenisVendor.isNotEmpty() && showValidationMessage) {
                        Text(text = "Jenis Vendor tidak boleh kosong", color = Color.Red)
                    }
                }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onValueChange(
                                if (option == "Lainnya") insertUiEvent.copy(jenisVendor = "")
                                else insertUiEvent.copy(jenisVendor = option)
                            )
                            expanded = false
                        }
                    )
                }
            }
        }

        if (insertUiEvent.jenisVendor.isEmpty() || options.contains(insertUiEvent.jenisVendor).not()) {
            OutlinedTextField(
                value = insertUiEvent.jenisVendor,
                onValueChange = { onValueChange(insertUiEvent.copy(jenisVendor = it)) },
                label = { Text("Tulis Jenis Vendor") },
                placeholder = { Text("Masukan Jenis Vendor") },
                leadingIcon = { Icon(imageVector = Icons.Default.Create, contentDescription = " ") },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true,
                isError = !insertUiEvent.jenisVendor.isNotEmpty() && showValidationMessage,
                supportingText = {
                    if (!insertUiEvent.jenisVendor.isNotEmpty() && showValidationMessage) {
                        Text(text = "Jenis Vendor tidak boleh kosong", color = Color.Red)
                    }
                }
            )
        }

        OutlinedTextField(
            value = insertUiEvent.emailVendor,
            onValueChange = { onValueChange(insertUiEvent.copy(emailVendor = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = { Text("Email Vendor") },
            placeholder = { Text("Masukan Email Vendor") },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = " ") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = !isEmailVendorValid && showValidationMessage,
            supportingText = {
                if (!isEmailVendorValid && showValidationMessage) {
                    Text(text = "Email Vendor tidak boleh kosong", color = Color.Red)
                }
            }
        )

        OutlinedTextField(
            value = insertUiEvent.notlpVendor,
            onValueChange = { onValueChange(insertUiEvent.copy(notlpVendor = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            label = { Text("Nomor Telepon Vendor") },
            placeholder = { Text("Masukan Nomor Telepon Vendor") },
            leadingIcon = { Icon(imageVector = Icons.Default.Call, contentDescription = " ") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = !isNotlpVendorValid && showValidationMessage,
            supportingText = {
                if (!isNotlpVendorValid && showValidationMessage) {
                    Text(text = "Nomor Telepon Vendor tidak boleh kosong", color = Color.Red)
                }
            }
        )

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}

