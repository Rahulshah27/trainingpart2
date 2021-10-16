package com.example.baseprojecttest.presentation.dogs.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.baseprojecttest.R
import com.example.baseprojecttest.data.model.ResultOf
import com.example.baseprojecttest.presentation.dogs.viewmodel.DogsVM
import com.example.baseprojecttest.presentation.dogs.viewmodel.DogsVMFactory
import com.example.baseprojecttest.util.GlideApp
import com.example.baseprojecttest.util.showToast
import kotlinx.android.synthetic.main.activity_dogs_auto_load.*

import kotlinx.coroutines.Job

class DogsAutoLoadActivity : AppCompatActivity() {
    private val dogsVM by viewModels<DogsVM>{ DogsVMFactory() }
    lateinit var job: Job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dogs_auto_load)
//        subscribeToObservers()
//        doFetchApi()
        dogsVM.getDogListAutoReload()
        subscribeToObservers()
    }

    private fun subscribeToObservers() {


        dogsVM.obDogsList.observe(this,{
            when(it){
                is ResultOf.Progress -> showToast(if(it.loading) "Loading.. " else "Loaded")
                is ResultOf.Success -> {
                    it?.let {
                        it.value[0].let {
                            tv1.text = it.breed
                            it.imageUrl?.let { it1-> GlideApp.with(this).load(it1).into(iv1) }
                        }
                        it.value[1].let {
                            tv2.text = it.breed
                            it.imageUrl?.let { it1-> GlideApp.with(this).load(it1).into(iv2) }
                        }
                        it.value[2].let {
                            tv3.text = it.breed
                            it.imageUrl?.let { it1-> GlideApp.with(this).load(it1).into(iv3) }
                        }
                        it.value[3].let {
                            tv4.text = it.breed
                            it.imageUrl?.let { it1-> GlideApp.with(this).load(it1).into(iv4) }
                        }
                        it.value[4].let {
                            tv5.text = it.breed
                            it.imageUrl?.let { it1-> GlideApp.with(this).load(it1).into(iv5) }
                        }
                    }


                }
                is ResultOf.Empty -> showToast("No data available!")
                is ResultOf.Failure -> {
                    showToast(it.message!!)
                }
            }
        })


    }

}