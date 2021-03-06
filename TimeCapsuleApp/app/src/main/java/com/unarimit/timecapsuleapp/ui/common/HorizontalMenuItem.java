package com.unarimit.timecapsuleapp.ui.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.Nullable;

import com.unarimit.timecapsuleapp.R;

public class HorizontalMenuItem extends androidx.appcompat.widget.AppCompatTextView {
    public HorizontalMenuItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setTextSize(18);
        this.SetOnClick(false);
        this.setGravity(Gravity.CENTER);
        this.setBackground(getResources().getDrawable(R.drawable.boradline, null));
    }

    public void SetOnClick(boolean isOnClick){
        if(isOnClick){
            this.setBackgroundColor(getResources().getColor(R.color.bright_blue, null));
            this.setTextColor(getResources().getColor(R.color.white, null));
        }else{
            this.setBackgroundColor(getResources().getColor(R.color.white, null));
            this.setTextColor(getResources().getColor(R.color.black, null));
        }
    }
}
