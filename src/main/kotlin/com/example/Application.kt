package com.example

import com.example.database.initDatabase
import com.example.managers.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

suspend fun main() {

    initDatabase()

    val imageManager = ImageManager()
    val userManager = UserManager()
    val categoriesManager = CategoriesManager()
    val objectManager = ObjectManager()
    val testsManager = TestsManager()
    val questionsManager = QuestionsManager()

    embeddedServer(Netty, port = System.getenv("PORT").toInt() ?: 8080, host = "0.0.0.0") {

        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                }
            )
        }
        install(StatusPages) {
            exception<Exception> { call, exception ->
                call.respond(HttpStatusCode.BadRequest, exception.message.orEmpty())
            }
        }


        routing {
            route("api/v1") {
                get("images") {
                    call.respond(imageManager.getImages())
                }
                route("users") {
                    get {
                        call.respond(userManager.getUsers())
                    }
                    get("{id}"){
                        call.respond(
                            userManager.getUserInfo(call.parameters["id"]?.toInt() ?: throw Exception("Missing id"))
                        )
                    }
                    get("login") {
                        val login = call.request.queryParameters["login"] ?: throw Exception("Missing login")
                        val password = call.request.queryParameters["password"] ?: throw Exception("Missing password")
                        call.respond(userManager.login(login, password))
                    }

                    get("tests/{id}") {
                        call.respond(
                            userManager.userTestsInfo(
                                call.parameters["id"]?.toInt() ?: throw Exception("Missing id")
                            )
                        )
                    }
                }

                route("categories") {
                    get {
                        call.respond(categoriesManager.getCategories())
                    }
                }

                route("objects") {
                    get("{id}") {
                        call.respond(
                            objectManager.getByCategory(call.parameters["id"]?.toInt() ?: throw Exception("Missing id"))
                        )
                    }
                }
                route("tests") {
                    get("{id}") {
                        call.respond(
                            testsManager.getTestsByCategory(call.parameters["id"]?.toInt() ?: throw Exception("Missing id"))
                        )
                    }
                }
                route("questions") {
                    get("{id}") {
                        call.respond(
                            questionsManager.getByTest(call.parameters["id"]?.toInt() ?: throw Exception("Missing id"))
                        )
                    }
                }
            }
        }


    }.start(wait = true)
}
