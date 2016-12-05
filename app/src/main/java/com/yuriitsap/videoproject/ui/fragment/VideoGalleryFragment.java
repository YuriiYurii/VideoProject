package com.yuriitsap.videoproject.ui.fragment;

import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.yuriitsap.videoproject.Application;
import com.yuriitsap.videoproject.R;
import com.yuriitsap.videoproject.databinding.VideoGalleryBinding;
import com.yuriitsap.videoproject.events.TagHasBeenChosenEvent;
import com.yuriitsap.videoproject.ui.adapter.VideoGalleryAdapter;

/**
 * Created by yuriitsap on 05.12.16.
 */
public class VideoGalleryFragment extends Fragment implements
        VideoGalleryAdapter.VideoHasBeenChosenListener {

    private static final String TAG_KEY = "tag_key";

    private String mTag;

    private Bus mBus;
    private VideoGalleryBinding mBinding;
    private VideoGalleryAdapter mAdapter;
    private MediaController mMediaController;

    public static VideoGalleryFragment newInstance(String tag) {
        Bundle args = new Bundle();
        args.putString(TAG_KEY, tag);
        VideoGalleryFragment galleryFragment = new VideoGalleryFragment();
        galleryFragment.setArguments(args);
        return galleryFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mTag = getArguments().getString(TAG_KEY);
        }
        mBus = Application.self().getBus();
        mBus.register(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.video_gallery, container,
                false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new VideoGalleryAdapter(this);
        mBinding.galleryRecyclerView.setAdapter(mAdapter);
        mAdapter.loadVinesByTag(mTag);
        mBinding.galleryRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mMediaController = new
                MediaController(view.getContext());
        mMediaController.setAnchorView(mBinding.videoView);
        mBinding.videoView.setMediaController(mMediaController);
        mMediaController.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAdapter.canHandlePreviousNextVideoClick(false)) {
                    mBinding.galleryRecyclerView.getLayoutManager().scrollToPosition(
                            mAdapter.getCurrentVideoIndex());
                    mAdapter.notifyItemRangeChanged(mAdapter.getCurrentVideoIndex() - 1,
                            2);
                }

            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAdapter.canHandlePreviousNextVideoClick(true)) {
                    mBinding.galleryRecyclerView.getLayoutManager().scrollToPosition(
                            mAdapter.getCurrentVideoIndex());
                    mAdapter.notifyItemRangeChanged(mAdapter.getCurrentVideoIndex(),
                            2);
                }
            }
        });
        mBinding.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (mAdapter.canHandlePreviousNextVideoClick(false)) {
                    mBinding.galleryRecyclerView.getLayoutManager().scrollToPosition(
                            mAdapter.getCurrentVideoIndex());
                    mAdapter.notifyItemRangeChanged(mAdapter.getCurrentVideoIndex() - 1,
                            2);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mBinding.videoView.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mBinding.videoView.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
    }

    @Subscribe
    public void onTagHasBeenChosen(TagHasBeenChosenEvent event) {
        setTag(event.getTag());
    }

    public void setTag(String tag) {
        mTag = tag;
        if (getArguments() != null) {
            getArguments().putString(TAG_KEY, mTag);
        }
        mAdapter.loadVinesByTag(mTag);
    }

    @Override
    public void onVideoHasBeenChosen(String url) {
        mBinding.videoView.setVideoPath(url);
        mBinding.videoView.start();
    }
}
