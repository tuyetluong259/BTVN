package com.example.btvn2
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
// ĐÃ SỬA: Xóa bỏ ".data" khỏi các dòng import
import com.example.btvn2.Routes
import com.example.btvn2.Screen
import com.example.btvn2.ui.theme.Btvn2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Sử dụng Theme thống nhất trên toàn ứng dụng
            Btvn2Theme {
                AppNavigation()
            }
        }
    }
}

// Hàm quản lý toàn bộ điều hướng của ứng dụng
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.LOGIN // ĐÃ SỬA: Bắt đầu từ màn hình Login
    ) {
        // 0. Màn hình Login (Màn hình bắt đầu)
        composable(Screen.LOGIN) {
            LoginScreen(navController = navController) // Màn hình giả lập để chạy được
        }

        // 1. Màn hình Quên Mật Khẩu (Nhập Email)
        composable(Screen.FORGOT_PASSWORD) {
            ForgotPasswordScreen(navController = navController)
        }

        // 2. Màn hình Xác minh Mã (Verify Code) - Nhận Email làm đối số
        composable(
            route = Routes.VERIFICATION_ROUTE,
            arguments = Routes.verificationArgs
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString(Routes.verificationArgs[0].name) ?: ""
            VerificationScreen(navController = navController, email = email)
        }

        // 3. Màn hình Đặt lại Mật khẩu (Reset Password) - Nhận Email và Code làm đối số
        composable(
            route = Routes.RESET_PASSWORD_ROUTE,
            arguments = Routes.resetPasswordArgs
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString(Routes.resetPasswordArgs[0].name) ?: ""
            val code = backStackEntry.arguments?.getString(Routes.resetPasswordArgs[1].name) ?: ""
            ResetPasswordScreen(navController = navController, email = email, code = code)
        }

        // 4. Màn hình Xác nhận (Confirm)
        composable(Screen.CONFIRM) {
            ConfirmScreen(navController = navController)
        }
    }
}

// --- Màn hình Login giả lập ---
// Bạn có thể tạo file riêng cho màn hình này sau
@Composable
fun LoginScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoHeader()
        Spacer(modifier = Modifier.height(32.dp))
        Text("H a l o o o o o")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate(Screen.FORGOT_PASSWORD) }) {
            Text("Tiếp")
        }
    }
}
