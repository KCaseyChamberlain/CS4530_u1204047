package com.example.assignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignment2.vm.CourseViewModel
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.widthIn

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { MinimalCourseApp() } }
    }
}

@Composable
private fun MinimalCourseApp(vm: CourseViewModel = viewModel()) {
    // Observable state from the ViewModel
    val courses by vm.courses.collectAsStateWithLifecycle()
    val selectedCourseId by vm.selectedId.collectAsStateWithLifecycle()

    // local UI state for the form
    var department by remember { mutableStateOf("") }
    var courseNumber by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var editingCourseId by remember { mutableStateOf<Long?>(null) }

    // Detail view source: priority; the course being edited - otherwise the one selected by tap
    val currentCourseId = editingCourseId ?: selectedCourseId
    val currentCourse = courses.firstOrNull { it.id == currentCourseId }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // course form
        Column(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .widthIn(max = 420.dp)
        ) {
            OutlinedTextField(
                value = department,
                onValueChange = { department = it },
                label = { Text("Dept") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = courseNumber,
                onValueChange = { courseNumber = it },
                label = { Text("Course Number") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        if (editingCourseId != null) {
                            vm.updateCourse(editingCourseId!!, department, courseNumber, location)
                        } else {
                            vm.addCourse(department, courseNumber, location)
                        }
                        // clear the form and exit edit mode
                        department = ""; courseNumber = ""; location = ""
                        editingCourseId = null
                    }
                ) {
                    Text(if (editingCourseId != null) "Save Changes" else "Save")
                }
                if (editingCourseId != null) {
                    OutlinedButton(
                        onClick = {
                            // switch back to "add new" mode
                            department = ""; courseNumber = ""; location = ""
                            editingCourseId = null
                        }
                    ) { Text("Add New Course") }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // scrollable list (only this section scrolls)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(courses, key = { it.id }) { course ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { vm.selectCourse(course.id) } // tap selects for details
                ) {
                    Box(Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("Department: ${course.department}")
                            Text("Course Number: ${course.courseNumber}")
                            Text("Location: ${course.location}")
                        }
                        Row(modifier = Modifier.align(Alignment.TopEnd)) {
                            TextButton(onClick = {
                                // enter edit mode + prefill the form, also reflect in detail view
                                editingCourseId = course.id
                                department = course.department
                                courseNumber = course.courseNumber
                                location = course.location
                                vm.selectCourse(course.id)
                            }) { Text("Edit") }
                            TextButton(onClick = {
                                // temp save the course, so if the course being edited is deleted, the form is cleared
                                val deletingEditingCourse = (editingCourseId == course.id)
                                vm.deleteCourse(course.id)
                                if (deletingEditingCourse) {
                                    editingCourseId = null
                                    department = ""; courseNumber = ""; location = ""
                                }
                            }) { Text("Delete") }
                        }
                    }
                }
            }
        }

        // course detail view
        currentCourse?.let { c ->
            Spacer(Modifier.height(12.dp))
            Text("Department: ${c.department}")
            Text("Course Number: ${c.courseNumber}")
            Text("Location: ${c.location}")
        }
    }
}