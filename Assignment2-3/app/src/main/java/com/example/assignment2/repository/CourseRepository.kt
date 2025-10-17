package com.example.assignment2.repository

import com.example.assignment2.data.CourseDao
import com.example.assignment2.model.Course
import kotlinx.coroutines.flow.Flow

class CourseRepository(private val dao: CourseDao) {

    val courses: Flow<List<Course>> = dao.getAll()

    suspend fun add(dept: String, num: String, loc: String) {
        dao.insert(Course(department = dept, courseNumber = num, location = loc))
    }

    suspend fun update(id: Long, dept: String, num: String, loc: String) {
        dao.update(Course(id = id, department = dept, courseNumber = num, location = loc))
    }

    suspend fun delete(course: Course) {
        dao.delete(course)
    }
}