package com.example.btth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

// Dòng import sai đã được xóa

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// Sửa lại tham số để khớp với MainActivity
fun ProfileScreen(authViewModel: AuthViewModel, onLogout: () -> Unit) {
    // Sửa lại cách lấy user để khớp với AuthViewModel của bạn
    val user by authViewModel.user.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    // Gán sự kiện onLogout cho nút Back
                    IconButton(onClick = onLogout) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(32.dp))

            // 1. Hiển thị Ảnh đại diện (nếu có)
            val photoUrl = user?.photoUrl
            if (photoUrl != null) {
                Image(
                    painter = rememberAsyncImagePainter(photoUrl.toString()),
                    contentDescription = "User Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
            } else {
                // Ảnh placeholder nếu không có ảnh đại diện
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        // Lấy ký tự đầu của Display Name hoặc Email
                        text = user?.displayName?.take(1)?.uppercase() ?: user?.email?.take(1)?.uppercase() ?: "A",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 2. Hiển thị Thông tin chi tiết
            ProfileDetailRow(label = "Name", value = user?.displayName ?: "N/A")
            Spacer(modifier = Modifier.height(16.dp))
            ProfileDetailRow(label = "Email", value = user?.email ?: "N/A")
            Spacer(modifier = Modifier.height(16.dp))
            ProfileDetailRow(label = "Date of Birth", value = "23/05/1985 (Simulated)")

            // Dùng Spacer với weight để đẩy nút Logout xuống dưới
            Spacer(modifier = Modifier.weight(1f))

            // 3. Nút Đăng xuất
            Button(
                // Gán sự kiện onLogout
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Sign Out")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// Composable phụ trợ để hiển thị thông tin dạng hàng
@Composable
fun ProfileDetailRow(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Divider(modifier = Modifier.padding(top = 8.dp))
    }
}
