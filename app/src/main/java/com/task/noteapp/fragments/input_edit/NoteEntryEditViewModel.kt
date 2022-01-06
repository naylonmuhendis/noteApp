package com.task.noteapp.fragments.input_edit

import com.task.noteapp.core.BaseViewModel
import com.task.noteapp.data.repository.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteEntryEditViewModel @Inject constructor(val repo: ToDoRepository) : BaseViewModel() {


    init {

    }

}