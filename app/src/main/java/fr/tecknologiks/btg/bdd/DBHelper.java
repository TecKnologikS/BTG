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

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CommandeEntry.TABLE_NAME + " (" +
                    CommandeEntry.COL_ID + " INTEGER PRIMARY KEY," +
                    CommandeEntry.COL_ACTION + " INTEGER," +
                    CommandeEntry.COL_INFO_COMP + " TEXT," +
                    CommandeEntry.COL_LAST_TIME + " TEXT," +
                    CommandeEntry.COL_MINUTE + " INTEGER," +
                    CommandeEntry.COL_ON_ATTACK + " INTEGER," +
                    CommandeEntry.COL_VILLAGE + " INTEGER," +
                    CommandeEntry.COL_ACTIF + " INTEGER  )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CommandeEntry.TABLE_NAME;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL("INSERT INTO " + CommandeEntry.TABLE_NAME + " (" + CommandeEntry.COL_ACTION + ", " + CommandeEntry.COL_INFO_COMP + ", " +
                                    CommandeEntry.COL_LAST_TIME + ", " + CommandeEntry.COL_MINUTE + ", " + CommandeEntry.COL_VILLAGE + ", " + CommandeEntry.COL_ON_ATTACK + ", " + CommandeEntry.COL_ACTIF + ") " +
                " VALUES  (1, '99', '0', 15, 101, 0, 1), (1, '685', '0', 10, 101, 0, 1), (4, '6', '0', 1, 101, 0, 1), (3, '1', '0', 1, 101, 0, 1);");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
