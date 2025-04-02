package com.futureatoms.bevybeats.data.model.browse.artist

import com.google.gson.annotations.SerializedName
import com.futureatoms.bevybeats.data.model.searchResult.songs.Thumbnail
import com.futureatoms.bevybeats.data.type.HomeContentType

data class ResultAlbum(
    @SerializedName("browseId")
    val browseId: String,
    @SerializedName("isExplicit")
    val isExplicit: Boolean,
    @SerializedName("thumbnails")
    val thumbnails: List<Thumbnail>,
    @SerializedName("title")
    val title: String,
    @SerializedName("year")
    val year: String,
) : HomeContentType