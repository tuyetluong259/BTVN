package com.example.btth

import com.example.btth.data.model.ApiResponse
import com.example.btth.data.model.Task
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/** URL gốc của API */
const val BASE_URL = "https://amock.io/"

/**
 * Interface khai báo các endpoint.
 */
interface TaskApiService {

    /** 1️⃣ Lấy danh sách task */
    @GET("api/researchUTH/tasks")
    suspend fun getTasks(): Response<ApiResponse<List<Task>>>

    /** 2️⃣ Lấy danh sách task (endpoint thứ 2 theo đề) */
    @GET("api/researchUTH/task/list")
    suspend fun getTaskList(): Response<ApiResponse<List<Task>>>

    /** 3️⃣ Lấy chi tiết một task theo ID */
    @GET("api/researchUTH/task/{id}")
    suspend fun getTaskDetail(
        @Path("id") taskId: String
    ): Response<ApiResponse<Task>>

    /** Xóa task theo ID */
    @DELETE("api/researchUTH/task/{id}")
    suspend fun deleteTask(
        @Path("id") taskId: String
    ): Response<ApiResponse<Unit>>

    /** Tạo task mới */
    @POST("api/researchUTH/task")
    suspend fun createTask(
        @Body task: Task
    ): Response<ApiResponse<Task>>

    /** Cập nhật task */
    @PUT("api/researchUTH/task/{id}")
    suspend fun updateTask(
        @Path("id") taskId: String,
        @Body task: Task
    ): Response<ApiResponse<Task>>
}

/**
 * Singleton Retrofit Client
 */
object RetrofitClient {
    val instance: TaskApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TaskApiService::class.java)
    }
}
