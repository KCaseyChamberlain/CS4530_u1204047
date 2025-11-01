package com.example.assignment4

import android.app.Application
import androidx.room.Room
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class FunFactsApp : Application() {
    lateinit var db: AppDb
    lateinit var client: HttpClient
    lateinit var repo: FactsRepository

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(this, AppDb::class.java, "funfacts.db").build()

        client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        repo = FactsRepository(client, db.factDao())
    }
}
