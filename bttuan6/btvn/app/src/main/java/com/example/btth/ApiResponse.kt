package com.example.btth.data.model

import com.google.gson.annotations.SerializedName

/**
 * Lớp đại diện cho cấu trúc JSON chung mà API trả về.
 * @param T kiểu dữ liệu (Task, List<Task>, ...)
 */
data class ApiResponse<T>(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: T
)
