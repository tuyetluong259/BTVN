package com.example.btth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.example.btth.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = viewModel(),
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    var isPhoneAuthDialogVisible by remember { mutableStateOf(false) }

    // 1. Google Sign-In setup (ĐÃ SỬA)
    // Chỉ định rõ Web Client ID để tránh lỗi "quay hoài" và đảm bảo xác thực đúng.
    val webClientId = "683013257609-nenvl8hhe83ik2khv8rd2m7ltqunhifo.apps.googleusercontent.com"

    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId) // Sử dụng client ID đã chỉ định
            .requestEmail()
            .build()
    }
    val googleSignInClient = remember { GoogleSignIn.getClient(context, gso) }

    // Launcher xử lý kết quả đăng nhập Google (ĐÃ SỬA ĐỂ CHỐNG CRASH)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        isLoading = true
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            // Lấy account một cách an toàn, không dùng '!!'
            val account = task.getResult(ApiException::class.java)

            // Kiểm tra kỹ account và idToken trước khi dùng để chống crash
            if (account != null && account.idToken != null) {
                // Chỉ thực hiện khi cả hai đều không null
                val credential = authViewModel.getGoogleAuthCredential(account.idToken!!) // Dùng !! ở đây là an toàn vì đã kiểm tra
                authViewModel.signInWithGoogleCredential(credential)
            } else {
                // Xử lý trường hợp account hoặc idToken bị null
                isLoading = false
                authViewModel.updateSignInStatus("Error: Google account info is missing.")
            }
        } catch (e: ApiException) {
            // Xử lý lỗi từ Google (ví dụ: người dùng hủy, lỗi mạng)
            isLoading = false
            authViewModel.updateSignInStatus("Google Sign-In Failed or Cancelled.")
        } catch (e: Exception) {
            // Bắt các lỗi không mong muốn khác để ứng dụng không bị văng
            isLoading = false
            authViewModel.updateSignInStatus("An unexpected error occurred: ${e.message}")
        }
    }

    // 2. Lắng nghe trạng thái ViewModel
    val user by authViewModel.user.collectAsState()
    val signInStatus by authViewModel.signInStatus.collectAsState()
    val phoneAuthState by authViewModel.phoneAuthState.collectAsState()

    // 3. Điều hướng và Xử lý Loading
    LaunchedEffect(user) {
        if (user != null) {
            isLoading = false
            onLoginSuccess()
        }
    }
    LaunchedEffect(signInStatus) {
        // Nếu có lỗi, dừng loading
        if (signInStatus?.contains("Error", ignoreCase = true) == true ||
            signInStatus?.contains("Failed", ignoreCase = true) == true
        ) {
            isLoading = false
        }
    }

    // 4. Xử lý trạng thái Phone Auth
    LaunchedEffect(phoneAuthState) {
        if (phoneAuthState is PhoneAuthState.Error) {
            isLoading = false
        }
    }

    // --- Giao diện (UI) ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        // Logo và tên ứng dụng
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "App Logo",
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("SmartTasks", style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "A simple and effective to-do app",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.weight(1f))

        // Lời chào mừng
        Text("Welcome!", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Ready to explore? Log in to get started.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Hiển thị Loading hoặc Buttons
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            // 1. Button Đăng nhập Google
            Button(
                onClick = {
                    isLoading = true
                    launcher.launch(googleSignInClient.signInIntent)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Text(
                    text = "SIGN IN WITH GOOGLE",
                    color = Color.Black.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 2. Button Đăng nhập Số điện thoại
            Button(
                onClick = { isPhoneAuthDialogVisible = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Text(
                    text = "SIGN IN WITH PHONE",
                    color = Color.Black.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Hiển thị thông báo (nếu có)
        signInStatus?.let { status ->
            if (!isLoading) { // Chỉ hiện lỗi khi không loading
                Text(
                    status,
                    color = if (status.contains("Error", true) || status.contains("Failed", true)) MaterialTheme.colorScheme.error else Color.Green,
                    modifier = Modifier.padding(top = 16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Text(
            "© UTHSmartTasks",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }

    // 5. Hiển thị Phone Auth Dialog
    if (isPhoneAuthDialogVisible) {
        PhoneAuthDialog(
            authViewModel = authViewModel,
            onDismiss = {
                isPhoneAuthDialogVisible = false
                authViewModel.resetPhoneAuthState() // Reset state khi đóng dialog
            },
            onVerificationStart = { isLoading = true },
            onVerificationEnd = { isLoading = false }
        )
    }
}
