package com.example.managers

import com.example.database.tables.*
import com.example.models.TestModel
import com.example.models.TestModelWithResult
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.select
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

    suspend fun getUserTestsResults(id: Int) = newSuspendedTransaction(Dispatchers.IO) {
        (User.findById(id) ?: throw Exception("User not found")).let { user ->
            UsersTests.select {
                UsersTests.user eq user.id
            }.map {
                TestModelWithResult(
                    id = it[UsersTests.test].value,
                    title = Test[it[UsersTests.test]].title,
                    category = Test[it[UsersTests.test]].category.id.value,
                    current = it[UsersTests.current]
                )
            }
        }
    }
}