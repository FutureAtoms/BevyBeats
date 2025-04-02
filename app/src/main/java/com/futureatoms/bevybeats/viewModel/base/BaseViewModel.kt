package com.futureatoms.bevybeats.viewModel.base

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import com.futureatoms.kotlinytmusicscraper.models.SongItem
import com.futureatoms.bevybeats.R
import com.futureatoms.bevybeats.common.Config.ALBUM_CLICK
import com.futureatoms.bevybeats.common.Config.PLAYLIST_CLICK
import com.futureatoms.bevybeats.common.Config.RADIO_CLICK
import com.futureatoms.bevybeats.common.Config.RECOVER_TRACK_QUEUE
import com.futureatoms.bevybeats.common.Config.SHARE
import com.futureatoms.bevybeats.common.Config.SONG_CLICK
import com.futureatoms.bevybeats.common.Config.VIDEO_CLICK
import com.futureatoms.bevybeats.data.dataStore.DataStoreManager
import com.futureatoms.bevybeats.data.db.entities.SongEntity
import com.futureatoms.bevybeats.data.model.browse.album.Track
import com.futureatoms.bevybeats.data.repository.MainRepository
import com.futureatoms.bevybeats.extension.toMediaItem
import com.futureatoms.bevybeats.extension.toSongEntity
import com.futureatoms.bevybeats.extension.toTrack
import com.futureatoms.bevybeats.service.QueueData
import com.futureatoms.bevybeats.service.SimpleMediaServiceHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@UnstableApi
abstract class BaseViewModel(
    private val application: Application,
) : AndroidViewModel(application),
    KoinComponent {
    protected val dataStoreManager: DataStoreManager by inject()
    protected val mainRepository: MainRepository by inject()

    // I want I can play track from any viewModel instead of calling from UI to sharedViewModel
    protected val simpleMediaServiceHandler: SimpleMediaServiceHandler by inject()

    private val _nowPlayingVideoId: MutableStateFlow<String> = MutableStateFlow("")

    /**
     * Get now playing video id
     * If empty, no video is playing
     */
    val nowPlayingVideoId: StateFlow<String> get() = _nowPlayingVideoId

    /**
     * Tag for logging
     */
    protected val tag: String = javaClass.simpleName

    /**
     * Log with viewModel tag
     */
    protected fun log(
        message: String,
        logType: Int = Log.WARN,
    ) {
        when (logType) {
            Log.ASSERT -> Log.wtf(tag, message)
            Log.VERBOSE -> Log.v(tag, message)
            Log.DEBUG -> Log.d(tag, message)
            Log.INFO -> Log.i(tag, message)
            Log.WARN -> Log.w(tag, message)
            Log.ERROR -> Log.e(tag, message)
            else -> Log.d(tag, message)
        }
    }

    /**
     * Cancel all jobs
     */
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    init {
        getNowPlayingVideoId()
    }

    protected fun makeToast(message: String?) {
        Toast.makeText(application, message ?: "NO MESSAGE", Toast.LENGTH_SHORT).show()
    }

    protected fun getString(resId: Int): String = application.getString(resId)

    // Loading dialog
    private val _showLoadingDialog: MutableStateFlow<Pair<Boolean, String>> = MutableStateFlow(false to getString(R.string.loading))
    val showLoadingDialog: StateFlow<Pair<Boolean, String>> get() = _showLoadingDialog

    fun showLoadingDialog(message: String? = null) {
        _showLoadingDialog.value = true to (message ?: getString(R.string.loading))
    }

    fun hideLoadingDialog() {
        _showLoadingDialog.value = false to getString(R.string.loading)
    }

    private fun getNowPlayingVideoId() {
        viewModelScope.launch {
            combine(simpleMediaServiceHandler.nowPlayingState, simpleMediaServiceHandler.controlState) { nowPlayingState, controlState ->
                Pair(nowPlayingState, controlState)
            }.collect { (nowPlayingState, controlState) ->
                if (controlState.isPlaying) {
                    _nowPlayingVideoId.value = nowPlayingState.songEntity?.videoId ?: ""
                } else {
                    _nowPlayingVideoId.value = ""
                }
            }
        }
    }

    /**
     * Communicate with SimpleMediaServiceHandler to load media item
     */
    fun setQueueData(queueData: QueueData) {
        simpleMediaServiceHandler.setQueueData(queueData)
    }

    fun <T> loadMediaItem(
        anyTrack: T,
        type: String,
        index: Int? = null,
    ) {
        val track =
            when (anyTrack) {
                is Track -> anyTrack
                is SongItem -> anyTrack.toTrack()
                is SongEntity -> anyTrack.toTrack()
                else -> return
            }
        viewModelScope.launch {
            mainRepository.insertSong(track.toSongEntity()).singleOrNull()?.let {
                log("Inserted song: ${track.title}", Log.DEBUG)
            }
            simpleMediaServiceHandler.clearMediaItems()
            track.durationSeconds?.let {
                mainRepository.updateDurationSeconds(it, track.videoId)
            }
            withContext(Dispatchers.Main) {
                simpleMediaServiceHandler.addMediaItem(track.toMediaItem(), playWhenReady = type != RECOVER_TRACK_QUEUE)
            }
            when (type) {
                SONG_CLICK, VIDEO_CLICK, SHARE -> {
                    simpleMediaServiceHandler.getRelated(track.videoId)
                }
                PLAYLIST_CLICK, ALBUM_CLICK, RADIO_CLICK -> {
                    simpleMediaServiceHandler.loadPlaylistOrAlbum(index)
                }
            }
        }
    }

    fun shufflePlaylist(firstPlayIndex: Int = 0) {
        simpleMediaServiceHandler.shufflePlaylist(firstPlayIndex)
    }

    fun getQueueData() = simpleMediaServiceHandler.queueData.value
}