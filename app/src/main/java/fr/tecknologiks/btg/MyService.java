package fr.tecknologiks.btg;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.WebView;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        if (prefs.getBoolean(TravianClient.LAUNCHED, false)) {
            Log.e("btg started", "");
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
            prefs.edit().putString("logPillage",  sdf.format(new Date(System.currentTimeMillis())) + " login " + "\n " + prefs.getString("logPillage", "").toString()).commit();
            webView.loadUrl(url + "/" + Page.LOGIN);
            travianClient.Reload();
            webView.setWebViewClient(travianClient);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bdd = new DBHelper(this);

        Log.d("Testing", "Service got created");
        Log.e("SERVICE", "launched");
        prefs = PreferenceManager.getDefaultSharedPreferences(this);


            //travianClient = new TravianClient("Doc Addict", "bogoss1994", url, prefs);
            travianClient = new TravianClientCommande("Doc Addict", "bogoss1994", url, prefs, bdd);
            //final WebView webView = ((WebView) findViewById(R.id.wvTest));
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
        // TODO Auto-generated method stub
        bdd.close();
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
