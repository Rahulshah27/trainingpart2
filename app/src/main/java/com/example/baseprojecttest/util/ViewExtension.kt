package com.example.baseprojecttest.util

import android.content.Context
import android.widget.Toast
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import java.io.InputStream

fun Context.showToast(msg:String){
    Toast.makeText(this, msg,Toast.LENGTH_SHORT).show()
}

fun CoroutineScope.launchPeriodically(repeatInMillis:Long,action: ()->Unit)
        = this.launch {
    withContext(Dispatchers.Main){
        if (repeatInMillis > 0){
            while (isActive){
                action()
                delay(repeatInMillis)
            }
        }else{
            action()
        }
    }

}

@GlideModule
@Excludes(OkHttpLibraryGlideModule::class)
class GlideModule : AppGlideModule() {
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
    }

    fun registerComponents(context: Context?, registry: Registry) {
        val okHttpClient =  OkHttpClient()
        registry.append(GlideUrl::class.java, InputStream::class.java,OkHttpUrlLoader.Factory(okHttpClient))
    }

}