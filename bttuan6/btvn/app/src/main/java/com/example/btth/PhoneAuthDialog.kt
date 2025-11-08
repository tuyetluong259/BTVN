package com.example.btth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit
import androidx.compose.ui.graphics.Color

@Composable
fun PhoneAuthDialog(
    authViewModel: AuthViewModel,
    onDismiss: () -> Unit,
    onVerificationStart: () -> Unit,
    onVerificationEnd: () -> Unit
) {
    val context = LocalContext.current
    val phoneAuthState by authViewModel.phoneAuthState.collectAsState()

    var phoneNumber by remember { mutableStateOf("") }
    var otpCode by remember { mutableStateOf("") }

    // Sử dụng Scaffold để hiển thị dialog toàn màn hình hoặc AlertDialog nếu muốn nhỏ hơn
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (phoneAuthState is PhoneAuthState.CodeSent) "Nhập Mã OTP" else "Đăng nhập bằng Số điện thoại") },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                when (phoneAuthState) {
                    is PhoneAuthState.Idle, is PhoneAuthState.Error -> {
                        // Bước 1: Nhập Số điện thoại
                        OutlinedTextField(
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            label = { Text("Số điện thoại (+84...)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(8.dp))
                        if (phoneAuthState is PhoneAuthState.Error) {
                            Text((phoneAuthState as PhoneAuthState.Error).message, color = Color.Red)
                        }
                    }
                    PhoneAuthState.CodeSent -> {
                        // Bước 2: Nhập Mã OTP
                        Text("Mã OTP đã được gửi đến số ${phoneNumber}.", style = MaterialTheme.typography.bodyMedium)
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = otpCode,
                            onValueChange = { otpCode = it },
                            label = { Text("Mã xác thực (OTP)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (phoneAuthState is PhoneAuthState.CodeSent) {
                        // ... (phần này giữ nguyên)
                    } else {
                        // Gửi mã OTP
                        onVerificationStart()

                        // 1. Lấy instance của FirebaseAuth
                        val firebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance()

                        // 2. Xây dựng đối tượng PhoneAuthOptions
                        val options = com.google.firebase.auth.PhoneAuthOptions.newBuilder(firebaseAuth)
                            .setPhoneNumber(phoneNumber)       // Số điện thoại cần xác thực
                            .setTimeout(60L, TimeUnit.SECONDS) // Thời gian chờ
                            .setActivity(context as androidx.activity.ComponentActivity) // Activity context
                            .setCallbacks(authViewModel.phoneAuthCallbacks) // Callbacks để xử lý kết quả
                            .build()

                        // 3. Gọi hàm verifyPhoneNumber với đối tượng options
                        PhoneAuthProvider.verifyPhoneNumber(options)
                    }
                },
                enabled = phoneNumber.isNotBlank() // Tùy chỉnh điều kiện enable
            ) {
                Text(if (phoneAuthState is PhoneAuthState.CodeSent) "Xác thực & Đăng nhập" else "Gửi Mã OTP")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onVerificationEnd() // Dừng loading
                onDismiss() // Đóng dialog
            }) {
                Text("Hủy")
            }
        }
    )
}