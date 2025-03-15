package com.example.luckyandroidapp.data.repository

import com.example.luckyandroidapp.data.model.User
import com.example.luckyandroidapp.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserInfoRepository @Inject constructor(private val apiService: ApiService) {
    fun getUsers(): Flow<List<User>> = flow {
        emit(apiService.getUsers())
    }
}