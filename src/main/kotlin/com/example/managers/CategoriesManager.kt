package com.example.managers

import com.example.database.tables.Category
import com.example.models.CategoryModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class CategoriesManager {
    private val mapper = object {
        fun map(category: Category) = CategoryModel(
            id = category.id.value,
            title = category.title,
            imageUrl = category.image.url
        )
    }

    suspend fun getCategories() = newSuspendedTransaction(Dispatchers.IO) {
        Category.all().map { mapper.map(it) }
    }

}