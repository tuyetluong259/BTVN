package com.example.bt2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
// Import cho WindowInsets, giúp khắc phục lỗi kẹt nút
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// LƯU Ý: Đảm bảo hình ảnh 'ic_compose_logo' tồn tại trong res/drawable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MyTaskManagementScreen()
            }
        }
    }
}

/**
 * Hàm Composable chính để xây dựng toàn bộ giao diện màn hình.
 */
@Composable
fun MyTaskManagementScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp), // Padding hai bên
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- 1. Tiêu đề nhỏ ở trên ---
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Get Started First - Task Management",
                fontSize = 14.sp,
                color = Color.DarkGray
            )

            // Khoảng trống lớn ban đầu
            Spacer(modifier = Modifier.height(150.dp))

            // --- 2. Hình ảnh biểu tượng ---
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "Jetpack Compose Logo",
                modifier = Modifier.size(150.dp)
            )

            // Khoảng cách giữa ảnh và tiêu đề
            Spacer(modifier = Modifier.height(32.dp))

            // --- 3. Tiêu đề chính ---
            Text(
                text = "Jetpack Compose",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // Khoảng cách giữa tiêu đề và mô tả
            Spacer(modifier = Modifier.height(8.dp))

            // --- 4. Mô tả ---
            Text(
                text = "Jetpack Compose is a modern UI toolkit for building native Android applications using a declarative programming approach.",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Spacer linh hoạt: Đẩy nút bấm xuống cuối cùng
            Spacer(modifier = Modifier.weight(1f))

            // --- 5. Nút bấm "I'm ready" ---
            Button(
                onClick = {
                    // TODO: Định nghĩa hành động khi người dùng nhấn nút
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF007AFF),
                    contentColor = Color.White
                )
            ) {
                Text("I'm ready", fontSize = 16.sp)
            }

            // --- KHẮC PHỤC LỖI KẸT NÚT ---
            // Thêm khoảng đệm an toàn và cố định ở đáy
            Spacer(modifier = Modifier
                .navigationBarsPadding() // Khoảng đệm tự động cho thanh điều hướng hệ thống
                .height(16.dp) // Thêm 16dp cố định để nút dịch lên cao hơn
            )
        }
    }
}

/**
 * Hàm Preview để xem trước giao diện trong Android Studio
 */
@Preview(showBackground = true, name = "Task Management Screen Preview")
@Composable
fun PreviewMyTaskManagementScreen() {
    MaterialTheme {
        MyTaskManagementScreen()
    }
}
