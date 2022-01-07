package com.task.noteapp.fragments.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.data.models.NoteModel
import com.task.noteapp.databinding.ItemNoteBinding
import com.task.noteapp.util.SwipeToDelete

class NoteListAdapter(
    private var onEdit: (NoteModel) -> Unit,
    private var onDelete: (NoteModel) -> Unit
) : ListAdapter<NoteModel, NoteListAdapter.NoteListViewHolder>(NoteDiffCallback()) {

    class NoteListViewHolder(
        private val binding: ItemNoteBinding,
        private var onEdit: (NoteModel) -> Unit,
        private var onDelete: (NoteModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(noteModel: NoteModel) {

            binding.noteModel = noteModel
//            binding.deleteButton.setOnClickListener {
//                onDelete(coffee)
//            }
            binding.root.setOnClickListener {
                onEdit(noteModel)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =  NoteListViewHolder(
        ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onEdit,
        onDelete
    )

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class NoteDiffCallback : DiffUtil.ItemCallback<NoteModel>() {
    override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
        return oldItem == newItem
    }
}