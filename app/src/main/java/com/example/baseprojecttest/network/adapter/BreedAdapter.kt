package com.example.baseprojecttest.network.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baseprojecttest.R
import com.example.baseprojecttest.presentation.dogs.model.Dogs
import com.example.baseprojecttest.presentation.dogs.model.DogsBreedImage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_breed_imaged.view.*

class BreedAdapter: RecyclerView.Adapter<BreedAdapter.VH>() {
    var breed:List<Dogs>?=null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    inner class VH(itemView : View) : RecyclerView.ViewHolder(itemView){

        init {
            with(itemView){
//                breed?.get(adapterPosition)?.let { callable.invoke(it) }


            }
        }

        fun bind(data: Dogs?) {
            //itemView.tag = data
            with(itemView){
                    tvBreedName.text = data?.breed
//                GlideApp.with(context).asBitmap().load(data?.imageUrl).apply( RequestOptions().diskCacheStrategy(
//                    DiskCacheStrategy.NONE)).into(ivBreed)
                    Picasso.get().load(data?.imageUrl).into(ivBreed)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.rv_breed_imaged, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(breed?.get(position))

    }

    override fun getItemCount() = breed?.size ?: 0
}