package fr.tecknologiks.btg;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

import fr.tecknologiks.btg.bdd.DBHelper;
import fr.tecknologiks.btg.classObject.Page;

public class MainActivity extends AppCompatActivity  implements JSInterface.Callback {
    AlarmManager alarm;
    SharedPreferences prefs ;
    PendingIntent pintent;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        prefs.edit().putBoolean(TravianClient.LAUNCHED, false).commit();
        alarm.cancel(pintent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        pintent = PendingIntent.getService(this, 0, new Intent(this, MyService.class), 0);
        alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        prefs.edit().putBoolean(TravianClient.LAUNCHED, true).commit();
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), 7*60000, pintent);
/*
                DBHelper bdd = new DBHelper(this);
        TravianClientCommande travianClient = new TravianClientCommande("Doc Addict", "bogoss1994", "http://ts20.travian.fr", PreferenceManager.getDefaultSharedPreferences(this), bdd);


        WebView webView = ((WebView) findViewById(R.id.wvTest));
        //final WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JSInterface(this, this), "Android");
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //WebView webView = new WebView(this);
        //webView.loadUrl(url + "/" + Page.DORF1);
        webView.loadUrl("http://ts20.travian.fr" + "/" + Page.LOGIN);
        webView.setWebViewClient(travianClient);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEvasionAdded() {

    }
}
