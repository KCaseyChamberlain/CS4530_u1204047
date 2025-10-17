package com.example.assignment2.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assignment2.data.CourseDatabase
import com.example.assignment2.repository.CourseRepository

class CourseViewModelFactory private constructor(appContext: Context) : ViewModelProvider.Factory {

    private val repo: CourseRepository by lazy {
        val dao = CourseDatabase.getInstance(appContext).courseDao()
        CourseRepository(dao)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CourseViewModel::class.java)) {
            return CourseViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {
        @Volatile private var INSTANCE: CourseViewModelFactory? = null
        fun getInstance(context: Context): CourseViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: CourseViewModelFactory(context.applicationContext).also { INSTANCE = it }
            }
    }
}