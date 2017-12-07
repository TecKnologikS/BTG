package fr.tecknologiks.btg.DAO;

import android.database.Cursor;

import java.util.ArrayList;

import fr.tecknologiks.btg.bdd.CommandeContract;
import fr.tecknologiks.btg.bdd.DBHelper;
import fr.tecknologiks.btg.classObject.Compte;

/**
 * Created by robinpauquet on 07/12/2017.
 */

public class CompteDAO {
    public static ArrayList<Compte> getListComptes(DBHelper bdd) {
        ArrayList<Compte> retour = new ArrayList<>();
        String[] projection = {
                CommandeContract.CompteEntry.COL_ID,
                CommandeContract.CompteEntry.COL_LOGIN,
                CommandeContract.CompteEntry.COL_PASSWORD,
                CommandeContract.CompteEntry.COL_SERVEUR,
                CommandeContract.CompteEntry.COL_ACTIF
        };
        Compte c;
        Cursor cursor = bdd.getReadableDatabase().query(CommandeContract.CompteEntry.TABLE_NAME,
                projection,
                null,
                null, null, null, CommandeContract.CompteEntry.COL_ID + " ASC ");
        while(cursor.moveToNext()) {
            c = new Compte();
            c.setId(cursor.getInt(cursor.getColumnIndex(CommandeContract.CompteEntry.COL_ID)));
            c.setLogin(cursor.getString(cursor.getColumnIndex(CommandeContract.CompteEntry.COL_LOGIN)));
            c.setPassword(cursor.getString(cursor.getColumnIndex(CommandeContract.CompteEntry.COL_PASSWORD)));
            c.setServer(cursor.getString(cursor.getColumnIndex(CommandeContract.CompteEntry.COL_SERVEUR)));
            c.setActif((cursor.getInt(cursor.getColumnIndex(CommandeContract.CompteEntry.COL_ACTIF)) > 0) ? true : false);
            retour.add(c);
        }
        cursor.close();
        return retour;
    }

    public static Compte getCompteById(DBHelper bdd, int id) {
        Compte retour = new Compte();
        String[] projection = {
                CommandeContract.CompteEntry.COL_ID,
                CommandeContract.CompteEntry.COL_LOGIN,
                CommandeContract.CompteEntry.COL_PASSWORD,
                CommandeContract.CompteEntry.COL_SERVEUR,
                CommandeContract.CompteEntry.COL_ACTIF
        };
        Cursor cursor = bdd.getReadableDatabase().query(CommandeContract.CompteEntry.TABLE_NAME,
                projection,
                CommandeContract.CompteEntry.COL_ID + " = " + id,
                null, null, null, CommandeContract.CompteEntry.COL_ID + " ASC ");
        if(cursor.moveToFirst()) {
            retour.setId(cursor.getInt(cursor.getColumnIndex(CommandeContract.CompteEntry.COL_ID)));
            retour.setLogin(cursor.getString(cursor.getColumnIndex(CommandeContract.CompteEntry.COL_LOGIN)));
            retour.setPassword(cursor.getString(cursor.getColumnIndex(CommandeContract.CompteEntry.COL_PASSWORD)));
            retour.setServer(cursor.getString(cursor.getColumnIndex(CommandeContract.CompteEntry.COL_SERVEUR)));
            retour.setActif((cursor.getInt(cursor.getColumnIndex(CommandeContract.CompteEntry.COL_ACTIF)) > 0) ? true : false);
        }
        cursor.close();
        return retour;
    }

    public static String[] getShowedName(ArrayList<Compte> lstComptes) {
        ArrayList<String>  lstComptesNames = new ArrayList<>();

        for (int i = 0; i  < lstComptes.size(); i++) {
            lstComptesNames.add(lstComptes.get(i).getLogin() + " " + lstComptes.get(i).getServer());
        }

        return lstComptesNames.toArray(new String[lstComptesNames.size()]);
    }

    public static int getPositionOfCompte(int idCompte, ArrayList<Compte> lstCompte) {
        int retour = 0;
        for(int i = 0; i < lstCompte.size(); i++) {
            if (lstCompte.get(i).getId() == idCompte)
                retour = i;
        }
        return retour;
    }

}
