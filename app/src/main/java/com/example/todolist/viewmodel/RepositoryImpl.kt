package com.example.todolist.viewmodel

import android.content.Context
import com.example.todolist.database.NoteDatabase
import com.example.todolist.database.NoteEntity
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepositoryImpl(context: Context) : Repository {
    private val noteDao = NoteDatabase.getInstance(context).noteDao()

    override fun getNote(noteId: Int): Single<NoteEntity> {
        return noteDao.getNote(noteId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getNotes(): Single<List<NoteEntity>> {
        return noteDao.getAllNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun addNoteEntity(entity: NoteEntity): Completable {
        return noteDao.addNote(entity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteEntity(noteID: Int): Completable {
        return noteDao.deleteById(noteID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}