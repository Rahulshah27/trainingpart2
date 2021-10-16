package com.example.baseprojecttest.presentation.dogs.domain

import com.example.baseprojecttest.data.model.ResultOf
import com.example.baseprojecttest.presentation.dogs.model.Dogs

interface IDogsRepo {
    suspend fun getAllDogsBreedAsync(): ResultOf<List<Dogs>>
    suspend fun getAllDogsAndImageList():ResultOf<List<Dogs>>

//    suspend fun getAllDogsBreed(): Breeds
//    suspend fun getDogsImage(breedName:String): DogsBreedImage
}