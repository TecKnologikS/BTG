package fr.tecknologiks.btg;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by robin on 11/22/2016.
 */

public class Villages {

    private static ArrayList<Villages> instance;
    public static ArrayList<Villages> getInstance() {
        if (instance == null)
            instance = new ArrayList<Villages>();
        return instance;
    }

    public Villages() {
        this.name = "";
        this.link = "";
        this.attack = false;
    }

    public Villages(String name, String link, boolean attack) {
        Map<String, String> tmp = Function.getQueryMap(link.substring(link.indexOf("?") + 1));

        this.name = name;
        if (tmp.containsKey("newdid"))
            this.link = tmp.get("newdid");
        else
            this.link = "";
        this.attack = attack;
    }

    private String name;
    private String link;
    private boolean attack;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }
}
