package fr.tecknologiks.btg.classObject;

import android.content.ContentValues;

import java.util.ArrayList;

import fr.tecknologiks.btg.bdd.CommandeContract;
import fr.tecknologiks.btg.bdd.DBHelper;

import static fr.tecknologiks.btg.bdd.CommandeContract.*;

/**
 * Created by robin on 2/27/2017.
 */

public class Commande {

    private int action = 0;
    private String info_comp = "";
    private boolean onAttack = false;
    private int minute = 0;
    private long lasttime = 0;
    private int village = 0; //0 --> Tous
    private int ID = 0;
    private boolean actif = false;
    private ArrayList<String> lstArg = new ArrayList<>();

    public Commande() {     }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getInfo_comp() {
        return info_comp;
    }

    public void setInfo_comp(String info_comp) {
        this.info_comp = info_comp;
    }

    public boolean isOnAttack() {
        return onAttack;
    }

    public void setOnAttack(boolean onAttack) {
        this.onAttack = onAttack;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public long getLasttime() {
        return lasttime;
    }

    public void setLasttime(long lasttime) {
        this.lasttime = lasttime;
    }

    public int getVillage() {
        return village;
    }

    public void setVillage(int village) {
        this.village = village;
    }

    public boolean isActif() {
        return actif;
    }

    public int getActif() {
        if (actif)
            return 1;
        else
            return 0;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public void setActifInt(int actif) {
        if (actif == 1)
            this.actif = true;
        else
            this.actif = false;
    }

    public ArrayList<SubCommande> generateSubCommande() {

        ArrayList<SubCommande> retour = new ArrayList<>();

        switch(this.getAction()) {
            case Action.PILLAGE:
                if (this.village != 0 && !this.info_comp.isEmpty()) {
                    retour.add(new SubCommande(1, Page.PILLAGE + Page.VILLAGE + this.village));
                    retour.add(new SubCommande(2, argToFunction(ListeCommande.PILLAGE_SELECT) + argToFunction(ListeCommande.PILLAGE_CLICK)));
                }
                break;
            case Action.TROUPE_ECURIE:
                if (this.village != 0 && !this.info_comp.isEmpty()) {
                    retour.add(new SubCommande(1, Page.TROUPAGES_ECURIE + Page.VILLAGE + this.village));
                    retour.add(new SubCommande(2, argToFunction(ListeCommande.TROUPAGE) + ListeCommande.TROUPAGE_CLICK));
                }
                break;
            case Action.TROUPE_CASERNE:
                if (this.village != 0 && !this.info_comp.isEmpty()) {
                    retour.add(new SubCommande(1, Page.TROUPAGES_CASERNE + Page.VILLAGE + this.village));
                    retour.add(new SubCommande(2, argToFunction(ListeCommande.TROUPAGE) + ListeCommande.TROUPAGE_CLICK));
                }
                break;
        }


        return retour;
    }

    private String argToFunction(String  commande) {
        if (lstArg.size() > 0) {
            for (int i = 0; i < lstArg.size(); i++) {
                commande = commande.replace("%" + (i+1) + "%", lstArg.get(i));
            }
        } else {
            commande = commande.replace("%1%", this.info_comp);
        }

        return commande;
    }

    public void updateLastTime(DBHelper bdd) {
        ContentValues cv = new ContentValues();
        cv.put(CommandeEntry.COL_LAST_TIME, System.currentTimeMillis() + "");
        bdd.getWritableDatabase().update(CommandeEntry.TABLE_NAME, cv, CommandeEntry.COL_ID + " = ? ", new String[] {this.getID() + ""});
    }

    @Override
    public String toString() {
        return "action: " + this.action + " / " +
                "info_comp: " + this.info_comp + " / " +
                "actif: " + this.actif + " / " +
                "minute: " + this.minute + " / " +
                "lasttime: " + this.lasttime + " / " +
                "village: " + this.village + "; ";
    }
}

/***


 Pillage liste 1
 info comp : ID liste pillage
 onattack false
 action : 2
 minute : 10
 village : id village


 ***/