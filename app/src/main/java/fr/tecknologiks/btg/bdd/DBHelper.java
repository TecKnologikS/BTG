package fr.tecknologiks.btg.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static fr.tecknologiks.btg.bdd.CommandeContract.*;

/**
 * Created by robin on 2/28/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BTG.db";

    private static final String SQL_CREATE_ENTRIES_COMPTES =
            "CREATE TABLE " + CommandeEntry.TABLE_NAME + " (" +
                    CommandeEntry.COL_ID + " INTEGER PRIMARY KEY," +
                    CommandeEntry.COL_ID_COMPTE + " INTEGER," +
                    CommandeEntry.COL_ACTION + " INTEGER," +
                    CommandeEntry.COL_INFO_COMP + " TEXT," +
                    CommandeEntry.COL_INFO_COMP2 + " TEXT," +
                    CommandeEntry.COL_LAST_TIME + " TEXT," +
                    CommandeEntry.COL_MINUTE + " INTEGER," +
                    CommandeEntry.COL_ON_ATTACK + " INTEGER," +
                    CommandeEntry.COL_VILLAGE + " INTEGER," +
                    CommandeEntry.COL_ACTIF + " INTEGER  )";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CompteEntry.TABLE_NAME + " (" +
                    CompteEntry.COL_ID + " INTEGER PRIMARY KEY," +
                    CompteEntry.COL_LOGIN + " TEXT," +
                    CompteEntry.COL_PASSWORD + " TEXT," +
                    CompteEntry.COL_SERVEUR + " TEXT," +
                    CompteEntry.COL_ACTIF + " INTEGER  )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CommandeEntry.TABLE_NAME;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES_COMPTES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
