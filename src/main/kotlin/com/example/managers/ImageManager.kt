package com.example.managers

import com.example.database.tables.Image
import com.example.models.ImageModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ImageManager {

    private val mapper = object {
        fun map(image: Image) = ImageModel(image.url)
    }

    suspend fun getImages() = newSuspendedTransaction(Dispatchers.IO) {
        Image.all().map(mapper::map)
    }

}