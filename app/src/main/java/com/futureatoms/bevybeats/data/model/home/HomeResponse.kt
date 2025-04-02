package com.futureatoms.bevybeats.data.model.home

import androidx.compose.runtime.Immutable
import com.futureatoms.bevybeats.data.model.explore.mood.Mood
import com.futureatoms.bevybeats.data.model.home.chart.Chart
import com.futureatoms.bevybeats.utils.Resource

@Immutable
data class HomeResponse(
    val homeItem: Resource<ArrayList<HomeItem>>,
    val exploreMood: Resource<Mood>,
    val exploreChart: Resource<Chart>,
)