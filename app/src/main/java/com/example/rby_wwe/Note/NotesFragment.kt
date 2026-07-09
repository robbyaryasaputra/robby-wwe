package com.example.rby_wwe.Note

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rby_wwe.data.AppDatabase
import com.example.rby_wwe.data.entity.NoteEntity
import com.example.rby_wwe.databinding.FragmentNotesBinding
import kotlinx.coroutines.launch

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var db: AppDatabase
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getInstance(requireContext())
        setupRecyclerView()

        binding.fabAddNote.setOnClickListener {
            val intent = Intent(requireContext(), NoteFormActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter(this)
        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteAdapter
            // Step 5: Tambahkan DividerItemDecoration
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
    }

    // Step 5: fetchNotes di onResume agar data otomatis refresh
    override fun onResume() {
        super.onResume()
        fetchNotes()
    }

    private fun fetchNotes() {
        lifecycleScope.launch {
            val notes = db.noteDao().getAll()
            noteAdapter.updateData(notes)
        }
    }

    // Step 6: Tambah fungsi deleteNote
    fun deleteNote(note: NoteEntity) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Note")
            .setMessage("Are you sure you want to delete this note?")
            .setPositiveButton("Delete") { _, _ ->
                lifecycleScope.launch {
                    db.noteDao().delete(note)
                    fetchNotes() // Refresh data setelah hapus
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}