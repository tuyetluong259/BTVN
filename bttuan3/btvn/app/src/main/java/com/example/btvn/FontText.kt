// FontText.kt
package com.example.btvn

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManHinhChiTietText(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Text Detail") },
                // Thêm nút quay lại
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                // Điều chỉnh padding để đẩy nội dung xuống dưới TopAppBar và căn lề trái như hình
                .padding(top = 120.dp, start = 32.dp, end = 16.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.Start
        ) {
            Text(
                text = buildAnnotatedString {
                    val baseFontSize = 24.sp

                    val brownColor = Color(0xFFC78440)
                    withStyle(style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.LineThrough,
                        color = brownColor,
                        fontSize = baseFontSize
                    )) {
                        append("The quick ")
                    }



                    withStyle(style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = brownColor,
                        fontSize = 30.sp // Brown to và đậm hơn
                    )) {
                        append("Brown")
                    }

                    // DÒNG 2: fox j u m p s over
                    append("\nfox j u m p s ") // Thêm khoảng trắng giữa j u m p s
                    withStyle(style = SpanStyle(
                        fontWeight = FontWeight.Black, // Rất đậm cho 'over'
                        fontSize = baseFontSize
                    )) {
                        append("over")
                    }

                    // DÒNG 3: the lazy dog.
                    append("\n")
                    // TỪ 'the' có gạch chân
                    withStyle(style = SpanStyle(
                        textDecoration = TextDecoration.Underline,
                        color = Color.Black,
                        fontSize = baseFontSize
                    )) {
                        append("the")
                    }
                    append(" ")

                    // TỪ 'lazy' nghiêng, gạch chân và màu xám nhạt hơn
                    withStyle(style = SpanStyle(
                        color = Color.DarkGray,
                        fontStyle = FontStyle.Italic,
                        textDecoration = TextDecoration.Underline,
                        fontSize = baseFontSize
                    )) {
                        append("lazy")
                    }

                    // TỪ 'dog' nghiêng, gạch chân
                    withStyle(style = SpanStyle(
                        color = Color.DarkGray,
                        fontStyle = FontStyle.Italic,
                        textDecoration = TextDecoration.Underline,
                        fontSize = baseFontSize
                    )) {
                        append(" dog")
                    }
                    // Dấu chấm thường (không gạch chân)
                    withStyle(style = SpanStyle(
                        color = Color.DarkGray,
                        fontSize = baseFontSize
                    )) {
                        append(".")
                    }

                },
                fontSize = 24.sp, // Kích thước chữ cơ bản
                lineHeight = 32.sp, // Khoảng cách dòng
            )
        }
    }
}
@Preview(showBackground = true) // Hiển thị nền
@Composable
fun PreviewManHinhChiTietText() {
    // SỬ DỤNG MATERIAL THEME CỦA BẠN (Ví dụ: BtvnTheme)
    MaterialTheme {
        Surface {
            // 1. Lấy Context hiện tại (cần thiết cho việc khởi tạo NavController)
            val context = LocalContext.current

            // 2. Tạo NavController giả (dummy) trong khối remember
            val dummyNavController = remember {
                NavController(context)
            }

            // 3. Truyền NavController giả vào hàm Composable
            ManHinhChiTietText(navController = dummyNavController)
        }
    }
}