package com.task.noteapp.fragments.input_edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isNotEmpty
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.task.noteapp.R
import com.task.noteapp.components.MyTextInputView
import com.task.noteapp.data.models.NoteModel
import com.task.noteapp.databinding.DialogNoteInputEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteInputEditDialogFragment : BottomSheetDialogFragment() {


    lateinit var binding: DialogNoteInputEditBinding

    var noteModel: NoteModel? = null

    companion object {
        const val TAG = "NoteInputEditDialogFragment"

        const val NOTE_MODEL_KEY = "noteModel"

        fun newInstance(
            note: NoteModel
        ) = NoteInputEditDialogFragment().apply {
            this.noteModel = note
        }
    }

    private val viewModel: NoteEntryEditViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogNoteInputEditBinding.inflate(inflater, container, false)
        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        noteModel?.let {
            binding.titleEdt.text = it.title
            binding.descriptionEdt.text = it.description
            binding.imgUrlEdit.text = it.imgUrl ?: ""
        }
        binding.saveBtn.setOnClickListener {
            var title = binding.titleEdt.text
            var description = binding.descriptionEdt.text
            if (validateForm()) {
                viewModel.insertUpdateNote(
                    NoteModel(
                        noteModel?.id ?: 0,
                        title,
                        description,
                        binding.imgUrlEdit.text
                    )
                )
                dismiss()
            }
        }
        binding.closeBtn.setOnClickListener {
            dismiss()
        }
    }

    private fun validateForm(): Boolean {
        val isTitleOK = validateIsnull(binding.titleEdt)
        val isDescriptionOK = validateIsnull(binding.descriptionEdt)
        return isTitleOK && isDescriptionOK
    }

    private fun validateIsnull(inputView: MyTextInputView): Boolean {

        return if (inputView.text.isNotEmpty()) {
            if (inputView.errorEnabled) {
                inputView.clearError()
            }
            true
        } else {
            inputView.error = getString(R.string.input_warning)
            false
        }

    }

}