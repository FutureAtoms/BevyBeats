
<div align="center">
	<img src="https://raw.githubusercontent.com/futureatoms/BevyBeats/jetpack_compose/fastlane/metadata/android/en-US/images/featureGraphic.png">
<h1>BevyBeats</h1>A premium Music AI app that plays and generates AI music<br>Developed by Abhilash Chadhar (FutureAtoms)<br>
<br>
<a href="https://github.com/futureatoms/BevyBeats/releases"><img src="https://img.shields.io/github/v/release/futureatoms/BevyBeats"></a>
<a href="https://github.com/futureatoms/BevyBeats/releases"><img src="https://img.shields.io/github/downloads/futureatoms/BevyBeats/total"></a>
<br>
<br>
<a href="https://apt.izzysoft.de/packages/com.futureatoms.bevybeats/"><img src="https://gitlab.com/IzzyOnDroid/repo/-/raw/master/assets/IzzyOnDroid.png" height="80"></a>
<a href="https://f-droid.org/en/packages/com.futureatoms.bevybeats/"><img src="https://fdroid.gitlab.io/artwork/badge/get-it-on.png" height="80"></a>
<a href="https://www.openapk.net/bevybeats/com.futureatoms.bevybeats/"><img src="https://www.openapk.net/images/openapk-badge.png" height="80"></a>
<a href="https://www.androidfreeware.net/download-bevybeats-apk.html"><img src="https://www.androidfreeware.net/images/androidfreeware-badge.png" height="80"></a>
<a href="https://github.com/futureatoms/BevyBeats/releases"><img src="https://raw.githubusercontent.com/NeoApplications/Neo-Backup/034b226cea5c1b30eb4f6a6f313e4dadcbb0ece4/badge_github.png" height="80"></a>
<a href="https://www.producthunt.com/posts/bevybeats?embed=true&utm_source=badge-featured&utm_medium=badge&utm_souce=badge-bevybeats" target="_blank"><img src="https://api.producthunt.com/widgets/embed-image/v1/featured.svg?post_id=797736&theme=light&t=1737299913224" alt="BevyBeats - A&#0032;FOSS&#0032;YouTube&#0032;Music&#0032;client&#0032;for&#0032;Android&#0032;with&#0032;many&#0032;features | Product Hunt" style="width: 250px; height: 80px;" width="250" height="80" /></a>
<h3>Nightly Build<h3>
<a href="https://nightly.link/futureatoms/BevyBeats/workflows/android/jetpack_compose/app.zip"><img src="https://github.com/futureatoms/BevyBeats/actions/workflows/android.yml/badge.svg"></a><br/>
<a href="https://nightly.link/futureatoms/BevyBeats/workflows/android/jetpack_compose/app.zip"><img src="https://raw.githubusercontent.com/NeoApplications/Neo-Backup/034b226cea5c1b30eb4f6a6f313e4dadcbb0ece4/badge_github.png" height="80"></a>
</div>
	
## Features ✨️

- Play and generate AI music 
- Browsing Home, Charts, Podcast, Moods & Genre with high speed
- Search everything
- Analyze your playing data, create custom playlists
- Spotify Canvas supported
- Play 1080p video option with subtitle
- AI-generated music suggestions
- Notification from followed artists
- Caching and can save data for offline playback
- Synced lyrics from Musixmatch, LRCLIB, Spotify (require login) and YouTube Transcript and translated lyrics (Community translation from Musixmatch)
- Personalize data (*) and multi-account support
- Support SponsorBlock, Return YouTube Dislike
- Sleep Timer
- Android Auto with online content
- And many more!

> (*) For users who chose "Send back to Google" feature

> **Warning**

>This app is in the beta stage, so it may have many bugs and make it crash. If you find any bugs,
> please create an issue or contact me via email or discord sever.




## Data

- This app uses hidden API from YouTube Music with some tricks to get data from YouTube Music.
- Use Spotify Web API and some tricks to get Spotify Canvas and Lyrics 
- Thanks to [SimpMusic](https://github.com/maxrave-dev/SimpMusic) for the best App ever 
- Thanks to [InnerTune](https://github.com/z-huang/InnerTune/) for the idea to get data from YouTube Music. This repo is my inspiration to create this app
- My app is using [SponsorBlock](https://sponsor.ajay.app/) to skip sponsor in YouTube videos.
- ReturnYouTubeDislike for getting information on votes
- Lyrics data from Musixmatch and LRCLIB. More information [Musixmatch](https://developer.musixmatch.com/), [LRCLIB](https://lrclib.net/)

## Privacy

BevyBeats doesn't have any tracker or third-party server for collecting user data. BevyBeats is a premium Music AI app that respects your privacy. If YouTube
logged-in users enable "Send back to Google" feature, BevyBeats only uses YouTube Music Tracking API
to send listening history and listening record of video to Google for better recommendations and
supporting artist or YouTube Creator (For API reference,
see [this](https://github.com/futureatoms/BevyBeats/blob/13f7ab6e5fa521b62a9fd31a1cefdc2787a1a8af/kotlinYtmusicScraper/src/main/java/com/futureatoms/kotlinytmusicscraper/Ytmusic.kt#L639C4-L666C1)).


## FAQ

#### 1. Wrong Lyrics?

YouTube Music is not an official partner of Musixmatch so you can't get lyrics directly if using YouTube"
videoId" parameter. So I need to use some "String Matcher" and "Duration" for search lyrics. So
sometimes, some songs or videos get the wrong lyric's

#### 2. Why the name or brand is "BevyBeats"?

BevyBeats is a premium Music AI app that plays and generates AI music. The name combines musical imagery with modern AI technology, representing the app's purpose.

## Developer/Team

### [Abhilash Chadhar (FutureAtoms)](https://github.com/futureatoms/BevyBeats): Founder/Developer/Designer 

