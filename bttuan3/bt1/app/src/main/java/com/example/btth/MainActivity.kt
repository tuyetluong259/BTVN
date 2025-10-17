package com.example.btth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults // Đã dùng ButtonDefaults của Material3
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // Cần thiết để sử dụng Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFirstAppScreen()
        }
    }
}

/**
 * The main composable function for the screen UI.
 */
@Composable
fun MyFirstAppScreen() {
    // 1. Manage the state of the displayed text.
    var greetingText by remember { mutableStateOf("Hello") }

    // Use a Column to stack elements vertically.
    Column(
        modifier = Modifier
            .fillMaxSize() // Chiếm toàn bộ kích thước màn hình
            .padding(16.dp), // Thêm padding xung quanh
        horizontalAlignment = Alignment.CenterHorizontally // Căn giữa nội dung theo chiều ngang
    ) {
        // 2. Title - "My First App"
        Text(
            text = "My First App",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 32.dp, bottom = 100.dp)
        )

        // Add a flexible space to push the main content down towards the center/bottom
        Spacer(modifier = Modifier.weight(1f))

        // 3. Greeting Text - Changes on button click
        Text(
            text = greetingText,
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )

        // Adjust vertical spacing
        val verticalPadding = if (greetingText == "Hello") 100.dp else 50.dp
        Spacer(modifier = Modifier.height(verticalPadding))


        // 4. Button - "Say Hi!"
        Button(
            // Event handler when the button is clicked
            onClick = {
                // Cập nhật state, kích hoạt recomposition
                greetingText = "I'm Luong Thi Anh Tuyet"
            },
            // THAM SỐ ĐÃ ĐƯỢC SỬA: Trong Material3, tham số là 'colors'
            colors = ButtonDefaults.buttonColors(
                // THAM SỐ ĐÃ ĐƯỢC SỬA: Màu nền là 'containerColor'
                containerColor = Color(0xFF02832F),
                contentColor = Color.White // Màu chữ là 'contentColor'
            ),
            // Style the button
            modifier = Modifier
                .fillMaxWidth(0.6f) // Chiếm 60% chiều rộng
                .height(50.dp) // Chiều cao cố định
        ) {
            Text("Say Hi!", fontSize = 18.sp)
        }

        // Add a flexible space to push the button closer to the bottom
        Spacer(modifier = Modifier.weight(1f))
    }
}
