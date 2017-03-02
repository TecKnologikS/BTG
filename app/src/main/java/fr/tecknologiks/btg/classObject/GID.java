package fr.tecknologiks.btg.classObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static fr.tecknologiks.btg.classObject.Peuple.*;

/**
 * Created by robin on 3/2/2017.
 */

public class GID {

    private int peuple =    0;
    private String label =  "";
    private int gid =       0;

    public GID(int gid, int peuple, String label) {
        this.peuple = peuple;
        this.label = label;
        this.gid = gid;
    }

    public int getPeuple() {
        return peuple;
    }

    public void setPeuple(int peuple) {
        this.peuple = peuple;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public static final List<GID> list = Arrays.asList(
            new GID(1,  TOUS,       "Bûcheron"),
            new GID(2,  TOUS,       "Carrière d'Argile"),
            new GID(3,  TOUS,       "Mine de fer"),
            new GID(4,  TOUS,       "Champ de céréales"),
            new GID(5,  TOUS,       "Scierie"),
            new GID(6,  TOUS,       "Usine de poterie"),
            new GID(7,  TOUS,       "Fonderie"),
            new GID(8,  TOUS,       "Moulin"),
            new GID(9,  TOUS,       "Boulangerie"),
            new GID(10, TOUS,       "Dépot"),
            new GID(11, TOUS,       "Silo"),
            new GID(13, TOUS,       "Forge"),
            new GID(14, TOUS,       "Place du tournoi"),
            new GID(15, TOUS,       "Bâtiment principal"),
            new GID(16, TOUS,       "Place de rassemblement"),
            new GID(17, TOUS,       "Place du marché"),
            new GID(18, TOUS,       "Ambassade"),
            new GID(19, TOUS,       "Caserne"),
            new GID(20, TOUS,       "Ecurie"),
            new GID(21, TOUS,       "Atelier"),
            new GID(22, TOUS,       "Academie"),
            new GID(23, TOUS,       "Cachette"),
            new GID(24, TOUS,       "Hôtel de ville"),
            new GID(25, TOUS,       "Résidence"),
            new GID(26, TOUS,       "Palais"),
            new GID(27, TOUS,       "Chambre du trésor"),
            new GID(28, TOUS,       "Comptoir de commerce"),
            new GID(29, TOUS,       "Grande caserne"),
            new GID(30, TOUS,       "Grande écurie"),
            new GID(31, ROMAIN,     "Mur d'enceinte"),
            new GID(32, GERMAIN,    "Mur de terre"),
            new GID(33, GAULOIS,    "Palissade"),
            new GID(34, TOUS,       "Tailleur de pierre"),
            new GID(35, GERMAIN,    "Brasserie"),
            new GID(36, GAULOIS,    "Fabricant de pièges"),
            new GID(37, TOUS,       "Manoir du héros"),
            new GID(38, TOUS,       "Grand dépôt"),
            new GID(39, TOUS,       "Grand silo"),
            new GID(40, TOUS,       "Merveille du monde"),
            new GID(41, ROMAIN,     "Abreuvoir")
    );


}
