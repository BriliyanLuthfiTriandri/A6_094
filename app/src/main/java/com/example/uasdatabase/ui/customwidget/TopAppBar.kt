package com.example.uasdatabase.ui.customwidget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CostumeTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    showRefresh: Boolean,
    showMenu: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFFF91A4),
                        Color(0xFFB0B0B0),
                        Color(0xFF8D6E63)
                    )
                )
            )
            .padding(12.dp)
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.CenterStart)
        ) {
            // Tombol Kembali
            if (canNavigateBack) {
                IconButton(
                    onClick = navigateUp,
                    modifier = Modifier
                        .size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Navigate Up",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // Tombol Menu
            if (showMenu) {
                IconButton(
                    onClick = onMenuClick,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }

        // Judul
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 16.dp)
        )

        // Tombol Refresh
        if (showRefresh) {
            IconButton(
                onClick = onRefresh,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
                    .size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
