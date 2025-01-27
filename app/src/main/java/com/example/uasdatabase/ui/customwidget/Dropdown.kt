package com.example.uasdatabase.ui.customwidget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.filled.Place


data class Dropdown(
    val id : Int,
    val label: String
)

enum class IconType {
    FACE,
    PLACE
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownTextField(
    selectedValue: String,
    options: List<Dropdown>,
    label: String,
    onValueChangedEvent: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    leadingIconType: IconType = IconType.FACE
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            label = { Text(text = label) },
            leadingIcon = {
                Icon(
                    imageVector = when (leadingIconType) {
                        IconType.FACE -> Icons.Default.Face
                        IconType.PLACE -> Icons.Default.Place
                    },
                    contentDescription = null
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            isError = isError,
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { dropdown ->
                DropdownMenuItem(
                    text = { Text(text = dropdown.label) },
                    onClick = {
                        expanded = false
                        onValueChangedEvent(dropdown.id)
                    }
                )
            }
        }
    }
}

