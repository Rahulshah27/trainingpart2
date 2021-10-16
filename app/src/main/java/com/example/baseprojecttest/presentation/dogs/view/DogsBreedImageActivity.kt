package com.example.baseprojecttest.presentation.dogs.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.baseprojecttest.R
import com.example.baseprojecttest.data.model.ResultOf
import com.example.baseprojecttest.network.adapter.BreedAdapter
import com.example.baseprojecttest.presentation.dogs.viewmodel.DogsVM
import com.example.baseprojecttest.presentation.dogs.viewmodel.DogsVMFactory
import com.example.baseprojecttest.util.showToast
import kotlinx.android.synthetic.main.activity_dogs_breed_image.*

class DogsBreedImageActivity : AppCompatActivity() {
    private val dogsVM by viewModels<DogsVM>{ DogsVMFactory() }
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {BreedAdapter()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dogs_breed_image)

        doFetchApi()
        subscribeToObservers()
        rvBreedImage.adapter = adapter
        rvBreedImage.apply {
            layoutManager = GridLayoutManager(applicationContext,3)
            setHasFixedSize(true)
        }
    }

    private fun doFetchApi() {
        dogsVM.getDogListSynchronously()
    }

    private fun subscribeToObservers() {
            dogsVM.obDogsList.observe(this,{
                when(it){
                    is ResultOf.Progress -> showToast(if(it.loading) "Loading.. " else "Loaded")
                    is ResultOf.Success -> {
                        adapter.breed = it.value
                    }
                    is ResultOf.Empty -> showToast("No data available!")
                    is ResultOf.Failure -> {
                        showToast(it.message!!)
                    }
                }
            })
    }

}