package fr.tecknologiks.btg.classObject;

import java.util.ArrayList;

/**
 * Created by robinpauquet on 05/12/2017.
 */

public class Compte {

    private int id;
    private String login;
    private String password;
    private String server;
    private boolean actif;
    private ArrayList<Commande> lstCommande = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public ArrayList<Commande> getLstCommande() {
        return lstCommande;
    }

    public void setLstCommande(ArrayList<Commande> lstCommande) {
        this.lstCommande = lstCommande;
    }
}
