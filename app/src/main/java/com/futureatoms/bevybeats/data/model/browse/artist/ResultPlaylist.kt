package com.futureatoms.bevybeats.data.model.browse.artist

import com.futureatoms.bevybeats.data.model.searchResult.songs.Thumbnail
import com.futureatoms.bevybeats.data.type.HomeContentType

data class ResultPlaylist(
    val id: String,
    val author: String,
    val thumbnails: List<Thumbnail>,
    val title: String,
) : HomeContentType