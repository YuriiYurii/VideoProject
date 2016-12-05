package com.yuriitsap.videoproject.events;

/**
 * Created by yuriitsap on 05.12.16.
 */
public class TagHasBeenChosenEvent {

    private String mTag;

    public TagHasBeenChosenEvent(String tag) {
        mTag = tag;
    }

    public String getTag() {
        return mTag;
    }

    public static TagHasBeenChosenEvent create(String tag) {
        return new TagHasBeenChosenEvent(tag);
    }
}
