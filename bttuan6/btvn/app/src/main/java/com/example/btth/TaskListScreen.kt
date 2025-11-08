package com.example.btth

import androidx.compose.foundation.Image // <<< THÊM IMPORT NÀY
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed // Sửa thành itemsIndexed để lấy index
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource // <<< THÊM IMPORT NÀY
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.btth.data.model.Task

// ✅ ĐỊNH NGHĨA DANH SÁCH CÁC MÀU SẮC
val taskColors = listOf(
    Color(0xFFE0BBE4), // Lavender
    Color(0xFF957DAD), // Dull Lavender
    Color(0xFFD291BC), // Pastel Violet
    Color(0xFFFEC8D8), // Pink Lavender
    Color(0xFFA2D2FF)  // Light Blue
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: HomeViewModel,
    onTaskClick: (String) -> Unit
) {
    val state by viewModel.taskListState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SmartTasks", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    // === THAY ĐỔI TỪ ICON SANG IMAGE ===
                    Image(
                        // Gọi hình ảnh 'img_2' từ thư mục res/drawable
                        painter = painterResource(id = R.drawable.img_2),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(32.dp) // Điều chỉnh kích thước ảnh cho phù hợp
                    )
                }
            )
        }
    ) { padding ->
        when (val currentState = state) {
            is TaskListState.Loading -> LoadingView(Modifier.padding(padding))
            is TaskListState.EmptyView -> EmptyTaskView(Modifier.padding(padding))
            is TaskListState.Success -> TaskListView(
                tasks = currentState.tasks,
                onTaskClick = onTaskClick,
                modifier = Modifier.padding(padding)
            )
            is TaskListState.Error -> ErrorView(currentState.message, Modifier.padding(padding))
        }
    }
}

// --- Composable: List View ---
@Composable
fun TaskListView(
    tasks: List<Task>,
    onTaskClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(tasks, key = { _, task -> task.id }) { index, task ->
            val cardColor = taskColors[index % taskColors.size]
            TaskListItem(
                task = task,
                containerColor = cardColor,
                onClick = { onTaskClick(task.id.toString()) }
            )
        }
    }
}

// --- Composable: List Item ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListItem(
    task: Task,
    containerColor: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(task.title, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
            Spacer(Modifier.height(4.dp))
            if (task.description.isNotBlank()) {
                Text(
                    task.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    color = Color.Black.copy(alpha = 0.7f)
                )
                Spacer(Modifier.height(8.dp))
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                val isCompleted = task.status.equals("Completed", ignoreCase = true)
                Text(
                    text = task.status,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isCompleted) Color(0xFF006400) else Color(0xFFB00020),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    try { task.dueDate.substring(0, 10) } catch (e: Exception) { "N/A" },
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black.copy(alpha = 0.6f)
                )
            }
        }
    }
}

// Các Composable LoadingView, ErrorView, EmptyTaskView giữ nguyên như cũ
@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(message: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Error: $message",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun EmptyTaskView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("No Tasks Yet!", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(8.dp))
        Text("Stay productive—add something to do", color = Color.Gray)
    }
}
