package com.example.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist.database.NoteEntity
import io.reactivex.disposables.CompositeDisposable

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    private val noteObservable = MutableLiveData<NoteEntity>()
    private val allNotesObservableSuccess = MutableLiveData<List<NoteEntity>>()
    private val allNotesObservableError = MutableLiveData<String>()
    private val addNoteObservable = MutableLiveData<Boolean>()
    private val deleteNoteObservable = MutableLiveData<Boolean>()

    private val repository = RepositoryImpl(application)

    fun getNote(id: Int) {
        compositeDisposable.add(
            repository.getNote(id).subscribe
            { note -> noteObservable.value = note }
        )
    }

    fun getAllNotes() {
        compositeDisposable.add(
            repository.getNotes().subscribe(
                { entity -> allNotesObservableSuccess.value = entity },
                { error -> allNotesObservableError.value = error.message })
        )
    }

    fun addNote(obj: NoteEntity) {
        compositeDisposable.add(
            repository.addNoteEntity(obj).subscribe(
                { addNoteObservable.value = true },
                { addNoteObservable.value = false }
            )
        )
    }
    fun deleteNote(id: Int){
        compositeDisposable.add(
            repository.deleteEntity(id).subscribe(
                { deleteNoteObservable.value = true },
                { deleteNoteObservable.value = false }
            )
        )
    }

    fun getNoteObservable(): LiveData<NoteEntity> = noteObservable
    fun getAllNotesObservableSuccess(): LiveData<List<NoteEntity>> = allNotesObservableSuccess
    fun getAllNotesObservableError(): LiveData<String> = allNotesObservableError
    fun getAddNoteObservable(): LiveData<Boolean> = addNoteObservable
    fun getDeleteNoteObservable(): LiveData<Boolean> = deleteNoteObservable

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}