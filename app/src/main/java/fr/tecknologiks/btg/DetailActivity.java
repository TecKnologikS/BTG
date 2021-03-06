package fr.tecknologiks.btg;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import fr.tecknologiks.btg.DAO.CommandeDAO;
import fr.tecknologiks.btg.DAO.CompteDAO;
import fr.tecknologiks.btg.bdd.DBHelper;
import fr.tecknologiks.btg.classObject.Commande;
import fr.tecknologiks.btg.classObject.Compte;

import static fr.tecknologiks.btg.bdd.CommandeContract.CommandeEntry;

public class DetailActivity extends AppCompatActivity {

    DBHelper bdd;
    Commande c;
    Spinner  spnAction;
    Spinner  spnCompte;
    EditText edtIDVillage;
    EditText edtInfoComp;
    EditText edtTime;
    Switch   swActif;
    ArrayList<Compte> lstComptes = new ArrayList<>();
    private int ID = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bdd = new DBHelper(getApplicationContext());

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spnAction = (Spinner) findViewById(R.id.spnAction);
        spnCompte = (Spinner) findViewById(R.id.spnCompte);
        edtIDVillage = (EditText) findViewById(R.id.edtIDVillage);
        edtInfoComp = (EditText) findViewById(R.id.edtInfoComp);
        edtTime = (EditText) findViewById(R.id.edtTime);
        swActif = (Switch) findViewById(R.id.swActif);
        spnAction = (Spinner) findViewById(R.id.spnAction);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                                                                             R.array.action, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAction.setAdapter(adapter);

        lstComptes = CompteDAO.getListComptes(bdd);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CompteDAO.getShowedName(lstComptes));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spnCompte.setAdapter(spinnerArrayAdapter);

        ID = getIntent().getExtras().getInt("ID");

        if (ID != -1) {
            c = CommandeDAO.getCommandeById(bdd, ID);
            if (c != null) {
                spnAction.setSelection(c.getAction() - 1);
                spnCompte.setSelection(CompteDAO.getPositionOfCompte(c.getIdCompte(), lstComptes));
                edtIDVillage.setText(Integer.toString(c.getVillage()));
                edtInfoComp.setText(c.getInfo_comp());
                edtTime.setText(Integer.toString(c.getMinute()));
                swActif.setChecked(c.isActif());
                Log.e("Commande", c.toString());
            }
        } else {
            c = new Commande();
        }


        ((Button) findViewById(R.id.btnSave)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtIDVillage.getText().toString().isEmpty())
                    edtIDVillage.setText("0");
                if (edtTime.getText().toString().isEmpty())
                    edtTime.setText("0");
                c.setAction(spnAction.getSelectedItemPosition() + 1);
                c.setIdCompte(lstComptes.get(spnCompte.getSelectedItemPosition()).getId());
                c.setVillage(Integer.parseInt(edtIDVillage.getText().toString()));
                c.setMinute(Integer.parseInt(edtTime.getText().toString()));
                c.setInfo_comp(edtInfoComp.getText().toString());
                c.setActif(swActif.isChecked());

                SaveAndQuit();
            }

        });

        ((Button) findViewById(R.id.btnSuppr)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ID != -1) {
                    int count = bdd.getWritableDatabase().delete(CommandeEntry.TABLE_NAME, CommandeEntry.COL_ID + " = " + Integer.toString(ID), null);
                    Toast.makeText(DetailActivity.this, "resultat update = " + count, Toast.LENGTH_SHORT).show();
                    if (count == 1)
                        finish();
                }
            }

        });

        edtInfoComp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //TODO: aide to choosez
                return false;
            }
        });
    }

    public void SaveAndQuit() {
        if (ID != -1) {
            ContentValues cv = new ContentValues();
            cv.put(CommandeEntry.COL_ACTION, c.getAction());
            cv.put(CommandeEntry.COL_VILLAGE, c.getVillage());
            cv.put(CommandeEntry.COL_ID_COMPTE, c.getIdCompte());
            cv.put(CommandeEntry.COL_INFO_COMP, c.getInfo_comp());
            cv.put(CommandeEntry.COL_MINUTE, c.getMinute());
            cv.put(CommandeEntry.COL_ACTIF, c.getActif());

            String   selection     = CommandeEntry.COL_ID + " = ?";
            String[] selectionArgs = {Integer.toString(ID)};

            int count = bdd.getWritableDatabase().update(
                    CommandeEntry.TABLE_NAME,
                    cv,
                    selection,
                    selectionArgs);
            Toast.makeText(DetailActivity.this, "resultat update = " + count, Toast.LENGTH_SHORT).show();
        } else {

            ContentValues cv = new ContentValues();
            cv.put(CommandeEntry.COL_ID_COMPTE, c.getIdCompte());
            cv.put(CommandeEntry.COL_ACTION, c.getAction());
            cv.put(CommandeEntry.COL_ON_ATTACK, 0);
            cv.put(CommandeEntry.COL_LAST_TIME, 0);
            cv.put(CommandeEntry.COL_VILLAGE, c.getVillage());
            cv.put(CommandeEntry.COL_INFO_COMP, c.getInfo_comp());
            cv.put(CommandeEntry.COL_MINUTE, c.getMinute());
            cv.put(CommandeEntry.COL_ACTIF, c.getActif());

            bdd.getWritableDatabase().insert(CommandeEntry.TABLE_NAME, null, cv);
        }
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
