package fr.tecknologiks.btg;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

import fr.tecknologiks.btg.bdd.CommandeContract;
import fr.tecknologiks.btg.bdd.DBHelper;
import fr.tecknologiks.btg.classObject.Commande;
import fr.tecknologiks.btg.classObject.Page;

public class MainActivity extends AppCompatActivity  implements JSInterface.Callback {
    AlarmManager alarm;
    SharedPreferences prefs ;
    PendingIntent pintent;
    Adapteur a;
    ListView lvCommande;
    ArrayList<Commande> lstCommandes = new ArrayList<>();
    DBHelper bdd;

    @Override
    protected void onResume() {
        super.onResume();
        lstCommandes.clear();
        getCommande();
        a.notifyDataSetChanged();
    }

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
        bdd = new DBHelper(getApplicationContext());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DetailActivity.class).putExtra("ID", -1));
            }
        });

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        pintent = PendingIntent.getService(this, 0, new Intent(this, MyService.class), 0);
        alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        prefs.edit().putBoolean(TravianClient.LAUNCHED, true).commit();
        if (!prefs.getString("prefLOGIN", "").isEmpty() && !prefs.getString("prefPWD", "").isEmpty() && !prefs.getString("prefURL", "").isEmpty())
            alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), prefs.getInt("prefMinute", 5) * 60000, pintent);
        getCommande();

        a = new Adapteur(this, lstCommandes);
        lvCommande = (ListView)findViewById(R.id.lstAction);
        lvCommande.setAdapter(a);
        lvCommande.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity((new Intent(MainActivity.this, DetailActivity.class)).putExtra("ID", lstCommandes.get(position).getID()));
            }
        });




/*
                DBHelper bdd = new DBHelper(this);
        TravianClientCommande travianClient = new TravianClientCommande(prefs.getString("prefLOGIN", ""), prefs.getString("prefPWD", ""), prefs.getString("prefURL", ""), PreferenceManager.getDefaultSharedPreferences(this), bdd);


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
            startActivity(new Intent(MainActivity.this, SettingActivity.class));
        }

        if (id == R.id.action_stopit) {
            stopService(new Intent(MainActivity.this, MyService.class));
            alarm.cancel(pintent);
        }

        if (id == R.id.action_go) {
            startService(new Intent(MainActivity.this, MyService.class));
            alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), prefs.getInt("prefMinute", 5)*60000, pintent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEvasionAdded() {

    }

    public void getCommande() {
        ArrayList<Commande> retour = new ArrayList<>();
        String[] projection = {
                CommandeContract.CommandeEntry.COL_ID,
                CommandeContract.CommandeEntry.COL_ACTION,
                CommandeContract.CommandeEntry.COL_VILLAGE,
                CommandeContract.CommandeEntry.COL_ON_ATTACK,
                CommandeContract.CommandeEntry.COL_MINUTE,
                CommandeContract.CommandeEntry.COL_LAST_TIME,
                CommandeContract.CommandeEntry.COL_INFO_COMP,
                CommandeContract.CommandeEntry.COL_ACTIF
        };
        Cursor cursor = bdd.getReadableDatabase().query(CommandeContract.CommandeEntry.TABLE_NAME,
                projection,
                " 1=1 ",
                null, null, null, CommandeContract.CommandeEntry.COL_ID + " ASC ");
        while(cursor.moveToNext()) {
            Commande tmp = new Commande();
            tmp.setID(cursor.getInt(cursor.getColumnIndex(CommandeContract.CommandeEntry.COL_ID)));
            tmp.setAction(cursor.getInt(cursor.getColumnIndex(CommandeContract.CommandeEntry.COL_ACTION)));
            tmp.setMinute(cursor.getInt(cursor.getColumnIndex(CommandeContract.CommandeEntry.COL_MINUTE)));
            tmp.setOnAttack(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(CommandeContract.CommandeEntry.COL_ON_ATTACK))));
            tmp.setVillage(cursor.getInt(cursor.getColumnIndex(CommandeContract.CommandeEntry.COL_VILLAGE)));
            tmp.setInfo_comp(cursor.getString(cursor.getColumnIndex(CommandeContract.CommandeEntry.COL_INFO_COMP)));
            tmp.setLasttime(Long.parseLong(cursor.getString(cursor.getColumnIndex(CommandeContract.CommandeEntry.COL_LAST_TIME))));
            tmp.setActifInt(cursor.getInt(cursor.getColumnIndex(CommandeContract.CommandeEntry.COL_ACTIF)));
            retour.add(tmp);
        }
        cursor.close();

        lstCommandes.addAll(retour);
    }
}
