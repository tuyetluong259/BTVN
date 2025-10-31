package com.example.myapplication

import androidx.compose.foundation.layout.Arrangement // ✅ 1. IMPORT THÊM
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameScreen(viewModel: GameViewModel) {
    // Lấy giá trị từ LiveData và biến nó thành State
    // Dấu "" hoặc giá trị ban đầu là giá trị sẽ được dùng khi LiveData chưa có dữ liệu
    val secretWordDisplay by viewModel.secretWordDisplay.observeAsState("")
    val incorrectGuesses by viewModel.incorrectGuesses.observeAsState("")
    val livesLeft by viewModel.livesLeft.observeAsState(0)

    // Tạo một State riêng trong Composable để lưu trữ nội dung người dùng nhập
    var guess by remember { mutableStateOf("") }

    // Dựng giao diện bằng các Composable function
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround

    ) {
        Text(text = "Lives left: $livesLeft", fontSize = 18.sp)
        Text(text = secretWordDisplay, fontSize = 32.sp)
        Text(text = "Incorrect guesses: $incorrectGuesses", fontSize = 16.sp)

        TextField(
            value = guess,
            onValueChange = { guess = it },
            label = { Text("Enter a letter") }
        )

        Button(onClick = {
            viewModel.makeGuess(guess) // Gọi hàm trong ViewModel
            guess = ""                 // Xóa trống ô nhập liệu sau khi đoán
        }) {
            Text("GUESS")
        }
    }
}
