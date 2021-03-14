package com.unarimit.timecapsuleapp.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.ui.common.ConstField;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

public class BitmapHelper {

    static int GAP = dip2px(4);
    static int HEIGHT = dip2px(4);
    public static Bitmap DrawEpochLogBar(int width, String EpochLog){
        Bitmap myBitmap = Bitmap.createBitmap(width, HEIGHT, Bitmap.Config.ARGB_4444);
        float single_width = (float)(width - (ConstField.EpochCount - 1) * GAP) / ConstField.EpochCount;

        Canvas canvas = new Canvas(myBitmap);
        Paint greenPaint = new Paint();
        greenPaint.setColor(DbContext.Context.getColor(R.color.bright_blue));

        Paint redPaint = new Paint();
        redPaint.setColor(DbContext.Context.getColor(R.color.red));
        if(EpochLog.isEmpty())
            return myBitmap;
        char[] array = EpochLog.toCharArray();
        float begin = 0;
        for(int i = 0; i < EpochLog.length(); i ++){
            if(array[i] == '1'){
                canvas.drawRect(new RectF(begin, 0, begin + single_width, HEIGHT), greenPaint);
            }else{
                canvas.drawRect(new RectF(begin, 0, begin + single_width, HEIGHT), redPaint);
            }
            begin += single_width + GAP;
        }


        return myBitmap;
    }

    public static int dip2px(int dp)
    {
        float density = DbContext.Context.getResources().getDisplayMetrics().density;
        return (int) (dp*density+0.5);
    }
}
