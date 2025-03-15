package com.example.luckyandroidapp.data.remote
import com.example.luckyandroidapp.data.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}
