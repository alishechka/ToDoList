package com.example.todolist.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "note_table")

data class NoteEntity(
    @PrimaryKey
    var id : Int,
    var head : String,
    var body : String
)