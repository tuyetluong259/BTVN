package com.example.btth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AuthScreen(
    onGoogleSignInClicked: () -> Unit,
    signInStatus: String?
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onGoogleSignInClicked) {
            Text("Login with Google")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hiển thị thông báo thành công hoặc thất bại
        signInStatus?.let {
            val isSuccess = !it.startsWith("Error:")
            val backgroundColor = if (isSuccess) Color.Green.copy(alpha = 0.1f) else Color.Red.copy(alpha = 0.1f)
            val textColor = if (isSuccess) Color.Black else Color.Red

            Surface(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = backgroundColor,
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = it,
                    color = textColor,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
