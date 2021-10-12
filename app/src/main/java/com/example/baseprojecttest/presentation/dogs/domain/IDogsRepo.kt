package com.example.baseprojecttest.presentation.dogs.domain

import com.example.baseprojecttest.presentation.dogs.model.Breeds
import com.example.baseprojecttest.presentation.dogs.model.DogsBreedImage
import kotlinx.coroutines.flow.Flow

interface IDogsRepo {
    suspend fun getAllDogsBreed(): Breeds
    suspend fun getDogsImage(breedName:String): DogsBreedImage
}