package com.futureatoms.bevybeats.data.model.browse.album

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName
import com.futureatoms.bevybeats.data.model.searchResult.songs.Album
import com.futureatoms.bevybeats.data.model.searchResult.songs.Artist
import com.futureatoms.bevybeats.data.model.searchResult.songs.FeedbackTokens
import com.futureatoms.bevybeats.data.model.searchResult.songs.Thumbnail

@Immutable
data class Track(
    @SerializedName("album")
    val album: Album?,
    @SerializedName("artists")
    val artists: List<Artist>?,
    @SerializedName("duration")
    val duration: String?,
    @SerializedName("duration_seconds")
    val durationSeconds: Int?,
    @SerializedName("isAvailable")
    val isAvailable: Boolean,
    @SerializedName("isExplicit")
    val isExplicit: Boolean,
    @SerializedName("likeStatus")
    val likeStatus: String?,
    @SerializedName("thumbnails")
    val thumbnails: List<Thumbnail>?,
    @SerializedName("title")
    val title: String,
    @SerializedName("videoId")
    val videoId: String,
    @SerializedName("videoType")
    val videoType: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("feedbackTokens")
    val feedbackTokens: FeedbackTokens?,
    @SerializedName("resultType")
    val resultType: String?,
    @SerializedName("year")
    val year: Any?,
)