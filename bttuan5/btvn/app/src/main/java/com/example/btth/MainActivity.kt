package com.example.btth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.btth.ui.theme.BtthTheme

class MainActivity : ComponentActivity() {

    // Khởi tạo ViewModel ở cấp Activity
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BtthTheme {
                // Gọi Composable điều hướng chính
                AppNavigation(authViewModel = authViewModel)
            }
        }
    }
}

@Composable
fun AppNavigation(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    val user by authViewModel.user.collectAsState()

    // Xác định màn hình bắt đầu dựa trên trạng thái đăng nhập
    val startDestination = if (user == null) "login" else "profile"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = {
                    // Điều hướng đến "profile" khi đăng nhập thành công
                    navController.navigate("profile") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // =================== PHẦN SỬA LỖI LÀ ĐÂY ===================
        composable("profile") {
            ProfileScreen(
                authViewModel = authViewModel,
                onLogout = {
                    // Xóa phần điều hướng thủ công và chỉ gọi signOut()
                    // NavHost sẽ tự động chuyển về "login" khi `user` thành null
                    authViewModel.signOut()
                }
            )
        }
        // ==========================================================
    }
}
