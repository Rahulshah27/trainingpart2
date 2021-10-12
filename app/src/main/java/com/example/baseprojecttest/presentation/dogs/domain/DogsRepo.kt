package com.example.baseprojecttest.presentation.dogs.domain

import com.example.baseprojecttest.data.remote.DogsService
import com.example.baseprojecttest.presentation.dogs.model.Breeds
import com.example.baseprojecttest.presentation.dogs.model.DogsBreedImage


class DogsRepo(private val dogsApi: DogsService): IDogsRepo {
    override suspend fun getAllDogsBreed(): Breeds {
        val dogsList = dogsApi.getAllDogsBreed()
        return dogsList!!
    }

    override suspend fun getDogsImage(breedName: String): DogsBreedImage {
        val dogsImage = dogsApi.getBreedImages(breedName)
        return dogsImage!!
    }
}