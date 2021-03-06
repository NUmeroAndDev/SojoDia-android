package com.numero.sojodia.data.remote

import com.squareup.moshi.JsonAdapter
import se.ansman.kotshi.KotshiJsonAdapterFactory

@KotshiJsonAdapterFactory
abstract class ResourceJsonAdapterFactory : JsonAdapter.Factory {
    companion object {
        val INSTANCE: ResourceJsonAdapterFactory = KotshiResourceJsonAdapterFactory()
    }
}