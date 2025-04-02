package com.futureatoms.kotlinytmusicscraper.models.body

import com.futureatoms.kotlinytmusicscraper.models.Context
import kotlinx.serialization.Serializable

@Serializable
data class GetTranscriptBody(
    val context: Context,
    val params: String,
)