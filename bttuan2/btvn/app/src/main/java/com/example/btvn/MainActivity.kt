package com.example.btvn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.btvn.ui.theme.BtvnTheme
import androidx.compose.ui.tooling.preview.Preview

// Lớp MainActivity chứa điểm khởi đầu của ứng dụng
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BtvnTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Tuan02ThucHanh01Screen() // Gọi màn hình bài tập
                }
            }
        }
    }
}

// Hàm Composable cho màn hình Thực hành 01
@Composable
fun Tuan02ThucHanh01Screen() {
    // 1. Trạng thái lưu trữ dữ liệu nhập
    var hoTenInput by remember { mutableStateOf("") }
    var tuoiInput by remember { mutableStateOf("") }

    // 2. Trạng thái lưu trữ kết quả và thông báo lỗi
    var ketQuaKiemTra by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tiêu đề
        Text(
            text = "THỰC HÀNH 01",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(top = 32.dp, bottom = 24.dp)
        )

        // --- Khu vực Nhập liệu (Card nền xám) ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f) // Màu nền xám nhạt
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // TextField Họ và tên
                OutlinedTextField(
                    value = hoTenInput,
                    onValueChange = { hoTenInput = it },
                    label = { Text("Họ và tên") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                // TextField Tuổi
                OutlinedTextField(
                    value = tuoiInput,
                    onValueChange = { newValue ->
                        // Chỉ cho phép nhập số
                        if (newValue.all { it.isDigit() } || newValue.isEmpty()) {
                            tuoiInput = newValue
                        }
                    },
                    label = { Text("Tuổi") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Nút Kiểm tra ---
        Button(
            onClick = {
                val tuoi = tuoiInput.toIntOrNull()

                if (hoTenInput.isBlank() || tuoi == null) {
                    // Xử lý lỗi nếu thiếu tên hoặc tuổi không phải số
                    isError = true
                    ketQuaKiemTra = "Vui lòng nhập đầy đủ Họ tên và Tuổi hợp lệ."
                } else {
                    isError = false
                    // Logic kiểm tra tuổi
                    ketQuaKiemTra = kiemTraDoTuoi(hoTenInput, tuoi)
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.6f) // Nút nhỏ hơn một chút so với màn hình
                .height(50.dp)
        ) {
            Text(text = "Kiểm tra")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- Hiển thị Kết quả ---
        if (ketQuaKiemTra.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isError) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (isError) "Lỗi!" else "Kết quả:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = if (isError) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = ketQuaKiemTra,
                        fontSize = 16.sp,
                        color = if (isError) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

// Hàm logic kiểm tra độ tuổi
fun kiemTraDoTuoi(ten: String, tuoi: Int): String {
    val loaiTuoi = when {
        tuoi > 65 -> "Người già"
        tuoi >= 6 -> "Người lớn" // Bao gồm (6 - 65)
        tuoi >= 2 -> "Trẻ em"    // Bao gồm (2 - 6)
        else -> "Em bé"         // Bao gồm (<= 2)
    }

    // Hiển thị thông tin và kết quả kiểm tra
    return "Thông tin: $ten, $tuoi tuổi.\nPhân loại: $ten thuộc nhóm $loaiTuoi."
}
@Preview(showBackground = true)
@Composable
fun PreviewTuan02() {
    BtvnTheme {
        Tuan02ThucHanh01Screen()
    }
}
