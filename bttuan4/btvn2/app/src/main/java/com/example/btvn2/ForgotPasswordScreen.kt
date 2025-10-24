package com.example.btvn2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.btvn2.NavArgs
// ĐÃ SỬA: Xóa bỏ phần ".data" khỏi import
import com.example.btvn2.Routes
import com.example.btvn2.R
import com.example.btvn2.ui.theme.Btvn2Theme

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    var email by remember { mutableStateOf("utd@gmail.com") }

    Scaffold(
        topBar = { TopAppBarLogo(title = "Quên mật khẩu", navController = navController) }
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
                text = "Quên mật khẩu?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
            )

            Text(
                text = "Nhập Email của bạn, chúng tôi sẽ gửi mã xác thực.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email của bạn") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                leadingIcon = { Icon(painterResource(id = R.drawable.mail), contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (email.isNotBlank()) {
                        // Điều hướng đến màn hình xác thực và truyền email qua route
                        navController.navigate(
                            Routes.VERIFICATION_ROUTE.replace("{${NavArgs.EMAIL}}", email)
                        )
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

@Preview(showBackground = true, name = "Forgot Password Preview")
@Composable
fun ForgotPasswordScreenPreview() {
    Btvn2Theme {
        // Tạo NavController giả cho mục đích xem trước
        val fakeNavController = rememberNavController()
        ForgotPasswordScreen(navController = fakeNavController)
    }
}
