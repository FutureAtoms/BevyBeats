package com.futureatoms.bevybeats.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.futureatoms.bevybeats.R
import com.futureatoms.bevybeats.common.Config
import com.futureatoms.bevybeats.data.db.entities.AlbumEntity
import com.futureatoms.bevybeats.data.db.entities.ArtistEntity
import com.futureatoms.bevybeats.data.db.entities.PlaylistEntity
import com.futureatoms.bevybeats.data.db.entities.SongEntity
import com.futureatoms.bevybeats.data.model.browse.album.Track
import com.futureatoms.bevybeats.databinding.FragmentRecentlySongsBinding
import com.futureatoms.bevybeats.extension.navigateSafe
import com.futureatoms.bevybeats.extension.toTrack
import com.futureatoms.bevybeats.pagination.RecentLoadStateAdapter
import com.futureatoms.bevybeats.pagination.RecentPagingAdapter
import com.futureatoms.bevybeats.service.PlaylistType
import com.futureatoms.bevybeats.service.QueueData
import com.futureatoms.bevybeats.viewModel.RecentlySongsViewModel
import com.futureatoms.bevybeats.viewModel.SharedViewModel
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecentlySongsFragment : Fragment() {
    private var _binding: FragmentRecentlySongsBinding? = null
    val binding get() = _binding!!

    private val viewModel by viewModels<RecentlySongsViewModel>()
    private val sharedViewModel by activityViewModels<SharedViewModel>()

    private lateinit var mainAdapter: RecentPagingAdapter
    private lateinit var loadAdapter: RecentLoadStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRecentlySongsBinding.inflate(inflater, container, false)
        binding.topAppBarLayout.applyInsetter {
            type(statusBars = true) {
                margin()
            }
        }
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        mainAdapter = RecentPagingAdapter(requireContext())
        loadAdapter = RecentLoadStateAdapter()

        binding.rvRecentlySongs.apply {
            adapter = mainAdapter.withLoadStateFooter(loadAdapter)
            layoutManager = LinearLayoutManager(context)
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                viewModel.recentlySongs.collectLatest { pagingData ->
                    mainAdapter.submitData(pagingData)
                }
            }
        }
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        mainAdapter.setOnClickListener(
            object : RecentPagingAdapter.onItemClickListener {
                @UnstableApi
                override fun onItemClick(
                    position: Int,
                    type: String,
                ) {
                    if (type == "artist") {
                        val channelId = (mainAdapter.getItemByIndex(position) as ArtistEntity).channelId
                        val args = Bundle()
                        args.putString("channelId", channelId)
                        findNavController().navigateSafe(R.id.action_global_artistFragment, args)
                    }
                    if (type == Config.ALBUM_CLICK) {
                        val browseId = (mainAdapter.getItemByIndex(position) as AlbumEntity).browseId
                        val args = Bundle()
                        args.putString("browseId", browseId)
                        findNavController().navigateSafe(R.id.action_global_albumFragment, args)
                    }
                    if (type == Config.PLAYLIST_CLICK) {
                        val id = (mainAdapter.getItemByIndex(position) as PlaylistEntity).id
                        val args = Bundle()
                        args.putString("id", id)
                        findNavController().navigateSafe(R.id.action_global_playlistFragment, args)
                    }
                    if (type == Config.SONG_CLICK) {
                        val songClicked = mainAdapter.getItemByIndex(position) as SongEntity
                        val videoId = songClicked.videoId
                        val firstQueue: Track = songClicked.toTrack()
                        viewModel.setQueueData(
                            QueueData(
                                listTracks = arrayListOf(firstQueue),
                                firstPlayedTrack = firstQueue,
                                playlistId = "RDAMVM$videoId",
                                playlistName = getString(R.string.recently_added),
                                playlistType = PlaylistType.RADIO,
                                continuation = null,
                            ),
                        )
                        viewModel.loadMediaItem(
                            firstQueue,
                            Config.SONG_CLICK,
                            0,
                        )
                    }
                }
            },
        )
    }
}