package com.task.noteapp.data.repository

import androidx.lifecycle.LiveData
import com.task.noteapp.data.models.NoteModel

interface ToDoRepository {

    suspend fun insertData(noteModel: NoteModel)

    suspend fun updateData(noteModel: NoteModel)

    suspend fun deleteItem(noteModel: NoteModel)

    suspend fun deleteAll()

}