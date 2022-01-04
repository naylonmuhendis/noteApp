package com.task.noteapp.di

import android.content.Context
import androidx.room.Room
import com.task.noteapp.data.ToDoDao
import com.task.noteapp.data.ToDoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext app: Context): ToDoDatabase {
        return Room
            .databaseBuilder(app, ToDoDatabase::class.java, "toDo.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideToDoDao(db: ToDoDatabase): ToDoDao {
        return db.toDoDao()
    }
}