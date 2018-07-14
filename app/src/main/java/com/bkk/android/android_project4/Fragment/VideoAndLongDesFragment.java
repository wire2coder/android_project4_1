package com.bkk.android.android_project4.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bkk.android.android_project4.Model.Step;
import com.bkk.android.android_project4.R;
import com.google.android.exoplayer2.DefaultLoadControl;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;

import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class VideoAndLongDesFragment extends Fragment {

    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private BandwidthMeter bandwidthMeter;
    private Handler mainHandler;


    // empty constructor for Fragment
    public VideoAndLongDesFragment() {
    }



    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_video_and_desc, container, false);

        // TODO: 7/14, add 'click listener' to these buttons
        Button but_next = rootView.findViewById(R.id.but_next);
        Button but_previous = rootView.findViewById(R.id.but_previous);


        Step step_object = getArguments().getParcelable("step_object");


        mainHandler = new Handler();
        bandwidthMeter = new DefaultBandwidthMeter();
        simpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.video_player1);
        simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        String link_to_video = step_object.getVideoURL();
        Uri uri1 = Uri.parse( link_to_video );

//        Log.v("tag", uri1.toString() );

        makeNewPlayer( uri1 );


        return rootView;
    } // onCreateView


    // create a new VideoPlayer
    private void makeNewPlayer(Uri mediaUri) {

            if (player == null) {

                TrackSelection.Factory videoTrackSelectionFactory =
                        new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);

                DefaultTrackSelector trackSelector =
                        new DefaultTrackSelector(mainHandler, videoTrackSelectionFactory);

                LoadControl loadControl = new DefaultLoadControl();

                player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
                simpleExoPlayerView.setPlayer(player);

                String userAgent = Util.getUserAgent(getContext(), "Baking Cookie App");

                MediaSource mediaSource = new ExtractorMediaSource(
                        mediaUri
                        , new DefaultDataSourceFactory(getContext(), userAgent)
                        , new DefaultExtractorsFactory()
                        , null
                        , null
                );


                player.prepare(mediaSource);
                player.setPlayWhenReady(true);


            } // if

    } // makePlayer()


    @Override
    public void onDetach() {
        super.onDetach();

        if (player!=null) {
            player.stop();
            player.release();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (player!=null) {
            player.stop();
            player.release();
            player=null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (player!=null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (player!=null) {
            player.stop();
            player.release();
        }
    }


} // class VideoAndLongDesFragment