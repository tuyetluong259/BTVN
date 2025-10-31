package com.example.btth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

@Composable
fun LoginScreen(authViewModel: AuthViewModel, onLoginSuccess: () -> Unit) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }

    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }
    val googleSignInClient = remember { GoogleSignIn.getClient(context, gso) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Vẫn giữ isLoading = true ở đây
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = authViewModel.getGoogleAuthCredential(account.idToken!!)
            authViewModel.signInWithGoogleCredential(credential)
            // Khi thành công, ViewModel sẽ cập nhật `user`
            // Và LaunchedEffect ở dưới sẽ xử lý việc điều hướng
        } catch (e: ApiException) {
            isLoading = false // Nếu có lỗi, dừng loading
        }
    }

    // Lắng nghe trạng thái người dùng từ ViewModel
    val user by authViewModel.user.collectAsState()

    // =================== PHẦN SỬA LỖI QUAN TRỌNG NHẤT LÀ ĐÂY ===================
    LaunchedEffect(user, isLoading) {
        // Điều kiện này đảm bảo rằng:
        // 1. Chỉ điều hướng khi quá trình loading đã kết thúc (`!isLoading`).
        // 2. Và người dùng đã được xác thực thành công (`user != null`).
        // Điều này ngăn việc điều hướng ngay lập tức khi `user` vẫn còn giá trị cũ.
        if (!isLoading && user != null) {
            onLoginSuccess()
        }
    }
    // =========================================================================

    // --- Giao diện (UI) ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "UTH Logo",
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "SmartTasks",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "A simple and effective to-do app",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Welcome",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Ready to explore? Log in to get started.",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    isLoading = true // BẮT ĐẦU loading khi nhấn nút
                    launcher.launch(googleSignInClient.signInIntent)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE3F2FD)
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_1),
                        contentDescription = "Google Logo",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "SIGN IN WITH GOOGLE",
                        color = Color.Black.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "© UTHSmartTasks",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 32.dp)
        )
    }
}
