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
import com.example.btvn2.Screen
import com.example.btvn2.R
import com.example.btvn2.ui.theme.Btvn2Theme

// SỬA 1: Thêm tham số "email: String?" để nhận dữ liệu
@Composable
fun ConfirmScreen(navController: NavController, email: String?) {

    // SỬA 2: Sử dụng email được truyền vào, nếu nó null thì hiển thị một chuỗi an toàn
    val userEmail = email ?: "Không có email"
    val userPasswordPlaceholder = "********"

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

            // Trường Email này giờ sẽ hiển thị đúng email người dùng đã nhập
            OutlinedTextField(
                value = userEmail,
                onValueChange = {},
                readOnly = true,
                label = { Text("Email") },
                // SỬA 3: Sửa lại tên icon cho đúng
                leadingIcon = { Icon(painterResource(id = R.drawable.lock), contentDescription = null) },
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
                // SỬA 3: Sửa lại tên icon cho đúng
                leadingIcon = { Icon(painterResource(id = R.drawable.lock), contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f)) // Đẩy nút xuống dưới

            // Nút xác nhận
            Button(
                onClick = {
                    // SỬA 4: Gửi email này về cho LoginScreen
                    val route = "${Screen.LOGIN}?${NavArgs.EMAIL}=$userEmail"
                    navController.navigate(route) {
                        popUpTo(Screen.LOGIN) {
                            inclusive = true
                        }
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
        val fakeNavController = rememberNavController()
        // SỬA 5: Cung cấp một email giả cho Preview để nó không báo lỗi
        ConfirmScreen(navController = fakeNavController, email = "preview@email.com")
    }
}
