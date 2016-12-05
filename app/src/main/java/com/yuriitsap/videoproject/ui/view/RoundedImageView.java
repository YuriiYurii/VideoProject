package com.yuriitsap.videoproject.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.yuriitsap.videoproject.R;

/**
 * Created by yuriitsap on 05.12.16.
 */

/** Rounded image view with the ability to highlight the image. */
// TODO: 05.12.16 refactor, was done in hurry
public class RoundedImageView extends ImageView {

    private static final float ROUND_RADIUS = 200.0F;

    private BitmapShader mBitmapShader;
    private Paint mPaint;
    private Paint mBorderPaint;
    private RectF mRectF;
    private boolean mIsActive;

    public RoundedImageView(Context context) {
        this(context, null);
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context
                .obtainStyledAttributes(attrs, R.styleable.RoundedImageView);
        mIsActive = array.getBoolean(R.styleable.RoundedImageView_is_active, false);
        array.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        activate(mIsActive);
        setLayerType(LAYER_TYPE_SOFTWARE, mBorderPaint);
        mRectF = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mRectF.set(left, top, right, bottom);
    }

    @Override
    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(mBitmapShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(mRectF, ROUND_RADIUS, ROUND_RADIUS, mPaint);
        canvas.drawRoundRect(mRectF, ROUND_RADIUS, ROUND_RADIUS, mBorderPaint);
    }

    public void activate(boolean isActive) {
        mIsActive = isActive;
        mBorderPaint.setShadowLayer(8.0f, 0.0f, 2.0f, mIsActive ? Color.BLUE : Color.BLACK);
    }
}
