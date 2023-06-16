package com.example.github

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel:ViewModel() {
    val resultDetail = MutableLiveData<ResultSealed>()

    fun getDetail(username: String){
        viewModelScope.launch{
            flow{
                val response = ApiClient
                    .githubService
                    .getDetailCapstone(username)

                emit(response)
            }.onStart {
                //mulai
                resultDetail.value = ResultSealed.Loading(true)
            }.onCompletion {
                //selesai
                resultDetail.value = ResultSealed.Loading(false)
            }.catch {
                //error
                Log.e("Error", it.message.toString())
                resultDetail.value = ResultSealed.Error(it)
            }.collect {
                //dapetin response
                resultDetail.value = ResultSealed.Success(it)
            }
        }
    }
}