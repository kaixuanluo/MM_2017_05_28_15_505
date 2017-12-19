package com.example.a90678.lkx_common_17_05_17_16_45.common.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;

import com.example.a90678.lkx_common_17_05_17_16_45.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 90678 on 2017/6/20.
 */

public class NotificationUtil {

    Activity mActivity;
    public NotificationUtil(Activity activity) {
        mActivity = activity;
        mNotificationManager = (NotificationManager) activity.getSystemService(
                Context.NOTIFICATION_SERVICE);
    }

    NotificationManager mNotificationManager;

    public NotificationManager getmNotificationManager() {
        return mNotificationManager;
    }

    public void setmNotificationManager(NotificationManager mNotificationManager) {
        this.mNotificationManager = mNotificationManager;
    }

    protected static final String ACTION_NOTIFICATION_DELETE
            = "com.example.android.activenotifications.delete";

    /**
     * The request code can be any number as long as it doesn't match another request code used
     * in the same app.
     */
    private static final int REQUEST_CODE = 2323;

    private static final String TAG = "ActiveNotificationsFragment";

    private static final String NOTIFICATION_GROUP =
            "com.example.android.activenotifications.notification_type";

    private static final int NOTIFICATION_GROUP_SUMMARY_ID = 1;

    // Every notification needs a unique ID otherwise the previous one would be overwritten. This
    // variable is incremented when used.
    private static int sNotificationId = NOTIFICATION_GROUP_SUMMARY_ID + 1;

    private PendingIntent mDeletePendingIntent;

    public interface NotificationFileDownloadProgressListener {
        void progress(int max, int progress);
        void finish ();
    }

    int currentNotificationId;

    public int getCurrentNotificationId() {
        return currentNotificationId;
    }

    public void setCurrentNotificationId(int currentNotificationId) {
        this.currentNotificationId = currentNotificationId;
    }

    private Map<Integer, NotificationCompat.Builder> builderMap;

    /**
     * Adds a new {@link Notification} with sample data and sends it to the system.
     * Then updates the current number of displayed notifications for this application and
     * creates a notification summary if more than one notification exists.
     */
    public  Map<Integer, NotificationCompat.Builder> addNotificationAndUpdateSummaries(String title, String content) {
        // [BEGIN create_notification]
        // Create a Notification and notify the system.

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(mActivity)
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setProgress(100, 0, false)
                .setDeleteIntent(mDeletePendingIntent)
                .setGroup(NOTIFICATION_GROUP);

        final Notification notification = builder.build();
        int newNotificationId = getNewNotificationId();
        currentNotificationId = newNotificationId;
        mNotificationManager.notify(newNotificationId, notification);
        // [END create_notification]
        Log.i(TAG, "Add a notification");

        builderMap = new HashMap<>();
        builderMap.put(newNotificationId, builder);

        updateNotificationSummary();
        updateNumberOfNotifications();
        return builderMap;
    }

    /**
     * Adds/updates/removes the notification summary as necessary.
     */
    protected void updateNotificationSummary() {
        int numberOfNotifications = getNumberOfNotifications();

        if (numberOfNotifications > 1) {
            // Add/update the notification summary.
            String notificationContent = "通知条数 "+
                    numberOfNotifications;
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(mActivity)
                    .setSmallIcon(R.drawable.app_icon)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .setSummaryText(notificationContent))
                    .setGroup(NOTIFICATION_GROUP)
                    .setGroupSummary(true);
            final Notification notification = builder.build();
            mNotificationManager.notify(NOTIFICATION_GROUP_SUMMARY_ID, notification);
        } else {
            // Remove the notification summary.
            mNotificationManager.cancel(NOTIFICATION_GROUP_SUMMARY_ID);
        }
    }

    /**
     * Requests the current number of notifications from the {@link NotificationManager} and
     * display them to the user.
     */
    protected void updateNumberOfNotifications() {
        final int numberOfNotifications = getNumberOfNotifications();
        Log.i(TAG, "通知条数 "+numberOfNotifications);
    }

    /**
     * Retrieves a unique notification ID.
     */
    public int getNewNotificationId() {
        int notificationId = sNotificationId++;

        // Unlikely in the sample, but the int will overflow if used enough so we skip the summary
        // ID. Most apps will prefer a more deterministic way of identifying an ID such as hashing
        // the content of the notification.
        if (notificationId == NOTIFICATION_GROUP_SUMMARY_ID) {
            notificationId = sNotificationId++;
        }
        return notificationId;
    }

    private int getNumberOfNotifications() {
        // [BEGIN get_active_notifications]
        // Query the currently displayed notifications.
        StatusBarNotification[] activeNotifications = new StatusBarNotification[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            activeNotifications = mNotificationManager
                    .getActiveNotifications();
        }
        // [END get_active_notifications]

        // Since the notifications might include a summary notification remove it from the count if
        // it is present.
        for (StatusBarNotification notification : activeNotifications) {
            if (notification.getId() == NOTIFICATION_GROUP_SUMMARY_ID) {
                return activeNotifications.length - 1;
            }
        }
        return activeNotifications.length;
    }
}
