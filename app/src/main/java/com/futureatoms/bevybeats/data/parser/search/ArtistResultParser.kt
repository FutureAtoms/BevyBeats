package com.futureatoms.bevybeats.data.parser.search

import com.futureatoms.kotlinytmusicscraper.models.ArtistItem
import com.futureatoms.kotlinytmusicscraper.pages.SearchResult
import com.futureatoms.bevybeats.data.model.searchResult.artists.ArtistsResult
import com.futureatoms.bevybeats.data.model.searchResult.songs.Thumbnail

fun parseSearchArtist(result: SearchResult): ArrayList<ArtistsResult> {
    val artistsResult: ArrayList<ArtistsResult> = arrayListOf()
    result.items.forEach {
        val artist = it as ArtistItem
        artistsResult.add(
            ArtistsResult(
                artist = artist.title,
                browseId = artist.id,
                category = "Artist",
                radioId = artist.radioEndpoint?.playlistId ?: "",
                resultType = "Artist",
                shuffleId = artist.shuffleEndpoint?.playlistId ?: "",
                thumbnails = listOf(Thumbnail(544, Regex("([wh])120").replace(artist.thumbnail, "$1544"), 544)),
            ),
        )
    }
    return artistsResult
}