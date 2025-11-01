package com.example.assignment4

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FactsViewModel(private val repo: FactsRepository) : ViewModel() {
    val facts = repo.facts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    fun fetch() = viewModelScope.launch { repo.fetchAndStore() }
}

class FactsVmFactory(private val app: FunFactsApp) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FactsViewModel(app.repo) as T
    }
}
