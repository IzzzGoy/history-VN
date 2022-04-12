package com.example.database.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object ObjectsImages: Table() {
    val oobject = reference("object", Objects, onUpdate = ReferenceOption.CASCADE, onDelete = ReferenceOption.CASCADE)
    val image = reference("image", Images, onUpdate = ReferenceOption.CASCADE, onDelete = ReferenceOption.CASCADE)
    override val primaryKey: PrimaryKey = PrimaryKey(oobject, image, name = "object_image_pk")
}