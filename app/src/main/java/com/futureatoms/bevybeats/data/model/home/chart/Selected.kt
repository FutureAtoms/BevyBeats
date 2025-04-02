package com.futureatoms.bevybeats.data.model.home.chart

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName

@Immutable
data class Selected(
    @SerializedName("text")
    val text: String,
)