package com.example.baseprojecttest.presentation.dogs.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.baseprojecttest.R
import com.example.baseprojecttest.data.model.ResultOf
import com.example.baseprojecttest.presentation.dogs.model.DogsBreedImage
import com.example.baseprojecttest.presentation.dogs.viewmodel.DogsVM
import com.example.baseprojecttest.presentation.dogs.viewmodel.DogsVMFactory
import com.example.baseprojecttest.util.showToast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_dogs.*

class DogsActivity : AppCompatActivity() {

    private val dogsVM by viewModels<DogsVM> { DogsVMFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dogs)
        subscribeToObservers()
        doFetchApi()
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



                    for (i in 0..it.value.size){
                        if (tempArr.size < 5 || tempArr.isEmpty()){
                            tempArr.add(it.value.random())

                        }else{
                            break
                        }
                    }


                    tempArr.sortBy { it->
                        it
                    }
                    tempArr.forEach { breedName->
                        fetchImages(breedName)
                    }
                    tv1.text = tempArr[0]


                    tv2.text = tempArr[1]


                    tv3.text = tempArr[2]


                    tv4.text = tempArr[3]

                    tv5.text = tempArr[4]



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
                    if (tempImgArr.isNotEmpty() && tempImgArr.size == 5) {

                        tempImgArr.sortBy { d->
                            d.message
                        }
                        Picasso.get().load(tempImgArr[0].message).into(iv1)
                        Picasso.get().load(tempImgArr[1].message).into(iv2)
                        Picasso.get().load(tempImgArr[2].message).into(iv3)
                        Picasso.get().load(tempImgArr[3].message).into(iv4)
                        Picasso.get().load(tempImgArr[4].message).into(iv5)




                    }

                }
                is ResultOf.Empty -> showToast("No breed available!")
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