package com.example.btth.data.model // Package phải khớp với vị trí file

import com.google.gson.annotations.SerializedName

/**
 * Data class này đại diện cho một đối tượng Công việc (Task).
 * Tên các trường đã được ánh xạ chính xác với JSON từ API.
 */
data class Task(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("priority")
    val priority: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("dueDate")
    val dueDate: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updatedAt")
    val updatedAt: String,

    @SerializedName("subtasks")
    val subtasks: List<Subtask>, // <-- `Subtask` được sử dụng ở đây

    @SerializedName("attachments")
    val attachments: List<Attachment>, // <-- `Attachment` được sử dụng ở đây

    @SerializedName("reminders")
    val reminders: List<Reminder> // <-- `Reminder` được sử dụng ở đây
)

/**
 * Data class cho một công việc phụ (Subtask).
 * Nó được định nghĩa ngay trong cùng file này.
 */
data class Subtask( // <<< ĐÂY LÀ NƠI `Subtask` ĐƯỢC ĐỊNH NGHĨA
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("isCompleted")
    val isCompleted: Boolean
)

/**
 * Data class cho một tệp đính kèm (Attachment).
 */
data class Attachment(
    @SerializedName("id")
    val id: Int,

    @SerializedName("fileName")
    val fileName: String,

    @SerializedName("fileUrl")
    val fileUrl: String
)

/**
 * Data class cho một lời nhắc (Reminder).
 */
data class Reminder(
    @SerializedName("id")
    val id: Int,

    @SerializedName("time")
    val time: String,

    @SerializedName("type")
    val type: String
)
