package com.example.baseprojecttest.presentation.dogs.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseprojecttest.data.model.ResultOf
import com.example.baseprojecttest.presentation.dogs.domain.IDogsRepo
import com.example.baseprojecttest.presentation.dogs.model.Dogs
import com.example.baseprojecttest.util.launchPeriodically
import kotlinx.coroutines.*
import kotlin.coroutines.*

class DogsVM(val dogsRepo: IDogsRepo) :ViewModel(),CoroutineScope {
    private val parentJob = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + parentJob

    val obDogsList = MutableLiveData<ResultOf<List<Dogs>>?>()
    //val obDogsImage = MutableLiveData<ResultOf<DogsBreedImage>>()

    fun performDelayAction(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                delay(3000)

            }
        }
    }

    fun getDogListAutoReload(){
        viewModelScope.launchPeriodically(6000){
            launch {
                val list = runCatching { dogsRepo.getAllDogsBreedAsync() }
                list.onSuccess {
                    obDogsList.value = it
                }
            }
        }

    }
    fun getDogsList(){
        viewModelScope.launch {
            runCatching {
                dogsRepo.getAllDogsBreedAsync()
            }.onSuccess {
                obDogsList.value = it
            }
        }
    }

    fun getDogListSynchronously(){
        viewModelScope.launch {
            runCatching {
                dogsRepo.getAllDogsAndImageList()
            }.onSuccess {
                obDogsList.value = it
            }
        }
    }

}