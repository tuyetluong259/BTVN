package com.example.btvn
// List.kt

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

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
        MucComponent("Column", "Arranges elements horizontally", ManHinh.CHI_TIET_LAYOUT2),

        //


        // TỰ TÌM HIỂU
        MucComponent("Tự tìm hiểu", "Tìm ra tất cả các thành phần UI Cơ bản", "", mauNen = Color(0xFFEF9A9A)), // Màu đỏ nhạt
        MucComponent("Box", "Arranges elements horizontally", ManHinh.CHI_TIET_LAYOUT3),

    )

    Scaffold(
        topBar = { TopAppBar(title = { Text("UI Components List") }) }
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
    // Mimics the colored block style from the slide
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .height(70.dp) // Chiều cao cố định cho dễ nhìn
            .background(color = item.mauNen)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Column {
            Text(text = item.tieuDe, style = MaterialTheme.typography.titleLarge)
            Text(text = item.moTa, style = MaterialTheme.typography.bodyMedium)
        }
    }
    // Dùng Spacer làm khoảng cách, mô phỏng divider
    Spacer(modifier = Modifier.height(2.dp))
}