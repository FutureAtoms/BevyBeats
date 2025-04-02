package com.futureatoms.kotlinytmusicscraper.pages

import com.futureatoms.kotlinytmusicscraper.models.Album
import com.futureatoms.kotlinytmusicscraper.models.AlbumItem
import com.futureatoms.kotlinytmusicscraper.models.Artist
import com.futureatoms.kotlinytmusicscraper.models.MusicResponsiveListItemRenderer
import com.futureatoms.kotlinytmusicscraper.models.MusicTwoRowItemRenderer
import com.futureatoms.kotlinytmusicscraper.models.SongItem
import com.futureatoms.kotlinytmusicscraper.models.Thumbnail
import com.futureatoms.kotlinytmusicscraper.models.Thumbnails
import com.futureatoms.kotlinytmusicscraper.models.oddElements
import com.futureatoms.kotlinytmusicscraper.utils.parseTime

data class AlbumPage(
    val album: AlbumItem,
    val songs: List<SongItem>,
    val description: String?,
    val thumbnails: Thumbnails?,
    val duration: String?,
    val otherVersion: List<AlbumItem>,
) {
    companion object {
        fun fromMusicTwoRowItemRenderer(renderer: MusicTwoRowItemRenderer?): AlbumItem? {
            if (renderer == null) {
                return null
            }
            return AlbumItem(
                browseId = renderer.navigationEndpoint.browseEndpoint?.browseId ?: return null,
                playlistId =
                    renderer.thumbnailOverlay
                        ?.musicItemThumbnailOverlayRenderer
                        ?.content
                        ?.musicPlayButtonRenderer
                        ?.playNavigationEndpoint
                        ?.watchPlaylistEndpoint
                        ?.playlistId ?: return null,
                title =
                    renderer.title.runs
                        ?.firstOrNull()
                        ?.text ?: return null,
                artists =
                    renderer.subtitle
                        ?.runs
                        ?.lastOrNull()
                        ?.let {
                            Artist(
                                id = it.navigationEndpoint?.browseEndpoint?.browseId,
                                name = it.text,
                            )
                        }?.let { listOf(it) } ?: emptyList(),
                year = null,
                isSingle = false,
                thumbnail = renderer.thumbnailRenderer.musicThumbnailRenderer?.getThumbnailUrl() ?: "",
                explicit =
                    renderer.subtitleBadges?.find {
                        it.musicInlineBadgeRenderer?.icon?.iconType == "MUSIC_EXPLICIT_BADGE"
                    } != null,
            )
        }

        fun fromMusicResponsiveListItemRenderer(
            renderer: MusicResponsiveListItemRenderer?,
            album: AlbumItem,
        ): SongItem? {
            if (renderer == null) {
                return null
            } else {
                return SongItem(
                    id = renderer.playlistItemData?.videoId ?: return null,
                    title =
                        renderer.flexColumns
                            .firstOrNull()
                            ?.musicResponsiveListItemFlexColumnRenderer
                            ?.text
                            ?.runs
                            ?.firstOrNull()
                            ?.text ?: return null,
                    artists =
                        renderer.flexColumns
                            .getOrNull(1)
                            ?.musicResponsiveListItemFlexColumnRenderer
                            ?.text
                            ?.runs
                            ?.oddElements()
                            ?.map {
                                Artist(
                                    name = it.text,
                                    id = it.navigationEndpoint?.browseEndpoint?.browseId,
                                )
                            } ?: album.artists ?: emptyList(),
                    album =
                        Album(
                            name = album.title,
                            id = album.id,
                        ),
                    duration =
                        renderer.fixedColumns
                            ?.firstOrNull()
                            ?.musicResponsiveListItemFlexColumnRenderer
                            ?.text
                            ?.runs
                            ?.firstOrNull()
                            ?.text
                            ?.parseTime() ?: return null,
                    thumbnail = album.thumbnail,
                    thumbnails =
                        Thumbnails(
                            thumbnails =
                                listOf(
                                    Thumbnail(
                                        url = album.thumbnail,
                                        width = 544,
                                        height = 544,
                                    ),
                                ),
                        ),
                    explicit =
                        renderer.badges?.find {
                            it.musicInlineBadgeRenderer?.icon?.iconType == "MUSIC_EXPLICIT_BADGE"
                        } != null,
                )
            }
        }
    }
}