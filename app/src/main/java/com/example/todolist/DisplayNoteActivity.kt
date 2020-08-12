package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.database.NoteEntity
import com.example.todolist.misc.KEY_NOTE_ID
import com.example.todolist.misc.LAST_ID
import com.example.todolist.adapter.NotesAdapter
import com.example.todolist.adapter.RecyclerViewClickListener
import com.example.todolist.viewmodel.NoteViewModel
import com.example.todolist.viewmodel.NoteViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class DisplayNoteActivity : AppCompatActivity(), RecyclerViewClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv_notes.visibility = View.GONE
        fab_note_display.visibility = View.GONE

        val viewModel = ViewModelProvider(
            this,
            NoteViewModelFactory(application)
        ).get(NoteViewModel::class.java)


        val intent = Intent(this, AddNoteActivity::class.java)
        viewModel.getAllNotes()

        val sampleNote = NoteEntity(1, getString(R.string.note1), getString(R.string.text_body))
        val sampleNoteList = listOf(sampleNote)

        viewModel.getAllNotesObservableSuccess().observe(this, Observer {
            //if list is EMPTY (on first run) will display blank to-do note
            if (it.isEmpty()) {
                viewModel.addNote(sampleNote)
                rv_notes.adapter =
                    NotesAdapter(sampleNoteList, this)
                rv_notes.layoutManager = LinearLayoutManager(this)
            } else {
                rv_notes.adapter =
                    NotesAdapter(it, this)
                rv_notes.layoutManager = LinearLayoutManager(this)
                intent.putExtra(LAST_ID, it.last().id)
            }
            rv_notes.visibility = View.VISIBLE
            fab_note_display.visibility = View.VISIBLE
            pb_main_progress.visibility = View.GONE
        })

        viewModel.getAllNotesObservableError().observe(this, Observer {
            tv_error.text = it
            rv_notes.visibility = View.GONE
            fab_note_display.visibility = View.GONE
            pb_main_progress.visibility = View.GONE
        })

        fab_note_display.setOnClickListener {
            startActivity(intent)
        }
    }

    override fun onNotesItemClicked(noteID: Int) {
        val intent = Intent(this, EditNoteActivity::class.java)
        intent.putExtra(KEY_NOTE_ID, noteID)
        startActivity(intent)
    }
}
