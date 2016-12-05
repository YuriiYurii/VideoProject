package com.yuriitsap.videoproject.ui.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuriitsap on 05.12.16.
 */
public class UserVideo {

    @SerializedName("username")
    private String mUserName;
    @SerializedName("description")
    private String mVideoDescription;
    @SerializedName("avatarUrl")
    private String mUserAvatar;
    @SerializedName("videoLowURL")
    private String mVideoUrl;

    private boolean mIsCurrentlyPlaying;

    private List<OnUserVideoDataChangeListener> mListeners = new ArrayList<>();

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
        for (OnUserVideoDataChangeListener listener : mListeners) {
            listener.onVideoUrlChanged(videoUrl);
        }
    }

    public boolean isCurrentlyPlaying() {
        return mIsCurrentlyPlaying;
    }

    public void setCurrentlyPlaying(boolean currentlyPlaying) {
        mIsCurrentlyPlaying = currentlyPlaying;
        for (OnUserVideoDataChangeListener listener : mListeners) {
            listener.onStartedPlaying(currentlyPlaying);
        }
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
        for (OnUserVideoDataChangeListener listener : mListeners) {
            listener.onUserNameChanged(userName);
        }
    }

    public String getVideoDescription() {
        return mVideoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        mVideoDescription = videoDescription;
        for (OnUserVideoDataChangeListener listener : mListeners) {
            listener.onVideoDescriptionChanged(videoDescription);
        }
    }

    public String getUserAvatar() {
        return mUserAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        mUserAvatar = userAvatar;
        for (OnUserVideoDataChangeListener listener : mListeners) {
            listener.onUserAvatarChanged(userAvatar);
        }
    }

    public void addOnUserVideoDataChangeListener(OnUserVideoDataChangeListener listener) {
        mListeners.add(listener);
    }

    public void removeOnUserVideoDataChangeListener(OnUserVideoDataChangeListener listener) {
        mListeners.remove(listener);
    }

    public interface OnUserVideoDataChangeListener {
        void onUserNameChanged(String userName);

        void onVideoDescriptionChanged(String videoDescription);

        void onUserAvatarChanged(String userAvatar);

        void onVideoUrlChanged(String videoUrl);

        void onStartedPlaying(boolean started);
    }
}
