package com.example.thuchanh1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Đảm bảo import này phải đúng với package của bạn
import com.example.thuchanh1.ui.theme.Thuchanh1Theme

// Lớp MainActivity chứa điểm khởi đầu của ứng dụng
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Thuchanh1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BaiTapThucHanh02Screen()
                }
            }
        }
    }
}

// Hàm Composable cho màn hình Thực hành 02
@Composable
fun BaiTapThucHanh02Screen() {
    // 1. Trạng thái cho TextField (Nội dung nhập vào)
    var input by remember { mutableStateOf("") }

    // 2. Trạng thái cho lỗi (Thông báo lỗi)
    var errorMessage by remember { mutableStateOf("") }

    // 3. Trạng thái cho danh sách (Số lượng mục cần hiển thị)
    var listCount by remember { mutableStateOf(0) }

    Column( // Column cha cần được fillMaxSize để weight hoạt động
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tiêu đề
        Text(
            text = "Thực hành 02",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Khu vực Nhập liệu và Nút Bấm ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // TextField nhập liệu (Cho phép nhập mọi ký tự)
            OutlinedTextField(
                value = input,
                onValueChange = { newValue ->
                    input = newValue // CHO PHÉP nhập TẤT CẢ ký tự
                    errorMessage = "" // Xóa lỗi khi người dùng bắt đầu nhập
                },
                label = { Text("Nhập vào số lượng") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                isError = errorMessage.isNotEmpty()
                // Không đặt KeyboardOptions để cho phép nhập tất cả
            )

            // Nút "Tạo" (Kiểm tra lỗi khi nhấn)
            Button(
                onClick = {
                    // Kiểm tra xem input có phải là số nguyên dương hay không
                    val number = input.toIntOrNull()

                    if (number == null || number <= 0) {
                        // Trường hợp: Không hợp lệ (không phải số, số âm, hoặc trống)
                        listCount = 0
                        errorMessage = "Dữ liệu bạn nhập không hợp lệ"
                    } else {
                        // Trường hợp: Hợp lệ (là số nguyên dương)
                        listCount = number
                        errorMessage = ""
                    }
                },
                modifier = Modifier
                    .height(56.dp)
                    .width(IntrinsicSize.Min)
            ) {
                Text(text = "Tạo")
            }
        }

        // --- Hiển thị Thông báo Lỗi ---
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Hiển thị Danh sách (LazyColumn) ---
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                // QUAN TRỌNG: Thêm weight(1f) để cuộn được
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(count = listCount) { index ->
                ItemCard(number = index + 1)
            }
        }
    }
}

// Hàm Composable cho mỗi mục trong danh sách
@Composable
fun ItemCard(number: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = CardDefaults.cardColors(
            // Dùng màu đỏ cho giống ảnh mẫu
            containerColor = MaterialTheme.colorScheme.error
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "$number",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}