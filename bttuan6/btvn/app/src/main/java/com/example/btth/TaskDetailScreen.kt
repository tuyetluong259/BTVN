package com.example.btth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.btth.data.model.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    // === CÁC THAM SỐ ĐÚNG CẦN CÓ ===
    taskId: String,
    viewModel: HomeViewModel, // 1. NHẬN VIEWMODEL TỪ ACTIVITY
    onNavigateBack: () -> Unit
) {
    // 2. TỰ ĐỘNG GỌI API KHI MÀN HÌNH MỞ RA
    // LaunchedEffect sẽ chỉ chạy 1 lần khi màn hình được tạo (hoặc khi taskId thay đổi).
    LaunchedEffect(key1 = taskId) {
        viewModel.getTaskDetail(taskId)
    }

    // 3. LẮNG NGHE TRẠNG THÁI DỮ LIỆU TỪ VIEWMODEL
    val state by viewModel.taskDetailState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Detail") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    // Nút Xóa công việc
                    IconButton(onClick = {
                        // Gọi hàm xóa trong ViewModel
                        viewModel.deleteTask(taskId)
                        // Sau khi gọi API xóa, tự động quay lại màn hình danh sách
                        onNavigateBack()
                    }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete Task", tint = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }
    ) { paddingValues ->
        // 4. HIỂN THỊ GIAO DIỆN DỰA VÀO TRẠNG THÁI (LOADING, SUCCESS, ERROR)
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (val currentState = state) {
                is TaskDetailState.Loading -> {
                    // Hiển thị vòng xoay loading ở giữa màn hình
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is TaskDetailState.Success -> {
                    // Nếu thành công, hiển thị nội dung chi tiết
                    TaskDetailContent(task = currentState.task)
                }
                is TaskDetailState.Error -> {
                    // Nếu lỗi, hiển thị thông báo lỗi
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Error: ${currentState.message}",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                // *** THÊM NHÁNH CÒN THIẾU ĐỂ XỬ LÝ KHI TASK BỊ XÓA ***
                is TaskDetailState.TaskDeleted -> {
                    // Không cần hiển thị gì ở đây vì màn hình sẽ được đóng lại ngay lập tức.
                    // Nhánh này là bắt buộc để biểu thức 'when' được hoàn chỉnh.
                }
            }
        }
    }
}

/**
 * Composable này CHỈ chịu trách nhiệm hiển thị dữ liệu của một 'Task', không chứa logic.
 */
@Composable
fun TaskDetailContent(task: Task, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // --- TIÊU ĐỀ VÀ MÔ TẢ ---
        Text(
            text = task.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        if (task.description.isNotBlank()) {
            Text(
                text = task.description,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Spacer(Modifier.height(16.dp))
        Divider()
        Spacer(Modifier.height(16.dp))

        // --- CÁC THÔNG TIN KHÁC ---
        InfoRow(label = "Status", value = task.status)
        InfoRow(label = "Priority", value = task.priority)
        InfoRow(label = "Category", value = task.category)
        InfoRow(label = "Due Date", value = try { task.dueDate.substring(0, 10) } catch (e: Exception) { "N/A" })

        Spacer(Modifier.height(16.dp))
        Divider()
        Spacer(Modifier.height(16.dp))

        // --- SUBTASKS ---
        Text("Subtasks", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))

        if (task.subtasks.isEmpty()) {
            Text("No subtasks.", color = Color.Gray)
        } else {
            Column {
                task.subtasks.forEach { subtask ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = subtask.isCompleted, onCheckedChange = null) // Checkbox chỉ để hiển thị
                        Text(text = subtask.title)
                    }
                }
            }
        }
    }
}

/**
 * Composable phụ để hiển thị một dòng thông tin (Label: Value).
 */
@Composable
private fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
