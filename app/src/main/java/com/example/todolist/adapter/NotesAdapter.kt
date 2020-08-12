package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.database.NoteEntity
import com.example.todolist.adapter.NotesAdapter.*
import kotlinx.android.synthetic.main.notes_item.view.*

class NotesAdapter(
    private val model: List<NoteEntity>,
    private val listener: RecyclerViewClickListener
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.notes_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return model.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            itemView.tv_add_note_head.text = model[position].head
            itemView.tv_note_add_body.text = model[position].body

            itemView.setOnClickListener {
                listener.onNotesItemClicked(model[position].id)
            }
        }
    }
}