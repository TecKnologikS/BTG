package fr.tecknologiks.btg;

/**
 * Created by robin on 2/27/2017.
 */

public class SubCommande {
    private int action = 0;
    private String infocomp = "";

    public SubCommande(int action, String infocomp) {
        this.action = action;
        this.infocomp = infocomp;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getInfocomp() {
        return infocomp;
    }

    public void setInfocomp(String infocomp) {
        this.infocomp = infocomp;
    }
}
