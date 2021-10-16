package com.example.baseprojecttest.presentation.dogs.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.baseprojecttest.R
import com.example.baseprojecttest.data.model.ResultOf
import com.example.baseprojecttest.presentation.dogs.viewmodel.DogsVM
import com.example.baseprojecttest.presentation.dogs.viewmodel.DogsVMFactory
import com.example.baseprojecttest.util.GlideApp
import com.example.baseprojecttest.util.showToast
import kotlinx.android.synthetic.main.activity_dogs.*
import kotlinx.coroutines.Job

class DogsActivity : AppCompatActivity() {

    private val dogsVM by viewModels<DogsVM> { DogsVMFactory() }
    lateinit var job: Job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dogs)
        doFetchApi()
        subscribeToObservers()
    }

    private fun doFetchApi() {
        dogsVM.getDogsList()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.breed_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_dogs_activity -> {
                showToast("please select other options!")
                return true
            }
            R.id.action_dogs_breeds_image_activity -> {
                val intent = Intent(this, DogsBreedImageActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_auto_reload_activity -> {
                val intent = Intent(this, DogsAutoLoadActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}