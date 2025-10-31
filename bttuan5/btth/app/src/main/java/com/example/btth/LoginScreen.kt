package com.example.btth

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

@Composable
fun LoginScreen(authViewModel: AuthViewModel, onLoginSuccess: () -> Unit) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }

    // --- Cấu hình Google Sign-In ---
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }
    val googleSignInClient = remember { GoogleSignIn.getClient(context, gso) }

    // --- Xử lý kết quả trả về từ màn hình đăng nhập Google ---
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        isLoading = true
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = authViewModel.getGoogleAuthCredential(account.idToken!!)
            authViewModel.signInWithGoogleCredential(credential)
        } catch (e: ApiException) {
            Toast.makeText(context, "Google Sign-In failed: ${e.message}", Toast.LENGTH_LONG).show()
            isLoading = false
        }
    }

    // --- Theo dõi trạng thái đăng nhập từ ViewModel ---
    val signInStatus by authViewModel.signInStatus.collectAsState()
    LaunchedEffect(signInStatus) {
        signInStatus?.let {
            isLoading = false
            if (it.startsWith("Success")) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                onLoginSuccess() // Điều hướng khi thành công
            } else if (it.startsWith("Error")) {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }


    // --- Giao diện (UI) ---
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(onClick = {
                isLoading = true
                launcher.launch(googleSignInClient.signInIntent)
            }) {
                Text("Sign in with Google")
            }
        }
    }
}
