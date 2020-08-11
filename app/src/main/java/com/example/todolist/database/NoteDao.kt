package com.example.todolist.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table WHERE id= :noteID")
    fun getNote(noteID: Int): Single<NoteEntity>

    @Query("SELECT * FROM note_table")
    fun getAllNotes(): Single<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note: NoteEntity): Completable

    @Query("DELETE FROM note_table WHERE id = :noteID")
    fun deleteById(noteID: Int): Completable

}