package com.futureatoms.bevybeats.data.model.home

import androidx.compose.runtime.Immutable
import com.futureatoms.bevybeats.data.model.explore.mood.Mood
import com.futureatoms.bevybeats.data.model.home.chart.Chart
import com.futureatoms.bevybeats.utils.Resource

@Immutable
data class HomeDataCombine(
    val home: Resource<ArrayList<HomeItem>>,
    val mood: Resource<Mood>,
    val chart: Resource<Chart>,
    val newRelease: Resource<ArrayList<HomeItem>>,
)