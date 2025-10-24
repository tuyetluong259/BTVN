package com.example.btvn2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.btvn2.ui.theme.Btvn2Theme
@Composable
fun VerificationScreen(navController: NavController, email: String) {
    // Mã OTP giả lập để kiểm tra
    val fakeOtpCode = "123456"
    var enteredCode by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBarLogo(title = "Xác thực", navController = navController) }
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
                text = "Xác thực mã",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
            )

            Text(
                text = "Nhập mã chúng tôi vừa gửi cho bạn qua Email: $email",
                color = MaterialTheme.colorScheme.onSurfaceVariant, // ĐÃ SỬA: Dùng màu từ Theme
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            // Giao diện nhập mã OTP trực quan hơn
            OtpTextField(
                otpText = enteredCode,
                onOtpTextChange = { value, _ ->
                    // Chỉ cập nhật nếu chuỗi mới là số và có độ dài không quá 6
                    if (value.all { it.isDigit() } && value.length <= 6) {
                        enteredCode = value
                    }
                },
                otpCount = 6
            )


            Spacer(modifier = Modifier.weight(1f)) // Đẩy nút xuống dưới

            Button(
                onClick = {
                    if (enteredCode == fakeOtpCode) {
                        // Tạo route hoàn chỉnh để điều hướng
                        val route = Routes.RESET_PASSWORD_ROUTE
                            .replace("{${NavArgs.EMAIL}}", email)
                            .replace("{${NavArgs.CODE}}", enteredCode)
                        navController.navigate(route)
                    }
                },
                // Vô hiệu hóa nút nếu chưa nhập đủ 6 ký tự
                enabled = enteredCode.length == 6,
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

// Composable để tạo các ô nhập mã OTP
@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String, Boolean) -> Unit
) {
    BasicTextField(
        modifier = modifier,
        value = otpText,
        onValueChange = {
            if (it.length <= otpCount) {
                onOtpTextChange.invoke(it, it.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    val char = when {
                        index < otpText.length -> otpText[index].toString()
                        else -> ""
                    }
                    val isFocused = otpText.length == index
                    OutlinedTextField(
                        modifier = Modifier
                            .width(50.dp)
                            .padding(horizontal = 4.dp),
                        value = char,
                        onValueChange = {},
                        readOnly = true,
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }
    )
}


@Preview(showBackground = true, name = "Verification Screen Preview")
@Composable
fun VerificationScreenPreview() {
    Btvn2Theme {
        val fakeNavController = NavController(LocalContext.current)
        VerificationScreen(navController = fakeNavController, email = "preview@email.com")
    }
}
