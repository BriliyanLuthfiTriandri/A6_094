package com.example.uasdatabase.ui.view.acara

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uasdatabase.ui.customwidget.CostumeTopAppBar
import com.example.uasdatabase.ui.navigation.AlamatNavigasi
import com.example.uasdatabase.ui.viewmodel.PenyediaViewModel
import com.example.uasdatabase.ui.viewmodel.acara.UpdateAcaraViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateAcara : AlamatNavigasi {
    override val route = "updateacara"
    override val titleRes = "Update Acara"
    const val ID_ACARA = "id_acara"
    val routesWithArg = "$route/{$ID_ACARA}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateAcaraScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdateAcaraViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateAcara.titleRes,
                canNavigateBack = true,
                showMenu = false,
                showRefresh = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ) { padding ->
        EntryAcaraBody(
            modifier = Modifier.padding(padding),
            insertUiState = viewModel.updateUiState,
            onAcaraValueChange = {
                event ->
                viewModel.updateInsertAcaraState(event)
            },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateAcara()
                    delay(600)
                    withContext(Dispatchers.Main){
                        onNavigate()
                    }
                }
            }
        )
    }
}

