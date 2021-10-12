package com.example.baseprojecttest.presentation.dogs.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.baseprojecttest.R
import com.example.baseprojecttest.data.model.ResultOf
import com.example.baseprojecttest.network.adapter.BreedAdapter
import com.example.baseprojecttest.presentation.dogs.model.DogsBreedImage
import com.example.baseprojecttest.presentation.dogs.viewmodel.DogsVM
import com.example.baseprojecttest.presentation.dogs.viewmodel.DogsVMFactory
import com.example.baseprojecttest.util.showToast
import kotlinx.android.synthetic.main.activity_dogs_breed_image.*

class DogsBreedImageActivity : AppCompatActivity() {
    private val dogsVM by viewModels<DogsVM>{ DogsVMFactory() }

    private lateinit var adapters:BreedAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dogs_breed_image)

        subscribeToObservers()
        doFetchApi()
        adapters = BreedAdapter(::breedName)
        rvBreedImage.apply {
            adapter = adapters
            layoutManager = GridLayoutManager(applicationContext,3)
            setHasFixedSize(true)
        }

    }

    private fun breedName(s: String) {
        fetchImages(s)

    }

    private fun doFetchApi() {
        dogsVM.getDogsList()
    }
    private fun fetchImages(breedName:String){
        dogsVM.getDogImage(breedName)
    }
    private fun subscribeToObservers() {

        var tempArr:MutableList<String> = ArrayList<String>()
        var tempImgArr:MutableList<DogsBreedImage> = ArrayList<DogsBreedImage>()
        dogsVM.obDogsList.observe(this,{
            when(it){
                is ResultOf.Progress -> showToast(if(it.loading) "Loading.. " else "Loaded")
                is ResultOf.Success -> {
                    tempArr = it.value.toMutableList()
                    tempArr.sortBy { name->
                        name
                    }
                    adapters.breed = tempArr
                    tempArr.forEach { tem->
                        fetchImages(tem)
                    }

                }
                is ResultOf.Empty -> showToast("No data available!")
                is ResultOf.Failure -> {
                    showToast(it.message!!)
                }
            }
        })

        dogsVM.obDogsImage.observe(this, {
            when(it){
                is ResultOf.Progress -> showToast(if(it.loading) "Loading.. " else "Loaded")
                is ResultOf.Success -> {
                    tempImgArr.add(it.value)
                    tempImgArr.sortBy {
                            dogsBreedImage ->
                        dogsBreedImage.message
                    }
                    if (tempImgArr.size == tempArr.size){
                        adapters.imgDog = tempImgArr
                    }
//                    tempImgArr.forEach {dogsBreedImage ->
//                    showToast("I"+tempImgArr.size!!.toString())
//                    showToast("B"+tempArr.size.toString())

//
//                    }

                }
                is ResultOf.Empty -> showToast("No breed available!")
                is ResultOf.Failure -> {
                    showToast(it.message!!)
                }
            }
        })
    }

}