package com.futureatoms.bevybeats.data.model.explore.mood.moodmoments

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName
import com.futureatoms.bevybeats.data.model.searchResult.songs.Thumbnail
import com.futureatoms.bevybeats.data.type.HomeContentType

@Immutable
data class Content(
    @SerializedName("playlistBrowseId")
    val playlistBrowseId: String,
    @SerializedName("subtitle")
    val subtitle: String,
    @SerializedName("thumbnails")
    val thumbnails: List<Thumbnail>?,
    @SerializedName("title")
    val title: String,
) : HomeContentType