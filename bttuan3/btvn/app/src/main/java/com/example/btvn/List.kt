package com.example.btvn

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.Alignment

data class MucComponent(
    val tieuDe: String,
    val moTa: String,
    val route: String,
    val laTieuDe: Boolean = false,
    val mauNen: Color = Color(0xFFBBDEFB) // Màu xanh nhạt mặc định
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManHinhDanhSachUI(navController: NavController) {
    val items = listOf(
        // DISPLAY
        MucComponent("Display", "", "", laTieuDe = true, mauNen = Color.White),
        MucComponent("Text", "Displays text", ManHinh.CHI_TIET_TEXT),
        MucComponent("Image", "Displays an image", ManHinh.CHI_TIET_IMAGE),

        // INPUT
        MucComponent("Input", "", "", laTieuDe = true, mauNen = Color.White),
        MucComponent("TextField", "Input field for text", ManHinh.CHI_TIET_TEXTFIELD),
        MucComponent("PasswordField", "Input field for passwords", ManHinh.CHI_TIET_PASSWORDFIELD),

        // LAYOUT
        MucComponent("Layout", "", "", laTieuDe = true, mauNen = Color.White),
        MucComponent("Row", "Arranges elements horizontally", ManHinh.CHI_TIET_LAYOUT1),
        MucComponent("Column", "Arranges elements vertically", ManHinh.CHI_TIET_LAYOUT2), // Sửa mô tả

        // TỰ TÌM HIỂU
        MucComponent("Tự tìm hiểu", "Tìm ra tất cả các thành phần UI Cơ bản", "", mauNen = Color(0xFFEF9A9A)),
        MucComponent("Box", "Arranges elements by layering", ManHinh.CHI_TIET_LAYOUT3), // Sửa mô tả
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("UI Components List") },
                // THÊM NÚT QUAY LẠI
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack() // Quay về WelcomeScreen
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Quay lại"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items.forEach { item ->
                if (item.laTieuDe) {
                    Text(
                        text = item.tieuDe,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 4.dp)
                    )
                } else {
                    MucDanhSach(
                        item = item,
                        onClick = {
                            if (item.route.isNotEmpty()) {
                                navController.navigate(item.route)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MucDanhSach(item: MucComponent, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .height(70.dp)
            .background(color = item.mauNen)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = item.tieuDe, style = MaterialTheme.typography.titleLarge)
            Text(text = item.moTa, style = MaterialTheme.typography.bodyMedium)
        }
    }
    Spacer(modifier = Modifier.height(2.dp))
}