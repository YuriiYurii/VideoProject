package com.yuriitsap.videoproject.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuriitsap.videoproject.R;
import com.yuriitsap.videoproject.databinding.VideoItemBinding;
import com.yuriitsap.videoproject.ui.model.UserVideo;
import com.yuriitsap.videoproject.ui.viewmodel.UserVideoViewModel;
import com.yuriitsap.videoproject.utils.ServiceGenerator;
import com.yuriitsap.videoproject.utils.rest.VineVideoEndpoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuriitsap on 05.12.16.
 */
public class VideoGalleryAdapter extends RecyclerView.Adapter<VideoGalleryAdapter.BindingHolder> {

    private static final String TAG = VideoGalleryAdapter.class.getSimpleName();

    private List<UserVideo> mUserVideos;
    private VineVideoEndpoint mEndpoint;
    private int mCurrentlyPlayingVideoPosition = 0;
    private VideoHasBeenChosenListener mListener;

    public VideoGalleryAdapter(@NonNull VideoHasBeenChosenListener listener) {
        mListener = listener;
        mEndpoint = ServiceGenerator.createService(VineVideoEndpoint.class);
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UserVideoViewModel viewModel = new UserVideoViewModel();
        VideoItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.video_item, parent, false);
        binding.setUserVideo(viewModel);
        return new BindingHolder(binding, viewModel);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.setItem(mUserVideos.get(position));
    }

    @Override
    public int getItemCount() {
        return mUserVideos == null ? 0 : mUserVideos.size();
    }

    public void loadVinesByTag(String tag) {
        mEndpoint.getAllVideosByTag(tag).enqueue(new Callback<List<UserVideo>>() {
            @Override
            public void onResponse(Call<List<UserVideo>> call, Response<List<UserVideo>> response) {
                mUserVideos = response.body();
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<UserVideo>> call, Throwable t) {
                Log.e(TAG, "failure");

            }
        });
    }

    public boolean canHandlePreviousNextVideoClick(boolean previous) {
        //looping through our video data set
        if ((mCurrentlyPlayingVideoPosition <= 0 && previous)
                || (mCurrentlyPlayingVideoPosition >= mUserVideos.size()-1 && !previous)) {
            return false;
        } else {
            mUserVideos.get(mCurrentlyPlayingVideoPosition).setCurrentlyPlaying(false);
            mCurrentlyPlayingVideoPosition =
                    previous ? --mCurrentlyPlayingVideoPosition : ++mCurrentlyPlayingVideoPosition;
            mUserVideos.get(mCurrentlyPlayingVideoPosition).setCurrentlyPlaying(true);
            return true;
        }
    }

    public int getCurrentVideoIndex() {
        return mCurrentlyPlayingVideoPosition;
    }

    //can be used in conjunction with generics, but not needed - it will only make our logic harder
    // to understand
    public class BindingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private VideoItemBinding mBinding;
        private UserVideoViewModel mUserVideoViewModel;

        public BindingHolder(VideoItemBinding binding,
                UserVideoViewModel userVideoViewModel) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.content.setOnClickListener(this);
            mUserVideoViewModel = userVideoViewModel;
        }

        public void setItem(UserVideo item) {
            mUserVideoViewModel.setUserVideo(item);
            mBinding.userAvatar.activate(item.isCurrentlyPlaying());
            if (item.isCurrentlyPlaying()) {
                mListener.onVideoHasBeenChosen(mUserVideoViewModel.getUserVideo().getVideoUrl());
            }
            mBinding.executePendingBindings();

        }

        // TODO: 05.12.16 move into a separate handler???
        @Override
        public void onClick(View view) {
            UserVideo video = mUserVideos.get(mCurrentlyPlayingVideoPosition);
            //disabling previous item highlighting
            if (mCurrentlyPlayingVideoPosition != getAdapterPosition()) {
                video.setCurrentlyPlaying(false);
                mCurrentlyPlayingVideoPosition = getAdapterPosition();
                video = mUserVideos.get(mCurrentlyPlayingVideoPosition);
            }
            video.setCurrentlyPlaying(!video.isCurrentlyPlaying());
            mBinding.userAvatar.activate(video.isCurrentlyPlaying());
            mListener.onVideoHasBeenChosen(mUserVideoViewModel.getUserVideo().getVideoUrl());

        }

    }

    //Alternative for posting events
    public interface VideoHasBeenChosenListener {
        void onVideoHasBeenChosen(String url);
    }
}
