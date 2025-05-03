package com.abhishek.dongle.exoplayerdemo

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.core.net.toUri

@Composable
fun PlayerScreen() {
    val videoUrl = "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4"
    val hlsUrl = "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8"
    val dashUrl = "https://storage.googleapis.com/shaka-demo-assets/angel-one/dash.mpd"

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Video Player",
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        ExoVideoPlayer(url = videoUrl, type = FileType.MP4)
    }
}

@Composable
fun ExoVideoPlayer(url: String, type: FileType) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(createMediaItem(url, type))
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(Unit) {
        onDispose { exoPlayer.release() }
    }

    PlayerView(exoPlayer = exoPlayer)
}

fun createMediaItem(url: String, type: FileType): MediaItem {
    return when (type) {
        FileType.MP4 -> MediaItem.fromUri(url.toUri())
        FileType.HLS -> MediaItem.Builder()
            .setUri(url)
            .setMimeType(MimeTypes.APPLICATION_M3U8)
            .build()

        FileType.DASH -> MediaItem.Builder()
            .setUri(url)
            .setMimeType(MimeTypes.APPLICATION_MPD)
            .build()
    }
}

@Composable
fun PlayerView(exoPlayer: ExoPlayer) {
    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
                useController = true
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
    )
}