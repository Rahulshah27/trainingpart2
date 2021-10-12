package com.example.baseprojecttest.data.remote

import com.example.baseprojecttest.presentation.dogs.model.Breeds
import com.example.baseprojecttest.presentation.dogs.model.DogsBreedImage
import retrofit2.http.GET
import retrofit2.http.Path

interface DogsService {

    @GET("breeds/list/all")
    suspend fun getAllDogsBreed(): Breeds?

    @GET("breed/{breed_name}/images/random")
    suspend fun getBreedImages(@Path("breed_name") breed_name:String): DogsBreedImage?

}