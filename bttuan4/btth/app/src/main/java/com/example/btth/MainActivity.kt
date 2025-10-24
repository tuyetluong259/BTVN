package com.example.btth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.btth.data.Screen
import com.example.btth.ui.OnboardingFlowScreen
import com.example.btth.ui.SplashScreen
import com.example.btth.ui.theme.BtthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BtthTheme {
                AppNavigation()
            }
        }
    }
}

// Hàm quản lý toàn bộ điều hướng ứng dụng
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.SPLASH
    ) {
        // Màn hình 1: Splash
        composable(Screen.SPLASH) {
            SplashScreen(navController = navController)
        }

        // Màn hình 2: Onboarding Flow (3 trang Pager)
        composable(Screen.ONBOARDING_FLOW) {
            OnboardingFlowScreen(navController = navController)
        }

    }
}
