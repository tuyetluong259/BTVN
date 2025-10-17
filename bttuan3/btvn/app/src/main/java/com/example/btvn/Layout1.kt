// Layout1.kt
package com.example.btvn

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Layout1(navController: NavController) { // Thêm NavController
    // Định nghĩa các màu sắc cho ô vuông (Sử dụng màu xanh-tím từ hình ảnh)
    val colorPrimaryLight = Color(0xFFC0D5E6) // Màu xanh nhạt
    val colorPrimaryDark = Color(0xFF6DA2DB)  // Màu xanh đậm

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Row Layout") }, // Tiêu đề "Row Layout"
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // Nút quay lại
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                }
            )
        }
    ) { paddingValues ->
        // Column chính để xếp 4 hàng (Row) theo chiều dọc
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp), // Padding cho nội dung bên trong
            verticalArrangement = Arrangement.spacedBy(16.dp) // Khoảng cách giữa các Row
        ) {
            // Vòng lặp để tạo 4 hàng (Row)
            repeat(4) { rowIndex ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp) // Khoảng cách giữa 3 ô vuông
                ) {
                    // Vòng lặp để tạo 3 ô vuông trong mỗi hàng
                    repeat(3) { itemIndex ->
                        // Logic chọn màu sắc cho ô vuông (mô phỏng ngẫu nhiên màu đậm/nhạt như hình)
                        val isDark = when {
                            rowIndex == 0 && itemIndex == 1 -> true  // Hàng 1, ô 2 (Đậm)
                            rowIndex == 1 && itemIndex == 1 -> true  // Hàng 2, ô 2 (Đậm)
                            rowIndex == 2 && itemIndex == 1 -> true  // Hàng 3, ô 2 (Đậm)
                            rowIndex == 3 && itemIndex == 1 -> true  // Hàng 4, ô 2 (Đậm)
                            else -> false
                        }
                        val boxColor = if (isDark) colorPrimaryDark else colorPrimaryLight

                        Box(
                            modifier = Modifier
                                .weight(1f) // Chia đều không gian ngang cho 3 ô
                                .height(60.dp) // Chiều cao cố định của ô vuông
                                .background(
                                    color = boxColor,
                                    shape = RoundedCornerShape(12.dp) // Bo góc 12dp
                                )
                        )
                    }
                }
            }
        }
    }
}

// =========================================================
// PHẦN PREVIEW
// =========================================================

@Preview(showBackground = true)
@Composable
fun PreviewLayout1() {
    MaterialTheme {
        Surface {
            val context = LocalContext.current
            // NavController giả cho mục đích Preview
            val dummyNavController = remember { NavController(context) }

            Layout1(navController = dummyNavController)
        }
    }
}