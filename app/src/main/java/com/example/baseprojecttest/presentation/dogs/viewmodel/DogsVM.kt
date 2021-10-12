package com.example.baseprojecttest.presentation.dogs.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseprojecttest.data.model.ResultOf
import com.example.baseprojecttest.presentation.dogs.domain.IDogsRepo
import com.example.baseprojecttest.presentation.dogs.model.DogsBreedImage
import com.google.gson.Gson

import kotlinx.coroutines.*
import org.json.JSONObject
import kotlin.coroutines.*

class DogsVM(val dogsRepo: IDogsRepo) :ViewModel(),CoroutineScope {
    private val parentJob = SupervisorJob()
    override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + parentJob

    val obDogsList = MutableLiveData<ResultOf<List<String>>?>()
    val obDogsImage = MutableLiveData<ResultOf<DogsBreedImage>>()

    fun performDelayAction(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                delay(3000)

            }
        }
    }

    fun getDogImage(breedName:String){
        launch {
            runCatching {
                dogsRepo.getDogsImage(breedName)
            }.onSuccess {
                if (it == null){
                    obDogsImage.value = ResultOf.Empty("No Dog Breed Found")
                }else{

                    obDogsImage.value = ResultOf.Success(it)

                }
            }.onFailure {
                obDogsImage.value = ResultOf.Failure(it.localizedMessage!!)
            }
        }
    }
    fun getDogsList(){
        launch {
            runCatching {
                dogsRepo.getAllDogsBreed()
            }.onSuccess {
                if (it == null)
                    obDogsList.value = ResultOf.Empty("No Dog Breed Found")
                else {
                    val tempArr = ArrayList<String>()
                    val toJson = Gson().toJson(it)

                    val obj = JSONObject(toJson)
                    val listJsonPath = obj.getJSONObject("message")
                    val itr = listJsonPath.keys()
                    while (itr.hasNext()){
                        tempArr.add(itr.next())
                    }

                    obDogsList.value = ResultOf.Success(tempArr)

                }
            }.onFailure {
                obDogsList.value = ResultOf.Failure(it.localizedMessage)
            }
        }
    }

}