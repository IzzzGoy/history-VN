package com.example.database

import com.example.database.tables.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.postgresql.Driver
import kotlin.reflect.jvm.jvmName

suspend fun initDatabase() = withContext(Dispatchers.IO) {
    Database.connect(
        url = "jdbc:postgresql://db.sdasujlqlftjjgehyqiv.supabase.co:5432/postgres",
        user = "postgres",
        password = "sev8624donlog159753mod",
        driver = Driver::class.jvmName
    )

    newSuspendedTransaction(Dispatchers.IO) {
        SchemaUtils.createMissingTablesAndColumns(
            Images,
            Users,
            Categories,
            Tests,
            UsersTests,
            AnswerPossibilities,
            Questions,
            Objects,
            ObjectsImages,
            Informations
        )
    }
}