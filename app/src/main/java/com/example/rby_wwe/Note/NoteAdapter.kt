package com.example.rby_wwe.Note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rby_wwe.data.entity.NoteEntity
import com.example.rby_wwe.databinding.ItemNoteBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteAdapter(
    private val fragment: NotesFragment,
    private var notes: List<NoteEntity> = listOf()
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.binding.apply {
            tvItemTitle.text = note.title
            tvItemContent.text = note.content
            
            val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
            tvItemDate.text = sdf.format(Date(note.createdAt))

            btnDelete.setOnClickListener {
                fragment.deleteNote(note)
            }
        }
    }

    override fun getItemCount(): Int = notes.size

    fun updateData(newNotes: List<NoteEntity>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}