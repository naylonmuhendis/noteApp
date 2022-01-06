package com.task.noteapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.task.noteapp.data.models.NoteModel

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo_table ORDER BY editDate ASC")
    fun getAllData(): LiveData<List<NoteModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: NoteModel)

    @Update
    suspend fun updateData(toDoData: NoteModel)

    @Delete
    suspend fun deleteItem(toDoData: NoteModel)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<NoteModel>>
}