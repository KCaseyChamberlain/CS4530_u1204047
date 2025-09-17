package com.example.assignment2.model

data class Course(
    val id: Long,              // Long: stable unique id (good for large ranges)
    val department: String,    // String: "CS", "MATH"
    val courseNumber: String,  // String: allows letters/dots if needed
    val location: String       // String: free form room/building codes, "MEB 3147"
) {
    val name: String get() = "${department.trim()} ${courseNumber.trim()}"
}
