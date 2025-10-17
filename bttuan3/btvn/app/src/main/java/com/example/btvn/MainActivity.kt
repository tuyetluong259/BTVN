// MainActivity.kt
package com.example.btvn // Thay thế bằng tên package của bạn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.btvn.ui.theme.BtvnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BtvnTheme { // Sử dụng Theme của bạn
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

// Định nghĩa các Route (Đường dẫn) cho Navigation
object ManHinh {
    const val DANH_SACH = "ui_components_list"
    const val CHI_TIET_TEXT = "text_detail"
    const val CHI_TIET_IMAGE = "images_detail"
    const val CHI_TIET_TEXTFIELD = "textfield_detail"
    const val CHI_TIET_PASSWORDFIELD = "passwordfield_detail"
    const val CHI_TIET_LAYOUT1 = "row_layout_detail"
    const val CHI_TIET_LAYOUT2 = "column_layout_detail"
    const val CHI_TIET_LAYOUT3 = "box_layout_detail"

}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ManHinh.DANH_SACH
    ) {
        // 1. Màn hình Danh sách (Menu chính)
        composable(ManHinh.DANH_SACH) {
            // ĐÃ SỬA: Loại bỏ `.kt` và đảm bảo tên hàm đúng là ManHinhDanhSachUI
            ManHinhDanhSachUI(navController)
        }
        // 2. Màn hình Chi tiết Text
        composable(ManHinh.CHI_TIET_TEXT) {
            ManHinhChiTietText(navController = navController)
        }
        // 3. Màn hình Chi tiết Image
        composable(ManHinh.CHI_TIET_IMAGE) {
            ManHinhChiTietImage(navController = navController)
        }
        // 4. Màn hình Chi tiết TextField
        composable(ManHinh.CHI_TIET_TEXTFIELD) {
            ManHinhChiTietInput(navController = navController, laPasswordField = false)
        }
        // 5. Màn hình Chi tiết PasswordField
        composable(ManHinh.CHI_TIET_PASSWORDFIELD) {
            ManHinhChiTietInput(navController = navController, laPasswordField = true)
        }
        // 6. Màn hình Chi tiết Layout (Column/Row)
        composable(ManHinh.CHI_TIET_LAYOUT1) {
            Layout1(navController = navController)
        }
        composable(ManHinh.CHI_TIET_LAYOUT2) {
            Layout2(navController = navController)
        }
        composable(ManHinh.CHI_TIET_LAYOUT3) {
            Layout3(navController = navController)
        }
    }
}