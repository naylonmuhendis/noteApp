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
    var description: String,
    var imgUrl: String?,
    val createdDate: Long = System.currentTimeMillis(),
    var editDate: Long? = 0,
) : Parcelable
