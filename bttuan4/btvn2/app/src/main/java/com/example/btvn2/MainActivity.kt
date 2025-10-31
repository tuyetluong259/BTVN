package com.example.btvn2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.btvn2.ui.theme.Btvn2Theme

// Không cần import LoginScreen nữa
// import com.example.btvn2.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Btvn2Theme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // THAY ĐỔI: Bắt đầu trực tiếp từ màn hình Quên Mật Khẩu
    NavHost(
        navController = navController,
        startDestination = Screen.FORGOT_PASSWORD
    ) {
        // 1. Màn hình Quên Mật Khẩu (Bây giờ là màn hình bắt đầu)
        composable(Screen.FORGOT_PASSWORD) {
            ForgotPasswordScreen(navController = navController)
        }

        // 2. Màn hình Xác minh Mã
        composable(
            route = Routes.VERIFICATION_ROUTE,
            arguments = Routes.verificationArgs
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString(NavArgs.EMAIL) ?: ""
            VerificationScreen(navController = navController, email = email)
        }

        // 3. Màn hình Đặt lại Mật khẩu
        composable(
            route = Routes.RESET_PASSWORD_ROUTE,
            arguments = Routes.resetPasswordArgs
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString(NavArgs.EMAIL) ?: ""
            val code = backStackEntry.arguments?.getString(NavArgs.CODE) ?: ""
            ResetPasswordScreen(navController = navController, email = email, code = code)
        }

        // 4. Màn hình Xác nhận (Confirm)
        composable(
            route = "${Screen.CONFIRM}?${NavArgs.EMAIL}={${NavArgs.EMAIL}}",
            arguments = listOf(
                navArgument(NavArgs.EMAIL) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString(NavArgs.EMAIL)
            ConfirmScreen(
                navController = navController,
                email = email
            )
        }

        // Màn hình Login đã được tạm thời loại bỏ để kiểm tra
        /*
        val loginScreenRoute = "${Screen.LOGIN}?${NavArgs.EMAIL}={${NavArgs.EMAIL}}"
        composable(
            route = loginScreenRoute,
            arguments = listOf(
                navArgument(NavArgs.EMAIL) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val emailFromConfirm = backStackEntry.arguments?.getString(NavArgs.EMAIL)
            // Vì LoginScreen chưa có nên dòng này sẽ gây lỗi
            // LoginScreen(
            //     navController = navController,
            //     emailFromConfirm = emailFromConfirm
            // )
        }
        */
    }
}
