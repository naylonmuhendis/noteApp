package com.task.noteapp.data.repository

import androidx.lifecycle.LiveData
import com.task.noteapp.data.ToDoDao
import com.task.noteapp.data.models.NoteModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepositoryImp @Inject constructor(val toDoDao: ToDoDao) : ToDoRepository {

    override val allData = toDoDao.getAllData()

    override suspend fun insertData(noteModel: NoteModel) = toDoDao.insertData(noteModel)

    override suspend fun updateData(noteModel: NoteModel) = toDoDao.updateData(noteModel)

    override suspend fun deleteItem(noteModel: NoteModel) = toDoDao.deleteItem(noteModel)

    override suspend fun deleteAll() = toDoDao.deleteAll()


}