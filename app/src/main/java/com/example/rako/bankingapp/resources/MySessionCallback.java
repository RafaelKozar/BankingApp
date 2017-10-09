package com.example.rako.bankingapp.resources;

/**
 * Created by Rafael Kozar on 08/10/2017.
 */

import android.support.v4.media.session.MediaSessionCompat;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;

/**
 * Media Session Callbacks, where all external clients control the player.
 */
public class MySessionCallback extends MediaSessionCompat.Callback {
    private SimpleExoPlayer mExoPlayer;
    public MySessionCallback(SimpleExoPlayer mExoPlayer) {
        this.mExoPlayer = mExoPlayer;
    }

    @Override
    public void onPlay() {
        mExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onPause() {
        mExoPlayer.setPlayWhenReady(false);
    }

    @Override
    public void onSkipToPrevious() {
        mExoPlayer.seekTo(0);
    }
}
