package com.example.btth.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.btth.R
import com.example.btth.data.Screen
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.font.Font
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(key1 = true) {
        delay(2000)
        navController.navigate(Screen.ONBOARDING_FLOW) {
            // Xóa Splash khỏi back stack
            popUpTo(Screen.SPLASH) { inclusive = true }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- 1. Logo (Ở trên cùng) ---
        Spacer(modifier = Modifier.height(200.dp))
        Image(
            painter = painterResource(id = R.drawable.anh1), // Đảm bảo có resource uth_logo
            contentDescription = "UTH Logo",
            modifier = Modifier.width(200.dp)
        )

        // Phần nội dung chính (Đẩy xuống dưới)
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "UTH SmartTasks",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp),
            fontFamily = androidx.compose.ui.text.font.FontFamily(Font(R.font.font1)),
            color = androidx.compose.ui.graphics.Color(0xFF02878A) // Ví dụ màu xanh dương
            )

        Text(
            text = "Nền tảng quản lý công việc và thời gian hiệu quả.",
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 24.dp),
            color = androidx.compose.ui.graphics.Color(0xFF000000)
        )

        // --- 2. Nút "Chào Mừng" (Ở dưới cùng) ---
//        Spacer(modifier = Modifier.weight(1f)) // Đẩy nút xuống cuối màn hình

//        Button(
//            onClick = {
//                // Điều hướng sang màn hình Onboarding Flow khi người dùng bấm nút
//                navController.navigate(Screen.ONBOARDING_FLOW) {
//                    popUpTo(Screen.SPLASH) { inclusive = true }
//                }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(56.dp)
//        ) {
//            Text(text = "Chào Mừng", fontSize = 20.sp)
//        }
    }
}
@Preview(showBackground = true)@Composable
fun SplashScreenPreview() {
    MaterialTheme {
        SplashScreen(navController = NavController(LocalContext.current))
    }
}