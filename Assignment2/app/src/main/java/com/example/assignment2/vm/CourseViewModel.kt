package com.example.assignment2.vm

import androidx.lifecycle.ViewModel
import com.example.assignment2.model.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CourseViewModel : ViewModel() {
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    private val _selectedId = MutableStateFlow<Long?>(null)
    val selectedId: StateFlow<Long?> = _selectedId.asStateFlow()

    private var nextCourseId = 1L

    fun addCourse(department: String, courseNumber: String, location: String) {
        if (department.isBlank() || courseNumber.isBlank()) return
        val newCourse = Course(nextCourseId++, department, courseNumber, location)
        _courses.value = _courses.value + newCourse
        _selectedId.value = newCourse.id
    }

    fun deleteCourse(courseId: Long) {
        _courses.value = _courses.value.filterNot { it.id == courseId }
        if (_selectedId.value == courseId) _selectedId.value = null
    }

    fun selectCourse(courseId: Long) {
        _selectedId.value = courseId
    }

    fun updateCourse(courseId: Long, department: String, courseNumber: String, location: String) {
        _courses.value = _courses.value.map { course ->
            if (course.id == courseId)
                course.copy(department = department, courseNumber = courseNumber, location = location)
            else
                course
        }
        _selectedId.value = courseId  // keep the same item selected after saving
    }
}