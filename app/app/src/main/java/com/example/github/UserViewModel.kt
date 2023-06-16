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

class UserViewModel: ViewModel() {

    val resultUser = MutableLiveData<ResultSealed>()

    fun getUser(){
        viewModelScope.launch{
                flow{
                    val response = ApiClient
                        .githubService
                        .getUserCapstone()
                    emit(response)
                }.onStart {
                    //mulai
                    resultUser.value = ResultSealed.Loading(true)
                }.onCompletion {
                    //selesai
                    resultUser.value = ResultSealed.Loading(false)
                }.catch {
                    //error
                    Log.e("Error", it.message.toString())
                    resultUser.value = ResultSealed.Error(it)
                }.collect {
                    //dapetin response
                    resultUser.value = ResultSealed.Success(it)
                }
            }
        }
}