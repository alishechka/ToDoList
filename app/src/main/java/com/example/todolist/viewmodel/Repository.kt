package com.example.todolist.viewmodel

import com.example.todolist.database.NoteEntity
import io.reactivex.Completable
import io.reactivex.Single

interface Repository {
    fun getNote(noteId: Int): Single<NoteEntity>

    fun getNotes(): Single<List<NoteEntity>>

    fun addNoteEntity(entity: NoteEntity): Completable

    fun deleteEntity(noteID: Int): Completable
}
