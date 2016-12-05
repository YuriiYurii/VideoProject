package com.yuriitsap.videoproject.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.yuriitsap.videoproject.ui.model.UserVideo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yuriitsap on 05.12.16.
 */

/**
 * Custom Json deserializer which intends to handle vine video request.
 * P.S. was written on fast hand.
 */
public class UserVideoDeserializer implements JsonDeserializer<List<UserVideo>> {
    @Override
    public List<UserVideo> deserialize(JsonElement json, Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {
        List<UserVideo> userVideos = new ArrayList<>();
        Iterator<JsonElement> iterator = ((JsonObject) json).get("data").getAsJsonObject().get(
                "records").getAsJsonArray().iterator();
        while (iterator.hasNext()) {
            JsonElement element = iterator.next();
            UserVideo userVideo = ServiceGenerator.mGson.fromJson(element, UserVideo.class);
            userVideos.add(userVideo);
        }
        return userVideos;
    }
}
