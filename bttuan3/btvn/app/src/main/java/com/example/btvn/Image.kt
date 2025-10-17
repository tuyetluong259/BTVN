// ManHinhChiTietImage.kt
package com.example.btvn

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.remember

// Lưu ý: Bạn cần phải có các file ảnh sau trong thư mục res/drawable
// Thay thế R.drawable.image_uth_1 và R.drawable.image_uth_2 bằng tên file ảnh thật của bạn.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManHinhChiTietImage(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Images") }, // Tiêu đề "Images"
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // Nút quay lại
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Quay lại"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 1. IMAGE 1 (Giả lập ảnh từ URL)
            // Thay R.drawable.image_uth_1 bằng tài nguyên ảnh thực tế của bạn
            Image(
                painter = painterResource(id = R.drawable.img_1),
                contentDescription = "Ảnh trường 1",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(top = 16.dp),
                contentScale = ContentScale.Crop
            )

            // Chú thích URL
            Text(
                text = "https://plo.vn/truong-dai-hoc-giao-thong-van-tai-tphcm-dao-tao-gan-lien-thuc-tien-post850383.html",
                modifier = Modifier.padding(vertical = 8.dp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp)) // Khoảng cách giữa 2 phần

            // 2. IMAGE 2 (Giả lập ảnh In app)
            // Thay R.drawable.image_uth_2 bằng tài nguyên ảnh thực tế của bạn
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "Ảnh trường 2",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            // Chú thích "In app"
            Text(
                text = "In app",
                modifier = Modifier.padding(top = 8.dp),
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// =========================================================
// PHẦN PREVIEW
// =========================================================

@Preview(showBackground = true)
@Composable
fun PreviewManHinhChiTietImage() {
    // SỬ DỤNG MATERIAL THEME CỦA BẠN (Ví dụ: BtvnTheme)
    MaterialTheme {
        Surface {
            // Tạo NavController giả
            val context = LocalContext.current
            val dummyNavController = remember { NavController(context) }

            ManHinhChiTietImage(navController = dummyNavController)
        }
    }
}