package com.example.baseprojecttest.data.remote

import com.example.baseprojecttest.presentation.dogs.model.Breeds
import com.example.baseprojecttest.presentation.dogs.model.DogBreed
import com.example.baseprojecttest.presentation.dogs.model.DogsBreedImage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DogsService {

    @GET("breeds/list/all")
    fun getBreedsListAsync(): Call<DogBreed<Map<String, List<String>>>>

    @GET("breed/{breedName}/images/random")
    fun getImageByUrlAsync(@Path("breedName") breedName: String): Call<DogBreed<String>>


    @GET("breeds/list/all")
    suspend fun getBreedsList(): DogBreed<Map<String, List<String>>>

    @GET("breed/{breedName}/images/random")
    suspend fun getImageByUrl(@Path("breedName") breedName: String): DogBreed<String>

}