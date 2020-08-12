package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.R
import com.example.todolist.database.NoteEntity
import com.example.todolist.misc.BODY
import com.example.todolist.misc.HEAD
import com.example.todolist.misc.KEY_NOTE_ID
import com.example.todolist.viewmodel.NoteViewModel
import com.example.todolist.viewmodel.NoteViewModelFactory
import kotlinx.android.synthetic.main.activity_edit_note.*

class EditNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        val viewModel = ViewModelProvider(
            this,
            NoteViewModelFactory(application)
        ).get(NoteViewModel::class.java)

        val noteId = intent.getIntExtra(KEY_NOTE_ID, 0)

        viewModel.getNote(noteId)

        viewModel.getNoteObservable().observe(this, Observer {
            pb_edit_note.visibility = GONE
            //To manage orientation change during Editing process
            if (savedInstanceState != null) {
                val body = savedInstanceState.getString(BODY)
                val head = savedInstanceState.getString(HEAD)
                et_edit_body.setText(body)
                et_edit_head.setText(head)
            } else {
                et_edit_body.setText(it.body)
                et_edit_head.setText(it.head)
            }
            lil_edit_note_container.visibility = VISIBLE
        })


        btn_update.setOnClickListener {
            if (et_edit_head.text.toString().isNotBlank()
                && et_edit_body.text.toString().isNotBlank()
            ) {
                viewModel.addNote(
                    NoteEntity(noteId, et_edit_head.text.toString(), et_edit_body.text.toString())
                )
                Toast.makeText(this, getString(R.string.note_changed), Toast.LENGTH_LONG).show()
                val intent = Intent(this, DisplayNoteActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.note_add_error), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        btn_delete.setOnClickListener {
            viewModel.deleteNote(noteId)
            Toast.makeText(this, getString(R.string.note_deleted), Toast.LENGTH_LONG).show()
            val intent = Intent(this, DisplayNoteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val body = et_edit_body.text.toString()
        val head = et_edit_head.text.toString()
        outState.putString(BODY, body)
        outState.putString(HEAD, head)
        super.onSaveInstanceState(outState)
    }
}
