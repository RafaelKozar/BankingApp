package com.example.rako.bankingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.rako.bankingapp.R;
import com.example.rako.bankingapp.resources.MySessionCallback;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

/**
 * Created by rako on 29/09/2017.
 */

public class FragmentRecipeStepDetail extends Fragment implements View.OnTouchListener{
    private TextView titulo;
    private TextView description;
    private String urlVideo;
    private String stringTitulo;
    private String stringDescription;
    private String TAG = this.getTag();
    private ImageButton buttonRight;
    private ImageButton buttonLeft;

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;


    private GestureDetector gestureDetector;
    private InterfacePhone listenerSwipe;

    public interface InterfacePhone {
        void delegateSwipeRight();
        void delegateSwipeLeft();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listenerSwipe = (InterfacePhone) context;
        this.gestureDetector = new GestureDetector(context, new GestureListener());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_detail_view, container, false);
        titulo = view.findViewById(R.id.titulo_step);
        description = view.findViewById(R.id.description);

        mPlayerView = view.findViewById(R.id.player_view);

        buttonRight = view.findViewById(R.id.button_right);
        buttonLeft = view.findViewById(R.id.button_left);

        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerSwipe.delegateSwipeRight();
            }
        });

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerSwipe.delegateSwipeLeft();
            }
        });

        titulo.setText(stringTitulo);
        description.setText(stringDescription);


        view.setOnTouchListener(this);
        return view;
    }

    public void setDescription(String descriptionString) {
        this.stringDescription = descriptionString;
    }

    public void setTitulo(String tituloString) {
        this.stringTitulo = tituloString;
    }

    public void setUrlVideo(String url) {
        this.urlVideo = url;
    }

    private void initilizeMediaSession() {
        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback(mExoPlayer));

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        return gestureDetector.onTouchEvent(motionEvent);
    }


    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            listenerSwipe.delegateSwipeRight();
                        } else {
                            listenerSwipe.delegateSwipeLeft();
                        }
                        result = true;
                    }
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

}
