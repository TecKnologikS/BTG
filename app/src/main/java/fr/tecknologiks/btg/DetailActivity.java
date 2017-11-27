package fr.tecknologiks.btg;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import fr.tecknologiks.btg.bdd.CommandeContract;
import fr.tecknologiks.btg.bdd.DBHelper;
import fr.tecknologiks.btg.classObject.Commande;

import static fr.tecknologiks.btg.bdd.CommandeContract.*;

public class DetailActivity extends AppCompatActivity {

    private int ID = -1;
    DBHelper bdd;
    Commande c;
    Spinner spnAction;
    EditText edtIDVillage;
    EditText edtInfoComp;
    EditText edtTime;
    Switch swActif;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bdd = new DBHelper(getApplicationContext());

        spnAction =         (Spinner)   findViewById(R.id.spnAction);
        edtIDVillage =      (EditText)  findViewById(R.id.edtIDVillage);
        edtInfoComp =       (EditText)  findViewById(R.id.edtInfoComp);
        edtTime =           (EditText)  findViewById(R.id.edtTime);
        swActif =           (Switch)    findViewById(R.id.swActif);
        spnAction =         (Spinner)   findViewById(R.id.spnAction);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.action, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAction.setAdapter(adapter);

        ID = getIntent().getExtras().getInt("ID");

        if (ID != -1) {
            ArrayList<Commande> retour = new ArrayList<>();
            String[] projection = {
                    CommandeEntry.COL_ID,
                    CommandeEntry.COL_ACTION,
                    CommandeEntry.COL_VILLAGE,
                    CommandeEntry.COL_ON_ATTACK,
                    CommandeEntry.COL_MINUTE,
                    CommandeEntry.COL_LAST_TIME,
                    CommandeEntry.COL_INFO_COMP,
                    CommandeEntry.COL_ACTIF
            };

            String whereClause = " " + CommandeEntry.COL_ID + " = ? ";
            String[] whereArgs = new String[] {
                    "" + ID + ""
            };

            Cursor cursor = bdd.getReadableDatabase().query(CommandeEntry.TABLE_NAME,
                    projection,
                    null,
                    null, null, null, CommandeEntry.COL_ID + " ASC ");
            while(cursor.moveToNext()) {
                if (cursor.getInt(cursor.getColumnIndex(CommandeEntry.COL_ID)) == ID) {
                    c = new Commande();
                    c.setID(cursor.getInt(cursor.getColumnIndex(CommandeEntry.COL_ID)));
                    c.setAction(cursor.getInt(cursor.getColumnIndex(CommandeEntry.COL_ACTION)));
                    c.setMinute(cursor.getInt(cursor.getColumnIndex(CommandeEntry.COL_MINUTE)));
                    c.setOnAttack(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(CommandeEntry.COL_ON_ATTACK))));
                    c.setVillage(cursor.getInt(cursor.getColumnIndex(CommandeEntry.COL_VILLAGE)));
                    c.setInfo_comp(cursor.getString(cursor.getColumnIndex(CommandeEntry.COL_INFO_COMP)));
                    c.setLasttime(Long.parseLong(cursor.getString(cursor.getColumnIndex(CommandeEntry.COL_LAST_TIME))));
                    c.setActifInt(cursor.getInt(cursor.getColumnIndex(CommandeEntry.COL_ACTIF)));
                    break;
                }
            }
            cursor.close();
            if (c != null) {
                spnAction.setSelection(c.getAction() - 1);
                edtIDVillage.setText(Integer.toString(c.getVillage()));
                edtInfoComp.setText(c.getInfo_comp());
                edtTime.setText(Integer.toString(c.getMinute()));
                swActif.setChecked(c.isActif());
                Log.e("Commande", c.toString());
            }
        } else {
            c = new Commande();
        }


        ((Button)findViewById(R.id.btnSave)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtIDVillage.getText().toString().isEmpty())
                    edtIDVillage.setText("0");
                if (edtTime.getText().toString().isEmpty())
                    edtTime.setText("0");
                c.setAction(spnAction.getSelectedItemPosition() + 1);
                c.setVillage(Integer.parseInt(edtIDVillage.getText().toString()));
                c.setMinute(Integer.parseInt(edtTime.getText().toString()));
                c.setInfo_comp(edtInfoComp.getText().toString());
                c.setActif(swActif.isChecked());

                SaveAndQuit();
            }

        });

        ((Button)findViewById(R.id.btnSuppr)).setOnClickListener(new View.OnClickListener() {
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
        if (ID != -1){
            ContentValues cv = new ContentValues();
            cv.put(CommandeEntry.COL_VILLAGE, c.getVillage());
            cv.put(CommandeEntry.COL_INFO_COMP, c.getInfo_comp());
            cv.put(CommandeEntry.COL_MINUTE, c.getMinute());
            cv.put(CommandeEntry.COL_ACTIF, c.getActif());

            String selection = CommandeEntry.COL_ID + " = ?";
            String[] selectionArgs = { Integer.toString(ID) };

            int count = bdd.getWritableDatabase().update(
                    CommandeEntry.TABLE_NAME,
                    cv,
                    selection,
                    selectionArgs);
            Toast.makeText(DetailActivity.this, "resultat update = " + count, Toast.LENGTH_SHORT).show();
        } else {

            ContentValues cv = new ContentValues();
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
}
