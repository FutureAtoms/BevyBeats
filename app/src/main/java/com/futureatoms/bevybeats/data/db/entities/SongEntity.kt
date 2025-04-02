package com.futureatoms.bevybeats.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.futureatoms.bevybeats.common.DownloadState.STATE_NOT_DOWNLOADED
import com.futureatoms.bevybeats.data.type.RecentlyType
import java.time.LocalDateTime

@Entity(tableName = "song")
data class SongEntity(
    @PrimaryKey(autoGenerate = false) val videoId: String = "",
    val albumId: String? = null,
    val albumName: String? = null,
    val artistId: List<String>? = null,
    val artistName: List<String>? = null,
    val duration: String,
    val durationSeconds: Int,
    val isAvailable: Boolean,
    val isExplicit: Boolean,
    val likeStatus: String,
    val thumbnails: String? = null,
    val title: String,
    val videoType: String,
    val category: String?,
    val resultType: String?,
    val liked: Boolean = false,
    val totalPlayTime: Long = 0, // in milliseconds
    val downloadState: Int = STATE_NOT_DOWNLOADED,
    val inLibrary: LocalDateTime = LocalDateTime.now(),
    val canvasUrl: String? = null,
) : RecentlyType {
    override fun objectType(): RecentlyType.Type = RecentlyType.Type.SONG

    fun toggleLike() = copy(liked = !liked)
}