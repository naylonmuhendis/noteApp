package com.task.noteapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "todo_table")
@Parcelize
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    val createdDate: Long,
    var editDate: Long?,
    var description: String,
) : Parcelable
