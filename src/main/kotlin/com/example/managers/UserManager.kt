package com.example.managers

import com.example.database.tables.*
import com.example.models.UserInfoModel
import com.example.models.UserModel
import com.example.models.UserRegisterModel
import com.example.models.UserTestsModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserManager {

    private val mapper = object {
        fun map(user: User) = UserModel(
            id = user.id.value,
            lastName = user.lastName,
            firstName = user.firstName,
            midlleName = user.midlleName,
            login = user.login,
            password = user.password,
            role = user.role,
            picture = user.pictureId.url,
            rating = user.rating
        )

        fun infoMap(user: User) = UserInfoModel(
            id = user.id.value,
            lastName = user.lastName,
            firstName = user.firstName,
            midlleName = user.midlleName,
            picture = user.pictureId.url,
            rating = user.rating
        )
    }

    suspend fun getUsers() = newSuspendedTransaction(Dispatchers.IO) {
        User.all().map(mapper::map)
    }

    suspend fun getUserInfo(id: Int) = newSuspendedTransaction(Dispatchers.IO) {
        User.findById(id)?.let(mapper::infoMap) ?: throw Exception("User not found")
    }

    suspend fun login(login: String, password: String) = newSuspendedTransaction {
        User.find {
            (Users.login eq login) and (Users.password eq password)
        }.firstOrNull()?.let(mapper::infoMap) ?: throw Exception("User not found")
    }

    suspend fun register(userRegisterModel: UserRegisterModel) = newSuspendedTransaction {
        User.new {
            lastName = userRegisterModel.lastName
            firstName = userRegisterModel.firstName
            midlleName = userRegisterModel.midlleName
            login = userRegisterModel.login
            password = userRegisterModel.password
            pictureId = userRegisterModel.picture?.let { Image.findById(it) ?: Image.findById(1) } ?: Image[1]
            role = Role.USER
            rating = 0.0
            tests = Test.all()
        }.let { mapper.infoMap(it) }
    }

    suspend fun userTestsInfo(id: Int) = newSuspendedTransaction(Dispatchers.IO) {
        (User.findById(id) ?: throw Exception("User not found")).let { user ->
            user.tests.mapNotNull {
                val res =
                    UsersTests.select { (UsersTests.test eq it.id) and (UsersTests.user eq user.id) }.firstOrNull()
                res?.let { rr ->
                    if (rr[UsersTests.current] != 0.0) {
                        UserTestsModel(
                            current = rr[UsersTests.current],
                            testTitle = it.title
                        )
                    } else {
                        null
                    }
                }
            }
        }
    }



}