package com.example.btvn2

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

// --- Định nghĩa các tên màn hình (Routes) ---
object Screen {
    const val LOGIN = "login" // ĐÃ THÊM: Thêm màn hình Login để làm điểm bắt đầu
    const val FORGOT_PASSWORD = "forgot_password"
    const val VERIFICATION = "verification"
    const val RESET_PASSWORD = "reset_password"
    const val CONFIRM = "confirm"
}

// --- Định nghĩa tên các Đối số được truyền qua routes ---
object NavArgs {
    const val EMAIL = "email"
    const val CODE = "code"
}

// --- Định nghĩa các Tuyến đường (Routes) hoàn chỉnh có Đối số ---
object Routes {
    // 1. Tuyến đường đến màn hình Xác minh (Verification) - Cần truyền Email
    const val VERIFICATION_ROUTE = "${Screen.VERIFICATION}/{${NavArgs.EMAIL}}"

    // Định nghĩa loại dữ liệu cho đối số của route VERIFICATION_ROUTE
    val verificationArgs: List<NamedNavArgument> = listOf(
        navArgument(NavArgs.EMAIL) { type = NavType.StringType }
    )

    // 2. Tuyến đường đến màn hình Đặt lại Mật khẩu (Reset Password) - Cần truyền Email & Code
    const val RESET_PASSWORD_ROUTE = "${Screen.RESET_PASSWORD}/{${NavArgs.EMAIL}}/{${NavArgs.CODE}}"

    // Định nghĩa loại dữ liệu cho các đối số của route RESET_PASSWORD_ROUTE
    val resetPasswordArgs: List<NamedNavArgument> = listOf(
        navArgument(NavArgs.EMAIL) { type = NavType.StringType },
        navArgument(NavArgs.CODE) { type = NavType.StringType }
    )
}
