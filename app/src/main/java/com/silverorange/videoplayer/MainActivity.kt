package com.silverorange.videoplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import io.noties.markwon.Markwon
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.net.URL
import javax.sql.DataSource

class MainActivity : AppCompatActivity() ,Player.Listener {

    private var mPlayer : ExoPlayer?= null
    private var playbackPosition = 0L
    private var playWhenReady = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preparePlayer()
        setTextValue()
    }

    private fun setTextValue() {
        var markwon = Markwon.create(this@MainActivity)
        markwon.setMarkdown(bodyTV," Lor.e►, ipsu►. dolor st't aw.et, cous.ect.etur \n" +
                ". . acitpacuig elif. Plias-eflus of wii eu purus waVesuacia sag■tt■s. 544 1.-efupuslar-e wequ-e, ;14 woffis olio viv.erra quis. \n" +
                "5441 sagiths olio purus, vita4VetUacia 141.414C cursus s'ed. Ikt.e9-er id Vectus V•ef -ex uftrici-es cowvaffis ac plac.erat .eros. Nuffa orwar-e posu.er4 of pulv■uar kii woxiktius \n" +
                "Aeweak varius waVesuacia cowvaliis. A414441.1 tiibb jUStO, VUiputat.e ac StIPI•th iti, POrta \n")

    }

    private fun preparePlayer() {
        //added Exoplayer
        mPlayer = ExoPlayer.Builder(this).build()
        mPlayer?.playWhenReady = true
        playerView.player = mPlayer
        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
        val mediaItem = MediaItem.fromUri(URL)
        val mediaSource = HlsMediaSource.Factory(defaultHttpDataSourceFactory).createMediaSource(mediaItem)
        mPlayer?.apply {
            setMediaSource(mediaSource)
            seekTo(playbackPosition)
            playWhenReady = playWhenReady
            prepare()
        }
    }

    private fun releasePlayer()
    {
        mPlayer?.let { player ->
            playbackPosition = player.currentPosition
            playWhenReady = player.playWhenReady
            player.release()
            mPlayer = null
        }
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    companion object
    {
        //Added url because unable to parse data from server
        const val URL = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"
    }
}