package com.yuriitsap.videoproject.ui.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.yuriitsap.videoproject.BR;
import com.yuriitsap.videoproject.ui.model.UserVideo;

/**
 * Created by yuriitsap on 05.12.16.
 */
public class UserVideoViewModel extends BaseObservable implements
        UserVideo.OnUserVideoDataChangeListener {

    private UserVideo mUserVideo;

    public UserVideo getUserVideo() {
        return mUserVideo;
    }

    public void setUserVideo(UserVideo userVideo) {
        //removing previous listener
        if (mUserVideo != null) {
            mUserVideo.removeOnUserVideoDataChangeListener(this);
        }
        mUserVideo = userVideo;
        mUserVideo.addOnUserVideoDataChangeListener(this);
        notifyChange();
    }

    @Bindable
    public boolean getIsPlaying(){
        return mUserVideo.isCurrentlyPlaying();
    }

    @Bindable
    public String getUserName() {
        return mUserVideo.getUserName();
    }

    @Bindable
    public String getVideoDescription() {
        return mUserVideo.getVideoDescription();
    }

    @Bindable
    public String getUserAvatar() {
        return mUserVideo.getUserAvatar();
    }


    @Override
    public void onUserNameChanged(String userName) {
        notifyPropertyChanged(BR.userName);
    }

    @Override
    public void onVideoDescriptionChanged(String videoDescription) {
        notifyPropertyChanged(BR.videoDescription);
    }

    @Override
    public void onUserAvatarChanged(String userAvatar) {
        notifyPropertyChanged(BR.userAvatar);
    }

    @Override
    public void onVideoUrlChanged(String videoUrl) {
        //shouldn't be handled here, has no affect
    }

    @Override
    public void onStartedPlaying(boolean started) {
        notifyChange();
    }
}
