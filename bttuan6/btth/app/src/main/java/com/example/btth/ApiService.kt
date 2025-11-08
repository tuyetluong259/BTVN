package com.example.btth.network

import com.example.btth.data.ApiResponse
import com.example.btth.data.Product
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// Định nghĩa base URL và endpoint
private const val BASE_URL = "https://mock.apidog.com/m1/890655-872447-default/v2"
private const val PRODUCT_ENDPOINT = "/product"

// Interface định nghĩa các "hợp đồng" mà ApiService phải thực hiện.
interface ApiService {
    // Hợp đồng: Phải có một hàm để lấy chi tiết sản phẩm.
    suspend fun getProductDetail(): Product

    // Cung cấp một cách chuẩn để tạo ra một đối tượng ApiService thực thi.
    companion object {
        fun create(): ApiService {
            return ApiServiceImpl()
        }
    }
}

// Class này chứa logic thực sự để gọi API bằng Ktor.
private class ApiServiceImpl : ApiService {
    private val client = HttpClient(Android) {
        // Cấu hình Content Negotiation để tự động chuyển đổi JSON sang Kotlin Object
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // Bỏ qua các trường không cần thiết trong JSON
                isLenient = true // Cho phép JSON không quá nghiêm ngặt
            })
        }
    }

    // Ghi đè (override) và triển khai hàm đã định nghĩa trong interface.
    override suspend fun getProductDetail(): Product {
        // Thực hiện request GET
        val response = client.get("$BASE_URL$PRODUCT_ENDPOINT")

        // ======================= SỬA LỖI Ở ĐÂY =======================
        //
        // Yêu cầu Ktor phân tích JSON trả về trực tiếp thành đối tượng Product,
        // không cần thông qua lớp ApiResponse nữa.
        //
        // Dòng cũ: val apiResponse = response.body<ApiResponse>()
        //           return apiResponse.data
        //
        // Dòng mới:
        return response.body<Product>()
        // =============================================================
    }
}
