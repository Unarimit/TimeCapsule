package com.unarimit.timecapsuleapp.ui.common;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.unarimit.timecapsuleapp.MainActivity;

public class IconStringList {
    public static String[] List = {"\uf805", "\uf094", "\uf094", "\uf521", "\uf094", "\uf094", "\uf094"};

    public static Bitmap StringToBitmap(String icon, String color){
        Bitmap myBitmap = Bitmap.createBitmap(128, 160, Bitmap.Config.ARGB_4444);
        Canvas myCanvas = new Canvas(myBitmap);
        Paint paint = new Paint();
        Typeface clock = Typeface.createFromAsset(MainActivity.Activity.getAssets(), "fonts/fa_solid_900.ttf");
        paint.setAntiAlias(true);
        paint.setSubpixelText(true);
        paint.setTypeface(clock);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor(color));
        paint.setTextSize(128);
        paint.setTextAlign(Paint.Align.CENTER);
        myCanvas.drawText(icon, 64, 128, paint);
        return myBitmap;
    }
}
