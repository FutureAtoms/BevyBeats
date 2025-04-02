package com.futureatoms.kotlinytmusicscraper.pages

import com.futureatoms.kotlinytmusicscraper.models.SongItem

data class PlaylistContinuationPage(
    val songs: List<SongItem>,
    val continuation: String?,
)