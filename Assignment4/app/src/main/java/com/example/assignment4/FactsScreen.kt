package com.example.assignment4

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FactsScreen(app: FunFactsApp) {
    val vm: FactsViewModel = viewModel(factory = FactsVmFactory(app))
    val facts by vm.facts.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { vm.fetch() }) { Text("+") }
        }
    ) { pad ->
        LazyColumn(contentPadding = pad) {
            items(facts) { f ->
                ListItem(
                    headlineContent = { Text(f.text) },
                )
                HorizontalDivider()
            }
        }
    }
}
