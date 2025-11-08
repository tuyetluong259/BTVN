package com.example.btth // Đảm bảo đúng tên package của bạn

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.btth.data.Product // Import Product Model
import com.example.btth.network.ApiService // Import ApiService
import com.example.btth.ui.theme.BtthTheme
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(apiService: ApiService) {
    // 1. Quản lý trạng thái dữ liệu (State)
    var product by remember { mutableStateOf<Product?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    // 2. Gọi API khi Composable được khởi tạo
    LaunchedEffect(Unit) {
        try {
            // Lấy dữ liệu từ ApiService
            val fetchedProduct = apiService.getProductDetail()
            product = fetchedProduct
        } catch (e: Exception) {
            // Xử lý lỗi (ví dụ: mất mạng, lỗi server)
            error = "Lỗi khi tải dữ liệu: ${e.message}"
            e.printStackTrace()
        } finally {
            // Dù thành công hay thất bại, đều tắt loading
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Product detail", fontWeight = FontWeight.SemiBold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color(0xFF007AFF) // Màu xanh dương cho icon
                ),
                navigationIcon = {
                    IconButton(onClick = { /* Handle back action */ }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        // 3. Hiển thị UI dựa trên trạng thái (Loading, Error, Content)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator() // Hiển thị vòng tròn loading
                }
                error != null -> {
                    Text(text = error!!, color = Color.Red, modifier = Modifier.padding(16.dp))
                }
                product != null -> {
                    // Hiển thị nội dung khi dữ liệu đã sẵn sàng
                    ProductDetailContent(product = product!!)
                }
            }
        }
    }
}

// ------------------------------------------------------------------
// B. NỘI DUNG SẢN PHẨM (Component hiển thị dữ liệu)
// ------------------------------------------------------------------

@Composable
fun ProductDetailContent(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        // --- 1. PHẦN HÌNH ẢNH SẢN PHẨM ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            AsyncImage( // Sử dụng Coil để tải ảnh từ URL API
                model = product.imageUrl.ifEmpty { null },
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp) // Kích thước cố định cho ảnh
                    .clip(RoundedCornerShape(8.dp)) // Bo góc
            )
        }

        // --- 2. Phần Thông tin cơ bản ---
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(
                text = product.name, // Dữ liệu từ API
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Hiển thị giá và định dạng tiền tệ
            Text(
                text = "Giá: ${String.format("%,.0fđ", product.price)}",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )
        }

        // --- Đường phân cách ---
        Divider(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp), color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp)

        // --- 3. Phần Mô tả chi tiết ---
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(
                text = product.description, // Dữ liệu từ API
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = Color.DarkGray
            )
        }
    }
}


// Tạo một ApiService giả (Mock) cho hàm Preview
private class MockApiService : ApiService {
    override suspend fun getProductDetail(): Product {
        return Product(
            id = "1",
            name = "Giày Nike Nam Nữ Chính Hãng - Nike Air Force 1 '07 LV8 - Màu Trắng",
            sku = "HF2898-100",
            price = 4000000.0,
            description = "Với giày chạy bộ, từng gram đều quan trọng. Đó là lý do tại sao đế giữa LIGHTSTRIKE PRO mới nhẹ hơn so với phiên bản trước. Mút foam để giữa, siêu nhẹ và thoải mái này có lớp đệm đàn hồi...",
            imageUrl = "https://i.imgur.com/your-mock-image.jpg" // Dùng một URL ảnh thật bất kỳ hoặc ảnh placeholder
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewProductDetailScreen() {
    BtthTheme {
        // Truyền MockApiService vào Preview
        ProductDetailScreen(apiService = MockApiService())
    }
}