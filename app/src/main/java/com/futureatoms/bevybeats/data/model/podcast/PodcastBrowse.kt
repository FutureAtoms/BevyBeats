package com.futureatoms.bevybeats.data.model.podcast

import com.futureatoms.bevybeats.data.model.searchResult.songs.Artist
import com.futureatoms.bevybeats.data.model.searchResult.songs.Thumbnail

data class PodcastBrowse(
    val title: String,
    val author: Artist,
    val authorThumbnail: String?,
    val thumbnail: List<Thumbnail>,
    val description: String?,
    val listEpisode: List<EpisodeItem>,
) {
    data class EpisodeItem(
        val title: String,
        val author: Artist,
        val description: String?,
        val thumbnail: List<Thumbnail>,
        val createdDay: String?,
        val durationString: String?,
        val videoId: String,
    )
}