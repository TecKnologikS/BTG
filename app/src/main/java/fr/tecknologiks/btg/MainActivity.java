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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

import fr.tecknologiks.btg.DAO.CommandeDAO;
import fr.tecknologiks.btg.DAO.CompteDAO;
import fr.tecknologiks.btg.adapter.Adapteur;
import fr.tecknologiks.btg.bdd.CommandeContract;
import fr.tecknologiks.btg.bdd.DBHelper;
import fr.tecknologiks.btg.classObject.Commande;
import fr.tecknologiks.btg.classObject.Compte;

public class MainActivity extends AppCompatActivity  implements JSInterface.Callback {
    AlarmManager alarm;
    SharedPreferences prefs ;
    PendingIntent pintent;
    Adapteur a;
    ListView lvCommande;
    ArrayList<Commande> lstCommandes = new ArrayList<>();
    ArrayList<Compte>   lstComptes = new ArrayList<>();
    DBHelper bdd;

    @Override
    protected void onResume() {
        super.onResume();
        lstCommandes.clear();
        lstCommandes.clear();
        getComptes();
        getCommandes();
        a.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        if (!prefs.getString("prefLOGIN", "").isEmpty() && !prefs.getString("prefPWD", "").isEmpty() && !prefs.getString("prefURL", "").isEmpty())
            alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), prefs.getInt("prefMinute", 5) * 60000, pintent);
        getComptes();
        getCommandes();

        a = new Adapteur(this, lstCommandes);
        lvCommande = (ListView)findViewById(R.id.lstAction);
        lvCommande.setAdapter(a);
        lvCommande.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity((new Intent(MainActivity.this, DetailActivity.class)).putExtra("ID", lstCommandes.get(position).getID()));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
            case R.id.action_comptes:
                startActivity(new Intent(MainActivity.this, CompteActivity.class));
                break;
            case R.id.action_stopit:
                stopService(new Intent(MainActivity.this, MyService.class));
                alarm.cancel(pintent);
                break;
            case R.id.action_go:
                startService(new Intent(MainActivity.this, MyService.class));
                alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), prefs.getInt("prefMinute", 5)*60000, pintent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEvasionAdded() {}

    public void getCommandes() {
        for(int i = 0; i < lstComptes.size(); i++) {
            ArrayList<Commande> tmp = CommandeDAO.getAllCommandeByIdCompte(bdd, lstComptes.get(i).getId(), false);
            for (int j = 0; j < tmp.size(); j++) {
                tmp.get(j).setCompte(lstComptes.get(i).getLogin());
            }
            lstCommandes.addAll(tmp);
        }
    }

    public void getComptes() {
        this.lstComptes = CompteDAO.getListComptes(bdd);
    }
}
