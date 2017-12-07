package fr.tecknologiks.btg.DAO;

import android.database.Cursor;

import java.util.ArrayList;

import fr.tecknologiks.btg.bdd.CommandeContract;
import fr.tecknologiks.btg.bdd.DBHelper;
import fr.tecknologiks.btg.classObject.Commande;

/**
 * Created by robinpauquet on 07/12/2017.
 */

public class CommandeDAO {



    public static ArrayList<Commande> getCommandeByIdCompte(DBHelper bdd, int _id) {
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
                CommandeContract.CommandeEntry.COL_ID_COMPTE + " = " + _id,
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
            tmp.setActifInt(cursor.getInt(cursor.getColumnIndex(CommandeContract.CommandeEntry.COL_ACTIF)));;
            if (tmp.isActif())
                if ((tmp.getLasttime() + (60000 * tmp.getMinute())) < System.currentTimeMillis())
                    retour.add(tmp);
        }
        cursor.close();

        return retour;
    }

    public static Commande getCommandeById(DBHelper bdd, int _id) {
        Commande retour = new Commande();
        String[] projection = {
                CommandeContract.CommandeEntry.COL_ID,
                CommandeContract.CommandeEntry.COL_ID_COMPTE,
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
                CommandeContract.CommandeEntry.COL_ID + " = " + _id,
                null, null, null, CommandeContract.CommandeEntry.COL_ID + " ASC ");
        if(cursor.moveToFirst()) {
            retour.setID(cursor.getInt(cursor.getColumnIndex(CommandeContract.CommandeEntry.COL_ID)));
            retour.setIdCompte(cursor.getInt(cursor.getColumnIndex(CommandeContract.CommandeEntry.COL_ID_COMPTE)));
            retour.setAction(cursor.getInt(cursor.getColumnIndex(CommandeContract.CommandeEntry.COL_ACTION)));
            retour.setMinute(cursor.getInt(cursor.getColumnIndex(CommandeContract.CommandeEntry.COL_MINUTE)));
            retour.setOnAttack(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(CommandeContract.CommandeEntry.COL_ON_ATTACK))));
            retour.setVillage(cursor.getInt(cursor.getColumnIndex(CommandeContract.CommandeEntry.COL_VILLAGE)));
            retour.setInfo_comp(cursor.getString(cursor.getColumnIndex(CommandeContract.CommandeEntry.COL_INFO_COMP)));
            retour.setLasttime(Long.parseLong(cursor.getString(cursor.getColumnIndex(CommandeContract.CommandeEntry.COL_LAST_TIME))));
            retour.setActifInt(cursor.getInt(cursor.getColumnIndex(CommandeContract.CommandeEntry.COL_ACTIF)));;
        }
        cursor.close();

        return retour;
    }
}
