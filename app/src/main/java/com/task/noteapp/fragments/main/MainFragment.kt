package com.task.noteapp.fragments.main

import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.R
import com.task.noteapp.core.BaseFragment
import com.task.noteapp.databinding.FragmentMainBinding
import com.task.noteapp.fragments.input_edit.NoteEntryEditViewModel
import com.task.noteapp.fragments.input_edit.NoteInputEditDialogFragment
import com.task.noteapp.fragments.input_edit.NoteInputEditDialogFragment.Companion.TAG
import com.task.noteapp.util.SwipeToDelete
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, NoteEntryEditViewModel>() {
    override fun getLayoutRes(): Int = R.layout.fragment_main

    override val viewModel: NoteEntryEditViewModel by activityViewModels()

    private val adapter = NoteListAdapter(
        onEdit = { note ->
            NoteInputEditDialogFragment.newInstance(note)
                .show(requireActivity().supportFragmentManager, TAG)
        },
        onDelete = { note ->
            NotificationManagerCompat.from(requireContext()).cancel(note.id)
            viewModel.deleteItem(note)
        }
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getAllData.observe(viewLifecycleOwner) { notes ->
            adapter.submitList(notes)
        }

        swipeToDelete(dataBinding.recyclerView)
        dataBinding.recyclerView.adapter = adapter

        dataBinding.addBtn.setOnClickListener {
            findNavController().navigate(R.id.noteEntryDialogFragment)
        }
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = viewModel.getAllData.value?.get(viewHolder.adapterPosition)
                itemToDelete?.let {
                    viewModel.deleteItem(itemToDelete)

                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}