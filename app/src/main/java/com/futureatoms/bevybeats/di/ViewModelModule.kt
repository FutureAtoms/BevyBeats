package com.futureatoms.bevybeats.di

import androidx.media3.common.util.UnstableApi
import com.futureatoms.bevybeats.viewModel.AlbumViewModel
import com.futureatoms.bevybeats.viewModel.ArtistViewModel
import com.futureatoms.bevybeats.viewModel.HomeViewModel
import com.futureatoms.bevybeats.viewModel.LibraryDynamicPlaylistViewModel
import com.futureatoms.bevybeats.viewModel.LibraryViewModel
import com.futureatoms.bevybeats.viewModel.NowPlayingBottomSheetViewModel
import com.futureatoms.bevybeats.viewModel.PlaylistViewModel
import com.futureatoms.bevybeats.viewModel.SettingsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

@UnstableApi
val viewModelModule =
    module {
        viewModel {
            NowPlayingBottomSheetViewModel(
                application = androidApplication(),
            )
        }
        viewModel {
            LibraryViewModel(
                application = androidApplication(),
            )
        }
        viewModel {
            LibraryDynamicPlaylistViewModel(
                application = androidApplication(),
            )
        }
        viewModel {
            AlbumViewModel(
                application = androidApplication(),
            )
        }
        viewModel {
            HomeViewModel(
                application = androidApplication(),
            )
        }
        viewModel {
            SettingsViewModel(
                application = androidApplication(),
            )
        }
        viewModel {
            ArtistViewModel(
                application = androidApplication(),
            )
        }
        viewModel {
            PlaylistViewModel(
                application = androidApplication(),
            )
        }
    }