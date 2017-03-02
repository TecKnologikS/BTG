package fr.tecknologiks.btg.classObject;

import java.util.Arrays;
import java.util.List;

import static fr.tecknologiks.btg.classObject.Peuple.GAULOIS;
import static fr.tecknologiks.btg.classObject.Peuple.GERMAIN;
import static fr.tecknologiks.btg.classObject.Peuple.ROMAIN;
import static fr.tecknologiks.btg.classObject.Peuple.TOUS;

/**
 * Created by robin on 3/2/2017.
 */

public class Troupe {


    private int peuple =    0;
    private String label =  "";
    private int id =       0;

    public Troupe(int id, int peuple, String label) {
        this.peuple = peuple;
        this.label = label;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int gid) {
        this.id = gid;
    }

    public static final List<Troupe> list = Arrays.asList(
            new Troupe(1,  GAULOIS,         "Phalange"),
            new Troupe(2,  GAULOIS,         "Combattant à l'épée"),
            new Troupe(3,  GAULOIS,         "Éclaireur"),
            new Troupe(4,  GAULOIS,         "Éclair de Toutatis"),
            new Troupe(5,  GAULOIS,         "Cavalier druide"),
            new Troupe(6,  GAULOIS,         "Hédouin"),
            new Troupe(7,  GAULOIS,         "Bélier"),
            new Troupe(8,  GAULOIS,         "Catapulte de Guerre"),
            new Troupe(1,  ROMAIN,          "Légionnaire"),
            new Troupe(2,  ROMAIN,          "Prétorien"),
            new Troupe(3,  ROMAIN,          "Imperian"),
            new Troupe(4,  ROMAIN,          "Equites Legati"),
            new Troupe(5,  ROMAIN,          "Equites Imperatoris"),
            new Troupe(6,  ROMAIN,          "Equites Caesaris"),
            new Troupe(7,  ROMAIN,          "Bélier"),
            new Troupe(8,  ROMAIN,          "Catapulte de feu"),
            new Troupe(1,  GERMAIN,         "Combattant au gourdin"),
            new Troupe(2,  GERMAIN,         "Combattant à la lance"),
            new Troupe(3,  GERMAIN,         "Combattant à la hache"),
            new Troupe(4,  GERMAIN,         "Eclaireur"),
            new Troupe(5,  GERMAIN,         "Paladin"),
            new Troupe(6,  GERMAIN,         "Cavalier Teuton"),
            new Troupe(7,  GERMAIN,         "Bélier"),
            new Troupe(8,  GERMAIN,         "Catapult")
    );


}
