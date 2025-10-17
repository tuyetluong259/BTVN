// ManHinhChiTietInput.kt (HOÀN THIỆN)
package com.example.btvn

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManHinhChiTietInput(navController: NavController, laPasswordField: Boolean) {
    // ⭐️ BIẾN TRẠNG THÁI: inputValue được cập nhật mỗi khi gõ
    var inputValue by remember { mutableStateOf("") }

    val tieuDe = if (laPasswordField) "PasswordField" else "TextField"
    val visualTransformation = if (laPasswordField) PasswordVisualTransformation() else VisualTransformation.None
    val keyboardType = if (laPasswordField) KeyboardType.Password else KeyboardType.Text

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(tieuDe) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Căn giữa nội dung màn hình
        ) {

            // Đặt TextField/PasswordField
            OutlinedTextField(
                value = inputValue,
                onValueChange = { inputValue = it }, // Hàm này cập nhật inputValue
                label = { Text("Thông tin nhập") },
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                visualTransformation = visualTransformation,
                modifier = Modifier.fillMaxWidth()
            )

            // DÒNG HIỂN THỊ CẬP NHẬT DỮ LIỆU (CHỈ DÀNH CHO TEXTFIELD)
            if (!laPasswordField) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    // ⭐️ ĐÃ FIX: SỬ DỤNG LẠI $inputValue ĐỂ DỮ LIỆU CẬP NHẬT THEO THỜI GIAN THỰC
                    text = "Tự động cập nhật dữ liệu theo textfield: $inputValue",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Red // Giữ màu đỏ như yêu cầu
                )
            }

            // Nếu là PasswordField
            if (laPasswordField) {
                Spacer(modifier = Modifier.height(12.dp))
                // Hiển thị thông báo gợi ý hoặc trạng thái
                Text(
                    text = if (inputValue.isEmpty()) "Nhập mật khẩu để xem hiệu ứng ẩn" else "Mật khẩu đã được ẩn",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
            }
        }
    }
}

// =========================================================
// PHẦN PREVIEW
// =========================================================

@Preview(showBackground = true)
@Composable
fun PreviewManHinhChiTietTextField() {
    MaterialTheme {
        Surface {
            val context = LocalContext.current
            val dummyNavController = remember { NavController(context) }
            // Preview cho TextField
            ManHinhChiTietInput(navController = dummyNavController, laPasswordField = false)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewManHinhChiTietPasswordField() {
    MaterialTheme {
        Surface {
            val context = LocalContext.current
            val dummyNavController = remember { NavController(context) }
            // Preview cho PasswordField
            ManHinhChiTietInput(navController = dummyNavController, laPasswordField = true)
        }
    }
}