package tw.gov.tra.twtraffic.plugin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import tw.gov.tra.twtraffic.MainActivity;
import tw.gov.tra.twtraffic.R;

/**
 * Created by ChrisTsai on 2018/11/26.
 */

public class NotificationService extends Service {

    private final String TAG_SERVICE = "NotificationService";

    public static final String ACTION_START = "start";

    public static final String ACTION_STOP = "stop";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            switch (action) {
                case ACTION_START:
                    try {
                        startForegroundService(intent.getExtras().getString("title")
                                , intent.getExtras().getString("message")
                                , intent.getExtras().getString("bigText")
                                , intent.getExtras().getString("openUrl"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case ACTION_STOP:
                    stopForegroundService();
                    break;
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopForegroundService();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void startForegroundService(String title, String message, String bigText , String openUrl) {
        String channelId = "default";
        Log.d(TAG_SERVICE, "Start foreground service.");

        NotificationManager mNotificationManager =
                (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "tra_notification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("提醒服務");
            mNotificationManager.createNotificationChannel(channel);
        }


//        String url = "ptrapp://?";
        Intent openurl = new Intent(Intent.ACTION_VIEW);
        openurl.setData(Uri.parse(openUrl));

        // Create notification default intent.
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, openurl, 0);


        // Create notification builder.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
        builder.setContentTitle(title != null ? title : getString(R.string.app_name))
                .setContentText(message != null ? message : "提醒服務執行中..");

//        // Make notification show big text.
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(title != null ? title : getString(R.string.app_name));
        bigTextStyle.bigText(bigText != null ? bigText : (message != null ? message : "提醒服務執行中.."));
        // Set big text style.
        builder.setStyle(bigTextStyle);

        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.drawable.ic_launcher_notification);
        Bitmap largeIconBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon);
        builder.setLargeIcon(largeIconBitmap);
        // Make the notification max priority.
        builder.setPriority(Notification.PRIORITY_MAX);

        builder.setContentIntent(pendingIntent);

        // Build the notification.
        Notification notification = builder.build();

        // Start foreground service.
        startForeground(5566, notification);
    }

    private void stopForegroundService() {
        Log.d(TAG_SERVICE, "Stop foreground service.");

        // Stop foreground service and remove the notification.
        stopForeground(true);

        // Stop the foreground service.
        stopSelf();
    }
}
