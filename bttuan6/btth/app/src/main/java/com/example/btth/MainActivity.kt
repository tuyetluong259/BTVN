package com.example.btth
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.btth.network.ApiService // Import ApiService
import com.example.btth.ui.theme.BtthTheme // Import Theme của bạn

class MainActivity : ComponentActivity() {

    // 1. Khởi tạo ApiService (Component)
    private val apiService = ApiService.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 2. Thiết lập giao diện Compose
        setContent {
            BtthTheme {
                // 3. Khởi chạy màn hình chi tiết sản phẩm và truyền Service
                ProductDetailScreen(apiService = apiService)
            }
        }
    }
}

