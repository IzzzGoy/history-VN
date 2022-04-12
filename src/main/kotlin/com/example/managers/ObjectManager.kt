package com.example.managers

import com.example.database.tables.Category
import com.example.database.tables.Object
import com.example.database.tables.Objects
import com.example.models.ImageModel
import com.example.models.ObjectModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ObjectManager {
    private val mapper = object {
        fun map(objects: Object) = ObjectModel(
            id = objects.id.value,
            title = objects.title,
            category = objects.category.id.value,
            location = objects.location,
            images = objects.image.map { ImageModel(it.url) }
        )
    }

    suspend fun getByCategory(id: Int) = newSuspendedTransaction(Dispatchers.IO) {
        Category.findById(id)?.let {  category ->
            Object.find { Objects.category eq category.id }.map(mapper::map)
        } ?: throw Exception("Unknown id")
    }
}