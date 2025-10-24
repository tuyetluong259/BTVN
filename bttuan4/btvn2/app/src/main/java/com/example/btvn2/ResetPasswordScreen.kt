package com.example.btvn2 // SỬA 1: Sửa lại package cho đúng

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
// import androidx.compose.runtime.getValue // XÓA: Không cần thiết
// import androidx.compose.runtime.setValue // XÓA: Không cần thiết
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.btvn2.R
// SỬA 3: Xóa bỏ ".data" khỏi import
import com.example.btvn2.Screen
import com.example.btvn2.ui.theme.Btvn2Theme

@Composable
fun ResetPasswordScreen(navController: NavController, email: String, code: String) {
    var newPassword by remember { mutableStateOf("123456") }
    var confirmPassword by remember { mutableStateOf("123456") }

    Scaffold(
        topBar = { TopAppBarLogo(title = "Đặt lại Mật khẩu", navController = navController) }
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
                text = "Tạo mật khẩu mới",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )

            Text(
                text = "Mật khẩu mới của bạn phải khác với các mật khẩu đã dùng trước đó. (Email: $email, Code: $code)",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            // Input Mật khẩu mới
            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text("Mật khẩu mới") },
                visualTransformation = PasswordVisualTransformation(),
                leadingIcon = { Icon(painterResource(id = R.drawable.lock), contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Input Xác nhận Mật khẩu
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Xác nhận Mật khẩu") },
                visualTransformation = PasswordVisualTransformation(),
                leadingIcon = { Icon(painterResource(id = R.drawable.loclopen), contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            // Nút Next (Tiếp theo)
            Button(
                onClick = {
                    if (newPassword.isNotBlank() && newPassword == confirmPassword) {
                        // SỬA 2: Sửa lại logic điều hướng cho đúng luồng
                        // Đơn giản là đi tới màn hình CONFIRM
                        navController.navigate(Screen.CONFIRM)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Tiếp theo", fontSize = 18.sp)
            }
        }
    }
}


@Preview(showBackground = true, name = "Reset Password Preview")
@Composable
fun ResetPasswordScreenPreview() {
    // SỬA 4: Sửa lại tên Theme cho đúng
    Btvn2Theme {
        val fakeNavController = rememberNavController()
        ResetPasswordScreen(
            navController = fakeNavController,
            email = "preview@email.com",
            code = "123456"
        )
    }
}
