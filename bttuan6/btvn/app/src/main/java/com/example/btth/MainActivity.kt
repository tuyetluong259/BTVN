package com.example.btth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.btth.ui.theme.BtthTheme

// Định nghĩa các route để tránh gõ sai và dễ quản lý
object Routes {
    const val TASK_LIST = "task_list"
    const val TASK_DETAIL = "task_detail/{taskId}" // Route cho màn hình chi tiết, có tham số

    // Hàm helper để tạo đường dẫn chi tiết dễ dàng hơn
    fun taskDetail(taskId: String) = "task_detail/$taskId"
}

class MainActivity : ComponentActivity() {

    // --- BƯỚC 1: LẤY FACTORY DUY NHẤT TỪ COMPANION OBJECT ---
    // Không cần khởi tạo bất cứ thứ gì khác ở đây.
    // ViewModelFactory.getInstance() sẽ tự lo việc tạo Retrofit, Repository.
    private val factory = ViewModelFactory.getInstance()

    // --- BƯỚC 2: KHỞI TẠO VIEWMODEL SỬ DỤNG FACTORY ĐÓ ---
    // Tất cả ViewModel cần Repository sẽ dùng chung factory này.
    private val homeViewModel: HomeViewModel by viewModels { factory }
    // private val authViewModel by viewModels() // Giữ lại nếu bạn có AuthViewModel không cần factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BtthTheme {
                // --- BƯỚC 3: TRUYỀN VIEWMODEL VÀO APP ---
                AppNavigation(homeViewModel = homeViewModel)
            }
        }
    }
}

@Composable
fun AppNavigation(
    homeViewModel: HomeViewModel // Nhận HomeViewModel
) {
    val navController = rememberNavController()

    // Màn hình bắt đầu là danh sách công việc
    NavHost(navController = navController, startDestination = Routes.TASK_LIST) {

        // Màn hình Danh sách Công việc (Trang chủ)
        composable(Routes.TASK_LIST) {
            TaskListScreen(
                viewModel = homeViewModel, // Truyền ViewModel xuống màn hình con
                onTaskClick = { taskId ->
                    // Điều hướng đến màn hình chi tiết với taskId tương ứng
                    navController.navigate(Routes.taskDetail(taskId))
                }
            )
        }

        // Màn hình Chi tiết Công việc
        composable(
            route = Routes.TASK_DETAIL,
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            // Lấy taskId từ arguments
            val taskId = backStackEntry.arguments?.getString("taskId")
            if (taskId != null) {
                // Sử dụng lại cùng một homeViewModel instance cho màn hình chi tiết
                TaskDetailScreen(
                    taskId = taskId,
                    viewModel = homeViewModel, // Truyền cùng viewModel vào
                    onNavigateBack = {
                        // Quay lại màn hình trước đó (TaskListScreen)
                        navController.popBackStack()
                    }
                )
            } else {
                // Xử lý trường hợp taskId bị null, ví dụ quay lại
                navController.popBackStack()
            }
        }
    }
}
