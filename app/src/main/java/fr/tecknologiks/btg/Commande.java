package fr.tecknologiks.btg;

import java.util.ArrayList;

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
    private ArrayList<String> lstArg = new ArrayList<>();

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

    public ArrayList<SubCommande> generateSubCommande() {

        ArrayList<SubCommande> retour = new ArrayList<>();

        switch(this.getAction()) {
            case Action.PILLAGE:
                if (this.village != 0 && !this.info_comp.isEmpty()) {
                    retour.add(new SubCommande(1, Page.PILLAGE + Page.VILLAGE + this.village));
                    retour.add(new SubCommande(2, argToFunction(ListeCommande.PILLAGE_SELECT) + argToFunction(ListeCommande.PILLAGE_CLICK)));
                }
                break;
        }


        return retour;
    }

    private String argToFunction(String  commande) {

        for (int i = 0; i < lstArg.size(); i++) {
            commande = commande.replace("%" + (i+1) + "%", lstArg.get(i));
        }

        return commande;
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