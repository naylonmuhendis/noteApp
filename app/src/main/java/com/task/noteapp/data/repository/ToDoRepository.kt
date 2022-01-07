package com.task.noteapp.data.repository

import androidx.lifecycle.LiveData
import com.task.noteapp.data.models.NoteModel
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    suspend fun insertData(noteModel: NoteModel)

    suspend fun updateData(noteModel: NoteModel)

    suspend fun deleteItem(noteModel: NoteModel)

    suspend fun deleteAll()

    val allData: LiveData<List<NoteModel>>

}