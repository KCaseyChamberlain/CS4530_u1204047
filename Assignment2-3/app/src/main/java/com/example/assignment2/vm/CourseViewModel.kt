package com.example.assignment2.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment2.model.Course
import com.example.assignment2.repository.CourseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CourseViewModel(private val repo: CourseRepository) : ViewModel() {

    // list of courses backed by Room
    val courses: StateFlow<List<Course>> = repo.courses.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    // VM selection tracking (like before, but no longer drives storage)
    private var _selectedId: Long? = null
    fun selectCourse(id: Long?) { _selectedId = id }
    val selectedId: Long? get() = _selectedId

    fun addCourse(dept: String, num: String, loc: String) {
        if (dept.isBlank() || num.isBlank()) return
        viewModelScope.launch { repo.add(dept, num, loc) }
    }

    fun updateCourse(id: Long, dept: String, num: String, loc: String) {
        viewModelScope.launch { repo.update(id, dept, num, loc) }
    }

    fun deleteCourse(course: Course) {
        viewModelScope.launch { repo.delete(course) }
        if (_selectedId == course.id) _selectedId = null
    }
}