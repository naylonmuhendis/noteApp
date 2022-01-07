package com.task.noteapp.fragments.input_edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.task.noteapp.core.BaseViewModel
import com.task.noteapp.data.models.NoteModel
import com.task.noteapp.data.repository.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteEntryEditViewModel @Inject constructor(val repo: ToDoRepository) : BaseViewModel() {


    val getAllData: LiveData<List<NoteModel>> = repo.allData

    fun insertUpdateNote(noteModel: NoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            if (noteModel.id != 0) {
                repo.updateData(noteModel)

            } else {
                repo.insertData(noteModel)
            }
        }
    }


    fun deleteItem(toDoData: NoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteItem(toDoData)
        }
    }

    init {

    }

}