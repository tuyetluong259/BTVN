package com.example.btvn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

// Định nghĩa màu sắc gần giống trong ảnh
val LightGreen = Color(0xFFC8E6C9) // Màu xanh lá nhạt
val MediumGreen = Color(0xFF81C784) // Màu xanh lá vừa

/**
 * Màn hình Column Layout.
 * @param navController Dùng để điều hướng, bao gồm cả chức năng quay lại.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Layout2(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Colum Layout") },
                navigationIcon = {
                    // Thêm icon mũi tên quay lại
                    IconButton(
                        // LỆNH QUAN TRỌNG: Quay về màn hình trước trong stack điều hướng
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Quay lại"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        // Vùng chứa nội dung chính
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card 1: Màu xanh lá nhạt
            RoundedBox(color = LightGreen)

            // Card 2: Màu xanh lá đậm/vừa
            RoundedBox(color = MediumGreen)

            // Card 3: Màu xanh lá nhạt
            RoundedBox(color = LightGreen)
        }
    }
}

// Composable để tạo hình chữ nhật bo góc (Card)
@Composable
fun RoundedBox(color: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = color
        )
    ) {}
}

// Phần Preview để xem trước layout trong Android Studio
@Preview(showBackground = true)
@Composable
fun Layout2Preview() {
    MaterialTheme {
        // Tạo một NavController giả cho Preview để tránh lỗi
        // Lưu ý: Trong ứng dụng thực tế, bạn sẽ truyền NavController từ NavHost
        val dummyNavController = NavHostController(LocalContext.current)
        Layout2(navController = dummyNavController)
    }
}