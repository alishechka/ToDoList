package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.R
import com.example.todolist.database.NoteEntity
import com.example.todolist.misc.LAST_ID
import com.example.todolist.viewmodel.NoteViewModel
import com.example.todolist.viewmodel.NoteViewModelFactory
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        val viewModel = ViewModelProvider(
            this, NoteViewModelFactory(application)
        ).get(NoteViewModel::class.java)

        val lastId = intent.getIntExtra(LAST_ID, 0)

        btn_note_add_save.setOnClickListener {
            if (tv_add_note_head.text.toString().isNotBlank()
                && tv_note_add_body.text.toString().isNotBlank()
            ) {
                val addedNoteEntity =
                    NoteEntity(
                        lastId + 1,
                        tv_add_note_head.text.toString(),
                        tv_note_add_body.text.toString()
                    )
                viewModel.addNote(addedNoteEntity)
                pb_note_add.visibility = View.VISIBLE
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.note_add_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.getAddNoteObservable().observe(this, androidx.lifecycle.Observer {
            if (it) {
                pb_note_add.visibility = View.GONE
                Toast.makeText(this, getString(R.string.note_added), Toast.LENGTH_SHORT)
                    .show()
                intent = Intent(this, DisplayNoteActivity::class.java)
                startActivity(intent)
            } else {
                pb_note_add.visibility = View.GONE
                Toast.makeText(this, getString(R.string.note_fail), Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}
