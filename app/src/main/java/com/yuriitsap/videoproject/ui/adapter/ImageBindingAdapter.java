package com.yuriitsap.videoproject.ui.adapter;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yuriitsap.videoproject.utils.transformators.CircleTransformator;

/**
 * Created by yuriitsap on 05.12.16.
 */
public class ImageBindingAdapter {

    @BindingAdapter({"bind:imageUrl", "bind:placeholder", "bind:applyCircleTransformation"})
    public static void loadImage(ImageView imageView, String imageUrl, Drawable placeholder,
            boolean applyCircleTransformation) {
        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setImageDrawable(placeholder);
            return;
        }

        //// TODO: 05.12.16 refactor into options builder
        if (applyCircleTransformation) {
            Picasso.with(imageView.getContext()).load(imageUrl).placeholder(placeholder).transform(
                    new CircleTransformator()).into(new ImageTarget(imageView));
        }else {
            Picasso.with(imageView.getContext()).load(imageUrl).placeholder(placeholder).into(new ImageTarget(imageView));

        }
    }

    //// TODO: 05.12.16 add image path check to avoid fast-scrolling missload behaviour
    private static class ImageTarget implements Target {
        private ImageView mImageView;

        public ImageTarget(ImageView imageView) {
            mImageView = imageView;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            mImageView.setImageBitmap(bitmap);


        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            mImageView.setImageDrawable(errorDrawable);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            //do nothing
        }
    }
}
