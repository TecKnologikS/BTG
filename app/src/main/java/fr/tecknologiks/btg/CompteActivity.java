package fr.tecknologiks.btg;

import android.app.AlarmManager;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

import fr.tecknologiks.btg.DAO.CompteDAO;
import fr.tecknologiks.btg.adapter.CompteAdapteur;
import fr.tecknologiks.btg.bdd.DBHelper;
import fr.tecknologiks.btg.classObject.Compte;

public class CompteActivity extends AppCompatActivity {

    ListView lvComptes;
    ArrayList<Compte> lstComptes = new ArrayList<>();
    DBHelper bdd;
    CompteAdapteur adapter;

    @Override
    protected void onResume() {
        super.onResume();
        lstComptes = CompteDAO.getListComptes(bdd);
        adapter = new CompteAdapteur(this, lstComptes);
        lvComptes.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);
        bdd = new DBHelper(getApplicationContext());

        lvComptes = (ListView)findViewById(R.id.lvComptes);


        lstComptes.clear();
        lstComptes = CompteDAO.getListComptes(bdd);
        adapter = new CompteAdapteur(this, lstComptes);
        lvComptes.setAdapter(adapter);
        Log.e("nb compte", "" + lstComptes.size());
        adapter.notifyDataSetChanged();
        Log.e("nb compte child", "" + lvComptes.getChildCount());

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CompteActivity.this, DetailCompteActivity.class).putExtra("ID", -1));
            }
        });

        lvComptes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity((new Intent(CompteActivity.this, DetailCompteActivity.class)).putExtra("ID", lstComptes.get(position).getId()));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
