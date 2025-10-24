package com.example.btth.data

import androidx.annotation.DrawableRes
import com.example.btth.R // Import R để truy cập tài nguyên

// --- 1. Data Class cho nội dung mỗi trang Onboarding ---
data class OnboardingItem(
    val title: String,
    val description: String,
    @DrawableRes val imageResId: Int
)

// Dữ liệu mẫu cho 3 trang giới thiệu
val onboardingPages = listOf(
    OnboardingItem(
        title = "Easy Time Management",
        description = "With management based on priority and daily tasks, it will give you convenience in managing and determining the tasks that must be done first.",
        imageResId = R.drawable.img // Tạo các drawable này trong thư mục res/drawable
    ),
    OnboardingItem(
        title = "Increase Work Effectiveness",
        description = "Time management and the determination of more important tasks will give your job statistics better and always improve.",
        imageResId = R.drawable.img2
    ),
    OnboardingItem(
        title = "Reminder Notification",
        description = "The advantage of this application is that it can provide reminders for you so you don't forget to keep up your tasks and schedule at the time you have set.",
        imageResId = R.drawable.img3
    )
)

// --- 2. Object chứa các Route cho Navigation ---
object Screen {
    const val SPLASH = "splash_screen"
    const val ONBOARDING_FLOW = "onboarding_flow" // Route cho toàn bộ Pager
    const val MAIN_APP = "main_app_screen"
}
