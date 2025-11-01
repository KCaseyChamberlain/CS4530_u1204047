package com.example.assignment4

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow

class FactsRepository(
    private val client: HttpClient,
    private val dao: FactDao
) {
    val facts: Flow<List<FactEntity>> = dao.observeFacts()

    suspend fun fetchAndStore() {
        val api = client
            .get("https://uselessfacts.jsph.pl/random.json?language=en")
            .body<ApiFact>()
        dao.insert(api.toEntity())
    }
}
