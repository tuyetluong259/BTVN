package com.example.btth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btth.data.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// State cho màn hình danh sách công việc
sealed class TaskListState {
    object Loading : TaskListState()
    object EmptyView : TaskListState()
    data class Success(val tasks: List<Task>) : TaskListState()
    data class Error(val message: String) : TaskListState()
}

// State cho màn hình chi tiết công việc
sealed class TaskDetailState {
    object Loading : TaskDetailState()
    data class Success(val task: Task) : TaskDetailState()
    data class Error(val message: String) : TaskDetailState()
    object TaskDeleted : TaskDetailState()
}

// ViewModel chính của bạn
class HomeViewModel(private val repository: TaskRepository) : ViewModel() {

    // State cho màn hình List
    private val _taskListState = MutableStateFlow<TaskListState>(TaskListState.Loading)
    val taskListState: StateFlow<TaskListState> = _taskListState

    // State cho màn hình Detail
    private val _taskDetailState = MutableStateFlow<TaskDetailState>(TaskDetailState.Loading)
    val taskDetailState: StateFlow<TaskDetailState> = _taskDetailState

    init {
        // Tự động tải danh sách khi ViewModel được tạo
        getTasks()
    }

    // Hàm gọi API để lấy danh sách công việc
    fun getTasks() {
        _taskListState.value = TaskListState.Loading
        viewModelScope.launch {
            val result = repository.getTasks()
            // Chỉ định rõ kiểu dữ liệu để code rõ ràng hơn
            result.onSuccess { tasks: List<Task> ->
                _taskListState.value = if (tasks.isNullOrEmpty()) {
                    TaskListState.EmptyView
                } else {
                    TaskListState.Success(tasks)
                }
            }.onFailure { e ->
                _taskListState.value = TaskListState.Error(e.message ?: "Lỗi kết nối API")
            }
        }
    }

    fun getTaskDetail(taskId: String) {
        _taskDetailState.value = TaskDetailState.Loading
        viewModelScope.launch {
            val result = repository.getTaskDetail(taskId)
            result.onSuccess { task: Task ->
                _taskDetailState.value = TaskDetailState.Success(task)
            }.onFailure { e ->
                _taskDetailState.value = TaskDetailState.Error(e.message ?: "Lỗi tải chi tiết công việc")
            }
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            val result = repository.deleteTask(taskId)
            result.onSuccess {
                _taskDetailState.value = TaskDetailState.TaskDeleted
                getTasks()

            }.onFailure { e ->
                _taskDetailState.value = TaskDetailState.Error(e.message ?: "Xóa thất bại")
            }
        }
    }
}
