package com.example.managers

import com.example.database.tables.Category
import com.example.database.tables.Test
import com.example.database.tables.Tests
import com.example.models.TestModel
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class TestsManager {

    private val mapper = object {
        fun map(test: Test) = TestModel(
            id = test.id.value,
            title = test.title,
            category = test.category.id.value
        )
    }

    suspend fun getTestsByCategory(id: Int) = newSuspendedTransaction {
        Category.findById(id)?.let {
            Test.find { Tests.category eq it.id }.map(mapper::map)
        } ?: throw Exception("Category with $id not found")
    }
}