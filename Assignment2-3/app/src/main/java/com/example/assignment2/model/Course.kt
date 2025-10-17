package com.example.assignment2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course")
data class Course(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,         // Long: stable unique id (good for large ranges)
    val department: String,    // String: "CS", "MATH"
    val courseNumber: String,  // String: allows letters/dots if needed
    val location: String       // String: free form room/building codes, "MEB 3147"
) {
    val name: String get() = "${department.trim()} ${courseNumber.trim()}"
}