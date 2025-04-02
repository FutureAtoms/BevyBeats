package com.futureatoms.kotlinytmusicscraper.models.body

import com.futureatoms.kotlinytmusicscraper.models.Context
import kotlinx.serialization.Serializable

@Serializable
data class SearchBody(
    val context: Context,
    val query: String?,
    val params: String?,
)