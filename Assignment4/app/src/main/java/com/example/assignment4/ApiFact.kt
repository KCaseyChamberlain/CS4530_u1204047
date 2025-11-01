package com.example.assignment4

import kotlinx.serialization.Serializable

@Serializable
data class ApiFact(
    val id: String? = null,
    val text: String,
    val source: String? = null,
    val language: String? = null
)

fun ApiFact.toEntity(): FactEntity = FactEntity(
    text = text,
    source = source
)
