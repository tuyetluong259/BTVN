package com.example.btth

// Import các lớp cần thiết
import com.example.btth.data.model.ApiResponse
import com.example.btth.data.model.Task

class TaskRepository(private val apiService: TaskApiService) {

    // Lấy tất cả công việc (dùng cho màn hình Home)
    suspend fun getTasks(): Result<List<Task>> {
        return try {
            // Giả định apiService.getTasks() trả về Response<ApiResponse<List<Task>>>
            val response = apiService.getTasks()

            if (response.isSuccessful && response.body() != null) {
                val apiResponse = response.body()!!
                // **SỬA LỖI LOGIC: Kiểm tra isSuccess và chỉ lấy trường 'data'**
                if (apiResponse.isSuccess) {
                    Result.success(apiResponse.data) // Trả về List<Task>
                } else {
                    Result.failure(Exception(apiResponse.message ?: "API trả về lỗi nhưng không có thông báo"))
                }
            } else {
                // Xử lý lỗi HTTP (ví dụ: 404, 500)
                Result.failure(Exception("Lỗi tải danh sách: ${response.code()}"))
            }
        } catch (e: Exception) {
            // Xử lý lỗi mạng/parsing
            Result.failure(e)
        }
    }

    // Lấy chi tiết công việc
    suspend fun getTaskDetail(taskId: String): Result<Task> {
        return try {
            // Giả định apiService.getTaskDetail() trả về Response<ApiResponse<Task>>
            val response = apiService.getTaskDetail(taskId)

            if (response.isSuccessful && response.body() != null) {
                val apiResponse = response.body()!!
                // **SỬA LỖI LOGIC: Tương tự như trên**
                if (apiResponse.isSuccess) {
                    Result.success(apiResponse.data) // Trả về đối tượng Task
                } else {
                    Result.failure(Exception(apiResponse.message ?: "API trả về lỗi chi tiết công việc"))
                }
            } else {
                Result.failure(Exception("Không tìm thấy chi tiết công việc: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Xóa công việc (Hàm này có thể giữ nguyên nếu API delete không trả về body)
    suspend fun deleteTask(taskId: String): Result<Unit> {
        return try {
            val response = apiService.deleteTask(taskId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Lỗi xóa công việc: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
