package com.example.baseprojecttest.presentation.dogs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baseprojecttest.network.NetworkModule
import com.example.baseprojecttest.presentation.dogs.domain.DogsRepo
import com.example.baseprojecttest.presentation.dogs.domain.IDogsRepo

class DogsVMFactory: ViewModelProvider.Factory {

    init {
        getInstance()
    }
    companion object{
        @Volatile
        private var INSTANCE:IDogsRepo?=null
        fun getInstance() =
            INSTANCE ?: synchronized(DogsVMFactory::class.java){
                INSTANCE?:DogsRepo(
                    NetworkModule.makeApiService()
                ).also { INSTANCE = it }
            }
        fun destroyInstance(){
            INSTANCE = null
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IDogsRepo::class.java).newInstance(INSTANCE)
    }

}