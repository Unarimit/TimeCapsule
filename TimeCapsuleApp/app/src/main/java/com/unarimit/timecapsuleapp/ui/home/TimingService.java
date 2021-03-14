package com.unarimit.timecapsuleapp.ui.home;

import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationChannelCompat.Builder;

import com.unarimit.timecapsuleapp.MainActivity;
import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.ui.common.IconStringList;
import com.unarimit.timecapsuleapp.utils.TimeHelper;

import java.util.Locale;

public class TimingService extends Service {
    public TimingService() {

    }

    RemoteViews remoteViews;
    NotificationManagerCompat notificationManager;
    androidx.core.app.NotificationCompat.Builder notificationBuilder;
    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = NotificationManagerCompat.from(this);
        NotificationChannelCompat channel = new NotificationChannelCompat.Builder("my_test",  NotificationManagerCompat.IMPORTANCE_LOW)
                .setName("name1")
                .setShowBadge(false)
                .build();
        notificationManager.createNotificationChannel(channel);

        remoteViews = new RemoteViews(getPackageName(), R.layout.notification_view);
        remoteViews.setImageViewBitmap(R.id.notification_view_icon, null);
        remoteViews.setViewVisibility(R.id.notification_view_during, View.VISIBLE);
        remoteViews.setViewVisibility(R.id.notification_view_count, View.GONE);
        remoteViews.setTextViewText(R.id.notification_view_during, "现在没有任务进行");

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        notificationBuilder = new NotificationCompat.Builder(this, "my_test")
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setContent(remoteViews)
                .setSmallIcon(R.drawable.blank)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.blank))
                .setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE)
                .setContentIntent(pi);
        startForeground(1, notificationBuilder.build());
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    boolean timing;
    long _begin; // data from intent
    Bitmap bitmap;
    TimingBinder binder = new TimingBinder();
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    class TimingBinder extends Binder{
        public void StartTiming(String color, String icon, String taskName, long begin){
            _begin = begin;
            timing = true;
            bitmap = IconStringList.StringToBitmap(icon, color);
            remoteViews.setImageViewBitmap(R.id.notification_view_icon, bitmap);
            remoteViews.setTextViewText(R.id.notification_view_taskname, taskName);
            remoteViews.setViewVisibility(R.id.notification_view_count, View.VISIBLE);
            remoteViews.setViewVisibility(R.id.notification_view_during, View.GONE);
            remoteViews.setChronometer(R.id.notification_view_count,   SystemClock.elapsedRealtime() - (TimeHelper.GetCurrentSeconds() - _begin) * 1000, null, true);
            notificationManager.notify(1, notificationBuilder.build());
        }

        public void StopTiming(){
            timing = false;
            remoteViews.setViewVisibility(R.id.notification_view_count, View.GONE);
            remoteViews.setImageViewBitmap(R.id.notification_view_icon, null);
            remoteViews.setViewVisibility(R.id.notification_view_during, View.VISIBLE);
            remoteViews.setTextViewText(R.id.notification_view_during, "现在没有任务进行");
            remoteViews.setTextViewText(R.id.notification_view_taskname, "");
            notificationManager.notify(1, notificationBuilder.build());
        }
    }


}