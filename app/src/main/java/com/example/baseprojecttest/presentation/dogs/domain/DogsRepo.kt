package com.example.baseprojecttest.presentation.dogs.domain

import com.example.baseprojecttest.data.model.ResultOf
import com.example.baseprojecttest.data.remote.DogsService
import com.example.baseprojecttest.presentation.dogs.model.Dogs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext


class DogsRepo(private val dogsApi: DogsService): IDogsRepo {

    override suspend fun getAllDogsBreedAsync(): ResultOf<List<Dogs>> = withContext(Dispatchers.IO) {
        val list = mutableListOf<Dogs>()

        val dogBreedListDeferred = async { dogsApi.getBreedsListAsync().execute() }
        val dogBreedListResponse = dogBreedListDeferred.await()

        val dogBreedOneName = dogBreedListResponse.body()?.message?.keys?.toList()?.random()
        val dogBreedTwoName = dogBreedListResponse.body()?.message?.keys?.toList()?.random()
        val dogBreedThreeName = dogBreedListResponse.body()?.message?.keys?.toList()?.random()
        val dogBreedFourName = dogBreedListResponse.body()?.message?.keys?.toList()?.random()
        val dogBreedFiveName = dogBreedListResponse.body()?.message?.keys?.toList()?.random()
        val dogBreedOneImageDeferred = async {
            dogBreedOneName?.let { dogsApi.getImageByUrlAsync(it).execute() }

        }
        val dogBreedTwoImageDeferred = async {
            dogBreedTwoName?.let { dogsApi.getImageByUrlAsync(it).execute() }

        }
        val dogBreedThreeImageDeferred = async {
            dogBreedThreeName?.let { dogsApi.getImageByUrlAsync(it).execute() }

        }
        val dogBreedFourImageDeferred = async {
            dogBreedFourName?.let { dogsApi.getImageByUrlAsync(it).execute() }

        }
        val dogBreedFiveImageDeferred = async {
            dogBreedFiveName?.let { dogsApi.getImageByUrlAsync(it).execute() }

        }
        val dogBreedOne = dogBreedOneImageDeferred.await()
        val dogBreedTwo = dogBreedTwoImageDeferred.await()
        val dogBreedThree = dogBreedThreeImageDeferred.await()
        val dogBreedFour = dogBreedFourImageDeferred.await()
        val dogBreedFive = dogBreedFiveImageDeferred.await()

        if (dogBreedFive?.isSuccessful!!) list.add(Dogs(dogBreedFiveName, dogBreedFive.body()?.message))
        if (dogBreedFour?.isSuccessful!!) list.add(Dogs(dogBreedFourName, dogBreedFour.body()?.message))
        if (dogBreedThree?.isSuccessful!!) list.add(Dogs(dogBreedThreeName, dogBreedThree.body()?.message))

        if (dogBreedTwo?.isSuccessful!!) list.add(Dogs(dogBreedTwoName, dogBreedTwo.body()?.message))
        if (dogBreedOne?.isSuccessful!!) list.add(Dogs(dogBreedOneName, dogBreedOne.body()?.message))

        ResultOf.Success(list)
    }


    override suspend fun getAllDogsAndImageList(): ResultOf<List<Dogs>> {
        val list = mutableListOf<Dogs>()

        val dogBreedList = dogsApi.getBreedsList().message.keys.toList()
        dogBreedList.forEach {
            val dogImage = dogsApi.getImageByUrl(it).message
            list.add(Dogs(it,dogImage))
        }
        return ResultOf.Success(list)
    }

}