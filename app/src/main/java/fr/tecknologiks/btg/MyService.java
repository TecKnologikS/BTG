package fr.tecknologiks.btg;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.webkit.WebView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import fr.tecknologiks.btg.bdd.DBHelper;
import fr.tecknologiks.btg.classObject.Page;

/**
 * Created by robin on 11/22/2016.
 */

public class MyService extends Service implements JSInterface.Callback {

    TravianClientCommande travianClient;
    private String url = "http://ts20.travian.fr";
    WebView webView;
    DBHelper bdd;

    SharedPreferences prefs;

    public void Go() {
        if (!prefs.getString("prefLOGIN", "").isEmpty() &&
                !prefs.getString("prefPWD", "").isEmpty() &&
                !prefs.getString("prefURL", "").isEmpty()) {
            Log.e("BTG", "dual relancé");
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
            prefs.edit().putString("logPillage",  sdf.format(new Date(System.currentTimeMillis())) + " login " + "\n " + prefs.getString("logPillage", "").toString()).commit();
            webView.loadUrl(url + "/" + Page.LOGIN);
            travianClient.Reload();
            webView.setWebViewClient(travianClient);
            showNotification("Le dual est en train de jouer :D", "Last Connexion : " + toMinute() + " ");
            prefs.edit().putLong("LastConnexion", System.currentTimeMillis()).commit();

        }
    }

    private String toMinute() {
        Timestamp stamp = new Timestamp(System.currentTimeMillis());
        Date date = new Date(stamp.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        //sdf.setTimeZone(TimeZone.getTimeZone("+1"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    private void showNotification(String title, String soustext)
    {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(soustext)
                        .setOngoing(true);

        startForeground(1000,mBuilder.build());  // 1000 - is Id for the notification
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bdd = new DBHelper(getApplicationContext());

        Log.e("BTG", "dual lancé");
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        url = prefs.getString("prefURL", "");

            //travianClient = new TravianClient("Doc Addict", "bogoss1994", url, prefs);
            travianClient = new TravianClientCommande(prefs.getString("prefLOGIN", ""), prefs.getString("prefPWD", ""), prefs.getString("prefURL", ""), prefs, bdd);
            //final WebV
        // iew webView = ((WebView) findViewById(R.id.wvTest));
            webView = new WebView(this);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.addJavascriptInterface(new JSInterface(this, this), "Android");
            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            //Toast.makeText(this, "ServiceClass.onCreate()", Toast.LENGTH_LONG).show();

    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onDestroy() {
        Log.e("BTG", "dual stoppé");
        bdd.close();
        stopForeground(true);
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        //Toast.makeText(this, "ServiceClass.onStart()", Toast.LENGTH_LONG).show();
        Log.d("Testing", "Service got started");
        Go();
    }

    @Override
    public void onEvasionAdded() {
        Log.e("EVASION", "on MainActivity");
        Handler mainHandler = new Handler(this.getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
               // if(Evasion.getInstance().size() > 0)
               //     webView.loadUrl(url + "/" + Page.ENVOI_TROUPES + "&newdid=" + Evasion.getInstance().get(0).getFrom().getLink());
            }
        };
        mainHandler.post(myRunnable);
    }
}
