package fr.tecknologiks.btg.classObject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.HashMap;
import java.util.Map;

import fr.tecknologiks.btg.MainActivity;

/**
 * Created by robin on 11/22/2016.
 */

public class Function {

    public static void Notification(Context c, String message) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(c)
                        .setSmallIcon(android.R.drawable.ic_dialog_alert)
                        .setContentTitle("Travian")
                        .setVibrate(new long[]{100, 300, 100, 500, 100, 700, 100, 1000, 200, 2000, 300, 3000})
                        .setContentText(message);
        int NOTIFICATION_ID = 12345;

        Intent targetIntent = new Intent(c, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(c, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager nManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(NOTIFICATION_ID, builder.build());
    }

    public static Map<String, String> getQueryMap(String query)
    {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params)
        {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
    }
}
