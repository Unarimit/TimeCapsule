package com.unarimit.timecapsuleapp.ui.common;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class IconTextView extends androidx.appcompat.widget.AppCompatTextView {

    public IconTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setTypeface(@Nullable Typeface tf, int style) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/fa_solid_900.ttf");
        super.setTypeface(tf, style);
    }
}
