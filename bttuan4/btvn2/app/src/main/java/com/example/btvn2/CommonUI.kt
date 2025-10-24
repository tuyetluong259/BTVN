package com.example.btvn2

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// --- Header chứa Logo UTH SmartTasks ---
@Composable
fun LogoHeader() {
    Column(
        modifier = Modifier.fillMaxWidth(), // Chiếm hết chiều rộng để căn giữa
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "UTH Logo",
            modifier = Modifier
                .height(90.dp)
                .width(180.dp)
        )
        Text(
            text = "SmartTasks",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

// --- Top AppBar (Thanh điều hướng trên cùng) với Logo ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarLogo(title: String, navController: NavController) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Quay lại"
                )
            }
        },
        actions = {
            // Tham số 'actions' đã là một RowScope, không cần Row lồng vào trong
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "UTH Logo Nhỏ",
                modifier = Modifier
                    .height(40.dp)
                    .padding(end = 16.dp)
            )
        },
        // Thêm màu sắc để nhất quán với theme của Material 3
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}


// --- Các hàm Preview để xem trước ---

@Preview(showBackground = true, name = "Logo Header Preview")
@Composable
fun LogoHeaderPreview() {
    // Bọc trong Theme để Preview có giao diện đúng
    MaterialTheme {
        Column(modifier = Modifier.padding(32.dp)) {
            LogoHeader()
        }
    }
}

@Preview(showBackground = true, name = "Top App Bar Preview")
@Composable
fun TopAppBarLogoPreview() {
    MaterialTheme {
        // Tạo một NavController giả cho mục đích Preview
        val fakeNavController = NavController(LocalContext.current)
        TopAppBarLogo(title = "Màn hình chi tiết", navController = fakeNavController)
    }
}

