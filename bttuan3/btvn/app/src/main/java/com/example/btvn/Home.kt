package com.example.btvn

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.btvn.ManHinh // <-- IMPORT QUAN TRỌNG: Cần thiết để điều hướng
import com.example.btvn.R // Cần import R để nhận diện tài nguyên

val LOGO_RESOURCE_ID = R.drawable.img_2 // Đảm bảo file img_2.png/webp đã có

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* Không có tiêu đề chính */ },
                actions = {
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Text(text = "Luong Thi Anh Tuyet", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(text = "012345678", fontSize = 12.sp, color = Color.Gray)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            // Giữ mặc định: verticalArrangement = Arrangement.Top
        ) {

            // 1. SPACER TRÊN: Trọng lượng 1.5f tạo khoảng trống lớn hơn ở trên
            Spacer(modifier = Modifier.weight(1.5f))

            // --- Phần Logo và Text (Nội dung chính) ---
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = LOGO_RESOURCE_ID),
                    contentDescription = "Jetpack Compose Logo",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Jetpack Compose",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Jetpack Compose is a modern UI toolkit for building native Android applications using a declarative programming approach.",
                    textAlign = TextAlign.Center,
                    color = Color.DarkGray,
                    fontSize = 14.sp
                )
            }

            // Spacer cố định giữa Text và Nút
            Spacer(modifier = Modifier.height(64.dp))

            // Nút "I'm ready"
            Button(
                onClick = {
                    // Điều hướng từ Home qua Danh sách
                    navController.navigate(ManHinh.DANH_SACH)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 0.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E88E5)
                )
            ) {
                Text(
                    text = "I'm ready",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // 2. SPACER DƯỚI: Trọng lượng 1f tạo khoảng trống nhỏ hơn ở dưới, đẩy nút lên
            Spacer(modifier = Modifier.weight(1f))

            // Thêm padding cố định ở đáy (ví dụ: navigation bar padding)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// Preview cho màn hình chào mừng
@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    MaterialTheme {
        // Tạo NavController giả cho Preview
        WelcomeScreen(navController = rememberNavController())
    }
}