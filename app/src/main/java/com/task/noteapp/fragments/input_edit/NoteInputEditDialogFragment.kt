package com.task.noteapp.fragments.input_edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.task.noteapp.databinding.DialogNoteInputEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteInputEditDialogFragment : BottomSheetDialogFragment() {

    private enum class EditingState {
        NEW_DONUT,
        EXISTING_DONUT
    }

    lateinit var binding: DialogNoteInputEditBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogNoteInputEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}