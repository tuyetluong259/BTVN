package com.example.btth.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.btth.* // Import tất cả các màn hình và ViewModel
import com.google.firebase.auth.FirebaseAuth

object Routes {
    const val LOGIN = "login"
    const val TASK_LIST = "task_list"
    const val TASK_DETAIL = "task_detail/{taskId}"

    fun taskDetail(taskId: String) = "task_detail/$taskId"
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    // --- SỬA LỖI: Sử dụng ViewModelFactory để tạo ViewModel ---
    // Điều này đảm bảo HomeViewModel có TaskRepository để gọi API
    val factory = ViewModelFactory.getInstance()
    val homeViewModel: HomeViewModel = viewModel(factory = factory)

    // AuthViewModel có thể không cần factory nếu nó không có dependency
    val authViewModel: AuthViewModel = viewModel()

    // Xác định màn hình bắt đầu dựa trên trạng thái đăng nhập
    val startDestination = if (FirebaseAuth.getInstance().currentUser != null) {
        Routes.TASK_LIST
    } else {
        Routes.LOGIN
    }

    NavHost(navController = navController, startDestination = startDestination) {

        // 1. Màn hình Đăng nhập
        composable(Routes.LOGIN) {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = {
                    // Xóa màn hình login khỏi backstack sau khi đăng nhập thành công
                    navController.navigate(Routes.TASK_LIST) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // 2. Màn hình Danh sách công việc
        composable(Routes.TASK_LIST) {
            TaskListScreen(
                viewModel = homeViewModel,
                onTaskClick = { taskId ->
                    navController.navigate(Routes.taskDetail(taskId))
                }
            )
        }

        // 3. Màn hình Chi tiết công việc
        composable(
            route = Routes.TASK_DETAIL,
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            if (taskId != null) {
                TaskDetailScreen(
                    taskId = taskId,
                    viewModel = homeViewModel, // TRUYỀN homeViewModel VÀO ĐÂY
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
