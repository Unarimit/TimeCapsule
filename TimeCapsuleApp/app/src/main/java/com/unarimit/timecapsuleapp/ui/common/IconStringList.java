package com.unarimit.timecapsuleapp.ui.common;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.unarimit.timecapsuleapp.MainActivity;

public class IconStringList {
    public static String[] List = {"\uf805", "\uf094", "\uf042", "\uf521", "\uf13d", "\uf556", "\uf5d1",
            "\uf5d2", "\uf05e", "\uf434", "\uf2cd", "\uf84a", "\uf02d", "\uf0b1", "\uf188", "\uf0a1",
            "\uf207", "\uf787", "\uf6be", "\uf121", "\uf561", "\uf0f4", "\uf086", "\uf14e", "\uf654",
            "\uf522", "\uf6d3", "\uf567", "\uf44b", "\uf0fb", "\uf182", "\uf56b", "\uf583", "\uf6e2",
            "\uf7a6", "\uf004", "\uf7a9", "\uf21e", "\uf58f", "\uf1da", "\uf6e3", "\uf015", "\uf534",
            "\uf1ab", "\uf1b6", "\uf3b5", "\uf554", "\uf2e7", "\uf773", "\uf164", "\uf54c", "\uf687",
            "\uf5c4", "\uf45d", "\uf3fd", "\uf7d9", "\uf7d8", "\uf5bb", "\uf67c", "\uf11b", "\uf5dc"};

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
