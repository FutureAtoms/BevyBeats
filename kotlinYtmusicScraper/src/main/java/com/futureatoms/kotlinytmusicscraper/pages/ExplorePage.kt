package com.futureatoms.kotlinytmusicscraper.pages

import com.futureatoms.kotlinytmusicscraper.models.PlaylistItem
import com.futureatoms.kotlinytmusicscraper.models.VideoItem

data class ExplorePage(
    val released: List<PlaylistItem>,
    val musicVideo: List<VideoItem>,
)