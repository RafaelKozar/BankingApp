package com.example.rako.bankingapp.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rako.bankingapp.R;
import com.example.rako.bankingapp.activitys.RecipeDetail;
import com.example.rako.bankingapp.adapters.AdapterIngredientes;
import com.example.rako.bankingapp.adapters.AdapterSteps;
import com.example.rako.bankingapp.model.Ingredient;
import com.example.rako.bankingapp.resources.ComponentListenner;
import com.example.rako.bankingapp.resources.MySessionCallback;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

/**
 * Created by rako on 29/09/2017.
 */

public class FragmentRecipeStepDetail extends Fragment implements View.OnTouchListener{
    private String urlVideo;
    private String stringTitulo;
    private String stringDescription;
    private static final String TAG = FragmentRecipeStepDetail.class.getSimpleName();

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private boolean temVideo;
    private boolean isIngredients;
    private List<Ingredient> ingredientList;


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

    public FragmentRecipeStepDetail() {

    }

    public FragmentRecipeStepDetail(String urlVideo) {
        this.urlVideo = urlVideo;
        Log.i(TAG, "URLVideo "+ urlVideo);
        setTemVideo(urlVideo);
    }

    public FragmentRecipeStepDetail(String urlVideo, List<Ingredient> ingredients) {
        this.urlVideo = urlVideo;
        Log.i(TAG, "URLVideo "+ urlVideo);
        setTemVideo(urlVideo);
        this.ingredientList = ingredients;
        isIngredients = true;
    }

    public void setTemVideo(String urlVideo) {
        if (urlVideo != null && !urlVideo.isEmpty()) {
            temVideo = true;
        } else {
            temVideo = false;
        }
        isIngredients = false;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_detail_view, container, false);
        setRetainInstance(true);
        mPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.player_view);
        FrameLayout frameLayout = view.findViewById(R.id.frame_no_video);

        if (temVideo) {
            initilizeMediaSession();
            initializePlayer(Uri.parse(urlVideo));
            frameLayout.setVisibility(View.GONE);

        } else {
            mPlayerView.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);
        }

        if (Configuration.ORIENTATION_LANDSCAPE != getResources().getConfiguration().orientation) {
            TextView titulo = view.findViewById(R.id.titulo_step);
            TextView description = view.findViewById(R.id.description);

            ImageButton buttonRight = view.findViewById(R.id.button_right);
            ImageButton buttonLeft = view.findViewById(R.id.button_left);

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

            LinearLayout linearLayout = view.findViewById(R.id.separadores);
            RecyclerView recyclerView = view.findViewById(R.id.ingredientes);
            if (isIngredients) {
                linearLayout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                description.setVisibility(View.GONE);
                titulo.setText(getString(R.string.texto_introducao_ingrediente));

                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setNestedScrollingEnabled(false);

                AdapterIngredientes adapterIngredientes = new AdapterIngredientes();
                adapterIngredientes.setIngredientList(ingredientList);
                recyclerView.setAdapter(adapterIngredientes);


            } else {
                linearLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                description.setVisibility(View.VISIBLE);
            }

            view.setOnTouchListener(this);
        }
        return view;
    }

    public void setDescription(String descriptionString) {
        this.stringDescription = descriptionString;
    }

    public void setTitulo(String tituloString) {
        this.stringTitulo = tituloString;
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


    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory trackFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(trackFactory);
            RenderersFactory renderersFactory = new DefaultRenderersFactory(getContext());
            LoadControl loadControl = new DefaultLoadControl();
            //Create our instance of `ExoPlayer`
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    renderersFactory,
                    trackSelector,
                    loadControl);

            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            ComponentListenner componentListenner = new ComponentListenner(mStateBuilder, mExoPlayer, mMediaSession);
            mExoPlayer.addListener(componentListenner);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onDestroyView() {
        releasePlayer();
        super.onDestroyView();
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
                            //listenerSwipe.delegateSwipeRight();
                            listenerSwipe.delegateSwipeLeft();
                        } else {
                            //listenerSwipe.delegateSwipeLeft();
                            listenerSwipe.delegateSwipeRight();
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
