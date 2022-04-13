package com.example.database.tables

import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable


object Images: IntIdTable() {
    val url = text("image_url")
}

class Image(id: EntityID<Int>): IntEntity(id) {
    companion object: EntityClass<Int, Image>(Images)

    var url by Images.url
}

