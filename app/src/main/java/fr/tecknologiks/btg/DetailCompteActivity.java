package fr.tecknologiks.btg;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.tecknologiks.btg.DAO.CompteDAO;
import fr.tecknologiks.btg.bdd.CommandeContract;
import fr.tecknologiks.btg.bdd.DBHelper;
import fr.tecknologiks.btg.classObject.Compte;

public class DetailCompteActivity extends AppCompatActivity {
    private int ID = -1;
    Compte c  = new Compte();
    DBHelper bdd;

    EditText edtServer;
    EditText edtLogin;
    EditText edtPassword;
    Button btnValider;
    Button btnSuppr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_compte);
        bdd = new DBHelper(getApplicationContext());

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ID = getIntent().getExtras().getInt("ID");
        edtServer = (EditText)findViewById(R.id.edtServeur);
        edtLogin = (EditText)findViewById(R.id.edtLogin);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        btnValider = (Button)findViewById(R.id.btnValider);
        btnSuppr = (Button)findViewById(R.id.btnSuppr);

        if (ID != -1) {
            c = CompteDAO.getCompteById(bdd, ID);
        }

        edtServer.setText(c.getServer());
        edtLogin.setText(c.getLogin());
        edtPassword.setText(c.getPassword());

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.setServer(edtServer.getText().toString());
                c.setLogin(edtLogin.getText().toString());
                c.setPassword(edtPassword.getText().toString());
                SaveAndQuit();
            }
        });
        btnSuppr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ID != -1) {
                    int count = bdd.getWritableDatabase().delete(CommandeContract.CompteEntry.TABLE_NAME, CommandeContract.CommandeEntry.COL_ID + " = " + Integer.toString(ID), null);
                    Toast.makeText(DetailCompteActivity.this, "resultat update = " + count, Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

    }

    public void SaveAndQuit() {
        if (ID != -1){
            ContentValues cv = new ContentValues();
            cv.put(CommandeContract.CompteEntry.COL_SERVEUR, c.getServer());
            cv.put(CommandeContract.CompteEntry.COL_LOGIN, c.getLogin());
            cv.put(CommandeContract.CompteEntry.COL_PASSWORD, c.getPassword());
            cv.put(CommandeContract.CompteEntry.COL_ACTIF, 1);

            String selection = CommandeContract.CompteEntry.COL_ID + " = ?";
            String[] selectionArgs = { Integer.toString(ID) };

            int count = bdd.getWritableDatabase().update(
                    CommandeContract.CompteEntry.TABLE_NAME,
                    cv,
                    selection,
                    selectionArgs);
            Toast.makeText(DetailCompteActivity.this, "resultat update = " + count, Toast.LENGTH_SHORT).show();
        } else {

            ContentValues cv = new ContentValues();
            cv.put(CommandeContract.CompteEntry.COL_SERVEUR, c.getServer());
            cv.put(CommandeContract.CompteEntry.COL_LOGIN, c.getLogin());
            cv.put(CommandeContract.CompteEntry.COL_PASSWORD, c.getPassword());
            cv.put(CommandeContract.CompteEntry.COL_ACTIF, 1);

            bdd.getWritableDatabase().insert(CommandeContract.CompteEntry.TABLE_NAME, null, cv);
        }
        finish();
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
