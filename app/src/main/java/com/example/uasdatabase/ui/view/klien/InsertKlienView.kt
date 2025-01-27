package com.example.uasdatabase.ui.view.klien

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.uasdatabase.ui.viewmodel.klien.InsertKlienUiEvent
import com.example.uasdatabase.ui.viewmodel.klien.InsertKlienUiState
import com.example.uasdatabase.ui.viewmodel.klien.InsertKlienViewModel
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryKlienScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertKlienViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Entry Klien",
                canNavigateBack = true,
                showMenu = false,
                showRefresh = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryKlienBody(
            insertUiState = viewModel.uiState,
            onKlienValueChange = viewModel::updateInsertKlienState,
            onSaveClick = {
                coroutineScope.launch {
                    if (validateForm(viewModel.uiState.insertUiEvent)) {
                        viewModel.insertKlien()
                        navigateBack()
                    }
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

fun validateForm(insertUiEvent: InsertKlienUiEvent): Boolean {
    return insertUiEvent.nama_klien.isNotEmpty() &&
            insertUiEvent.email_klien.isNotEmpty() &&
            insertUiEvent.notlp_klien.isNotEmpty()
}

@Composable
fun EntryKlienBody(
    insertUiState: InsertKlienUiState,
    onKlienValueChange: (InsertKlienUiEvent) -> Unit,
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
        FormKlienInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onKlienValueChange,
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
fun FormKlienInput(
    insertUiEvent: InsertKlienUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertKlienUiEvent) -> Unit = {},
    enabled: Boolean = true,
    isFormValid: Boolean,
    showValidationMessage: Boolean
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val isNamaKlienValid = insertUiEvent.nama_klien.isNotEmpty()
        val isEmailKlienValid = insertUiEvent.email_klien.isNotEmpty()
        val isNotlpKlienValid = insertUiEvent.notlp_klien.isNotEmpty()

        OutlinedTextField(
            value = insertUiEvent.nama_klien,
            onValueChange = { onValueChange(insertUiEvent.copy(nama_klien = it)) },
            label = { Text("Nama Klien") },
            placeholder = { Text("Masukan Nama Klien") },
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = " ") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = !isNamaKlienValid && showValidationMessage,
            supportingText = {
                if (!isNamaKlienValid && showValidationMessage) {
                    Text(text = "Nama Klien tidak boleh kosong", color = Color.Red)
                }
            }
        )

        OutlinedTextField(
            value = insertUiEvent.email_klien,
            onValueChange = { onValueChange(insertUiEvent.copy(email_klien = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = { Text("Email Klien") },
            placeholder = { Text("Masukan Email Klien") },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = " ") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = !isEmailKlienValid && showValidationMessage,
            supportingText = {
                if (!isEmailKlienValid && showValidationMessage) {
                    Text(text = "Email Klien tidak boleh kosong", color = Color.Red)
                }
            }
        )

        OutlinedTextField(
            value = insertUiEvent.notlp_klien,
            onValueChange = { onValueChange(insertUiEvent.copy(notlp_klien = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            label = { Text("Nomor Telepon Klien") },
            placeholder = { Text("Masukan Nomor Telepon Klien") },
            leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = " ") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = !isNotlpKlienValid && showValidationMessage,
            supportingText = {
                if (!isNotlpKlienValid && showValidationMessage) {
                    Text(text = "Nomor Telepon Klien tidak boleh kosong", color = Color.Red)
                }
            }
        )

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
