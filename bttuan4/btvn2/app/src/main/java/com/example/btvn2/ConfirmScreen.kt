package com.example.btvn2

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
// ĐÃ SỬA: Xóa bỏ ".data" khỏi import
import com.example.btvn2.Screen
import com.example.btvn2.R
import com.example.btvn2.ui.theme.Btvn2Theme

@Composable
fun ConfirmScreen(navController: NavController) {
    // Giả sử bạn nhận email từ màn hình trước hoặc ViewModel
    val userEmail = "utd@gmail.com"
    val userPasswordPlaceholder = "********" // Mật khẩu chỉ nên là placeholder

    Scaffold(
        topBar = { TopAppBarLogo(title = "Xác nhận", navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LogoHeader()

            Text(
                text = "Xác nhận thông tin",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
            )

            Text(
                text = "Vui lòng kiểm tra lại thông tin đăng nhập của bạn.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Trường Email chỉ đọc
            OutlinedTextField(
                value = userEmail,
                onValueChange = {},
                readOnly = true,
                label = { Text("Email") },
                leadingIcon = { Icon(painterResource(id = R.drawable.mail), contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Trường Password chỉ đọc
            OutlinedTextField(
                value = userPasswordPlaceholder,
                onValueChange = {},
                readOnly = true,
                label = { Text("Mật khẩu") },
                leadingIcon = { Icon(painterResource(id = R.drawable.lock), contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f)) // Đẩy nút xuống dưới

            // Nút xác nhận
            Button(
                onClick = {
                    // Quay lại màn hình Login và xóa các màn hình trung gian (Forgot, Confirm)
                    navController.navigate(Screen.LOGIN) {
                        popUpTo(Screen.LOGIN) {
                            inclusive = true // Xóa cả màn hình Login cũ để tạo cái mới
                        }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Hoàn tất", fontSize = 18.sp)
            }
        }
    }
}

// Hàm Preview để xem trước giao diện
@Preview(showBackground = true, name = "Confirm Screen Preview")
@Composable
fun ConfirmScreenPreview() {
    Btvn2Theme {
        // ĐÃ SỬA: Dùng rememberNavController cho Preview
        val fakeNavController = rememberNavController()
        ConfirmScreen(navController = fakeNavController)
    }
}
