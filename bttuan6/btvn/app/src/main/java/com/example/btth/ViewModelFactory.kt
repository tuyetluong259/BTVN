package com.example.btth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Lớp này chịu trách nhiệm tạo ra các instance của ViewModel.
 * Nó đảm bảo rằng HomeViewModel sẽ được khởi tạo cùng với TaskRepository.
 * Đây là một phần của mô hình "Dependency Injection" (Tiêm phụ thuộc) thủ công.
 */
class ViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.NewInstanceFactory() {

    // Ghi đè hàm create để tùy chỉnh cách ViewModel được tạo
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Nếu lớp được yêu cầu là HomeViewModel, hãy tạo nó với repository
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        // Nếu là ViewModel khác, có thể thêm logic ở đây
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    /**
     * Companion object để triển khai Singleton Pattern.
     * Điều này đảm bảo chỉ có MỘT instance của Factory và Repository trong toàn bộ ứng dụng.
     */
    companion object {
        // Biến tĩnh để giữ một instance duy nhất của Factory
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        // Hàm để lấy instance của Factory
        fun getInstance(): ViewModelFactory {
            // Nếu INSTANCE đã tồn tại, trả về nó.
            // Nếu chưa, tạo một instance mới trong một khối synchronized để đảm bảo an toàn luồng.
            return INSTANCE ?: synchronized(this) {
                // Khởi tạo các dependencies cần thiết chỉ một lần
                val apiService = RetrofitClient.instance
                val repository = TaskRepository(apiService)
                // Tạo Factory với repository
                val instance = ViewModelFactory(repository)
                INSTANCE = instance
                // Trả về instance vừa tạo
                instance
            }
        }
    }
}
