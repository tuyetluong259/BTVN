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

// ********** LƯU Ý QUAN TRỌNG **********
// Bạn phải đảm bảo rằng các hàm Composable sau đã được định nghĩa
// và có sẵn trong package com.example.btvn:
// WelcomeScreen, ManHinhDanhSachUI, ManHinhChiTietText, ManHinhChiTietImage,
// ManHinhChiTietInput, Layout1, Layout2, Layout3.
// ************************************

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
    // Thêm màn hình HOME (WelcomeScreen) làm điểm bắt đầu
    const val HOME = "welcome_screen"

    // Màn hình chính sau khi nhấn "I'm ready"
    const val DANH_SACH = "ui_components_list"

    // Các màn hình chi tiết khác
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
        startDestination = ManHinh.HOME // ĐÃ SỬA: Đặt HOME là màn hình khởi đầu
    ) {
        // 1. Màn hình HOME (WelcomeScreen)
        composable(ManHinh.HOME) {
            // Home (WelcomeScreen) đã được định nghĩa ở bước trước
            WelcomeScreen(navController = navController)
        }

        // 2. Màn hình Danh sách (Menu chính)
        // Đây sẽ là màn hình đích khi nhấn "I'm ready"
        composable(ManHinh.DANH_SACH) {
            ManHinhDanhSachUI(navController)
        }

        // 3. Màn hình Chi tiết Text
        composable(ManHinh.CHI_TIET_TEXT) {
            ManHinhChiTietText(navController = navController)
        }

        // 4. Màn hình Chi tiết Image
        composable(ManHinh.CHI_TIET_IMAGE) {
            ManHinhChiTietImage(navController = navController)
        }

        // 5. Màn hình Chi tiết TextField (Input)
        composable(ManHinh.CHI_TIET_TEXTFIELD) {
            ManHinhChiTietInput(navController = navController, laPasswordField = false)
        }

        // 6. Màn hình Chi tiết PasswordField (Input)
        composable(ManHinh.CHI_TIET_PASSWORDFIELD) {
            ManHinhChiTietInput(navController = navController, laPasswordField = true)
        }

        // 7. Màn hình Chi tiết Layouts
        composable(ManHinh.CHI_TIET_LAYOUT1) {
            Layout1(navController = navController)
        }
        composable(ManHinh.CHI_TIET_LAYOUT2) {
            Layout2(navController = navController)
        }
        composable(ManHinh.CHI_TIET_LAYOUT3) {
            Layout3(navController = navController)
        }

        // ĐÃ XÓA: Loại bỏ định nghĩa Home thừa hoặc bị trùng lặp
        // composable(ManHinh.CHI_TIET_LAYOUT3) { Home(navController = navController) }
    }
}