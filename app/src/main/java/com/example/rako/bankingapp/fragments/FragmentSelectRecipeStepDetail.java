package com.example.rako.bankingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rako.bankingapp.R;
import com.example.rako.bankingapp.activitys.RecipeDetail;
import com.example.rako.bankingapp.adapters.AdapterIngredientes;
import com.example.rako.bankingapp.model.Ingredient;
import com.example.rako.bankingapp.model.Recipe;
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
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rako on 29/09/2017.
 */

public class FragmentSelectRecipeStepDetail extends Fragment {
    private static SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private static final String TAG = FragmentSelectRecipeStepDetail.class.getSimpleName();

    private List<Ingredient> ingredientList;

    private boolean temVideo;
    private boolean temImg;
    private boolean isIngredients;
    private String urlVideo;
    private String stringTitulo;
    private String urlImg;
    private String urlImg2;
    private String stringDescription;

    private static Long positionTime;
    private static boolean isReady;
    public static Bundle bundle = null;

    private static final String POSITION = "positionTime";
    private static final String PLAY_PAUSE = "getPlayPause";

    public FragmentSelectRecipeStepDetail(String urlVideo, String urlImg, long pTime, boolean ready,
                                          String urlImg2) {
        Log.i(TAG, "URLVideo " + urlVideo);
        this.urlVideo = urlVideo;
        isIngredients = false;
        this.urlImg = urlImg;
        setTemVideo(urlVideo);
        setTemImg(urlImg);
        positionTime = pTime;
        isReady = ready;
        this.urlImg2 = urlImg2;
    }

    public FragmentSelectRecipeStepDetail(String urlVideo, List<Ingredient> ingredients,
                                          String urlImg, long pTime, boolean ready, String urlImg2) {
        Log.i(TAG, "URLVideo " + urlVideo);
        this.urlVideo = urlVideo;
        this.ingredientList = ingredients;
        isIngredients = true;
        this.urlImg = urlImg;
        setTemVideo(urlVideo);
        setTemImg(urlImg);
        positionTime = pTime;
        isReady = ready;
        this.urlImg2 = urlImg2;
    }

    public FragmentSelectRecipeStepDetail() {

    }

    public void setTemVideo(String urlVideo) {
        temVideo = urlVideo != null && !urlVideo.isEmpty();
    }

    public void setTemImg(String urlImg) {
        temImg = urlImg != null && !urlImg.isEmpty();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("FragmentRecipeStepDetail", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_select_step_detail_view, container, false);
        if (!temVideo) {
            FrameLayout frameLayout = view.findViewById(R.id.frame_no_video);
            ImageView thumbnail = view.findViewById(R.id.thumbnailIMG);

            if (temImg) {
                setImage(urlImg, view);
            } else if (urlImg2 != null && !urlImg2.contains("")) {
                setImage(urlImg2, view);
            }else{
                thumbnail.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
            }
        }else{
            view.findViewById(R.id.frame_no_video).setVisibility(View.GONE);
            view.findViewById(R.id.thumbnailIMG).setVisibility(View.GONE);
            view.findViewById(R.id.simpleExoPlayerView).setVisibility(View.VISIBLE);
        }

        mPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.simpleExoPlayerView);

        if (temVideo) {
            initilizeMediaSession();
            initializePlayer(Uri.parse(urlVideo));
        }

        TextView titulo = view.findViewById(R.id.titulo_step);
        TextView description = view.findViewById(R.id.description);
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

        return view;
    }

    private void setImage(String image, View view) {
        ImageView imageView = view.findViewById(R.id.thumbnailIMG);
        Picasso.with(getContext())
                .load(image)
                .into(imageView);
        view.findViewById(R.id.thumbnailIMG).setVisibility(View.VISIBLE);
        view.findViewById(R.id.frame_no_video).setVisibility(View.GONE);
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
        }
    }

    private void releasePlayer() {
        mExoPlayer.release();
        mExoPlayer.stop();
        mExoPlayer = null;
    }

    public void onPause() {
        if (mExoPlayer != null) {
            positionTime = mExoPlayer.getCurrentPosition();
            isReady = mExoPlayer.getPlayWhenReady() && mExoPlayer.getPlaybackState() == 3;
            setState();
            releasePlayer();
        }
        super.onPause();
    }

    public void setTitulo(String stringTitulo) {
        this.stringTitulo = stringTitulo;
    }

    public void setDescription(String stringDescription) {
        this.stringDescription = stringDescription;
    }

    public void setmExoPlayerBackView() {
        if (temVideo) {
            initilizeMediaSession();
            initializePlayer(Uri.parse(urlVideo));
        }
        if (mExoPlayer != null) {
            if (positionTime != null && positionTime != 0) {
                mExoPlayer.seekTo(positionTime);
            }
            if (isReady) {
                mExoPlayer.setPlayWhenReady(true);
            } else {
                mExoPlayer.setPlayWhenReady(false);
            }
        }
    }

    /*********** onSaveInstante  ***********/
    /** I implemented onSavenInstante on rotation in parent activity */

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.e("FragmentRecipeStepDetail", "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.e("FragmentRecipeStepDetail", "onSaveInstanceState");
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onResume() {
        Log.e("FragmentRecipeStepDetail", "onResume");
        setmExoPlayerBackView();
        super.onResume();
    }

    public static void setState() {
        bundle = new Bundle();
        if (mExoPlayer != null) {
            positionTime = mExoPlayer.getCurrentPosition();
            isReady = mExoPlayer.getPlayWhenReady() && mExoPlayer.getPlaybackState() == 3;
            bundle.putLong(POSITION, positionTime);
            bundle.putBoolean(PLAY_PAUSE, isReady);
        }
    }

//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        Log.e("FragmentRecipeStepDetail", "onViewStateRestored");
//        if (savedInstanceState != null) {
//           positionTime = savedInstanceState.getLong(POSITION, 0);
//           isReady = savedInstanceState.getBoolean(PLAY_PAUSE, false);
//           setmExoPlayerBackView();
//        }
//        super.onViewStateRestored(savedInstanceState);
//    }
}
