package fr.tecknologiks.btg;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.Map;

import fr.tecknologiks.btg.bdd.CommandeContract;
import fr.tecknologiks.btg.bdd.DBHelper;
import fr.tecknologiks.btg.classObject.Commande;
import fr.tecknologiks.btg.classObject.Function;
import fr.tecknologiks.btg.classObject.ID;
import fr.tecknologiks.btg.classObject.Page;
import fr.tecknologiks.btg.classObject.SubAction;
import fr.tecknologiks.btg.classObject.SubCommande;
import fr.tecknologiks.btg.classObject.Villages;

import static fr.tecknologiks.btg.bdd.CommandeContract.*;

/**
 * Created by robin on 2/27/2017.
 */

public class TravianClientCommande extends WebViewClient {

    private DBHelper bdd;
    private String user;
    private String mdp;
    private String url;
    private String previous = "FIRSTPAGE";
    private boolean troupage = false;
    private int evasion = 0;
    private int envoi_ress = 0;
    private SharedPreferences prefs;
    public static final String LAUNCHED = "service_lancer";
    public static final String TIME = "service_interval";
    public static ArrayList<Commande> lstCommande = new ArrayList<>();
    public static ArrayList<SubCommande> lstAction = new ArrayList<>();
    boolean ressInit = false;
    boolean villageInit = false;



    public TravianClientCommande() {
        this.user = "";
        this.mdp = "";
        this.url = "";
    }

    public TravianClientCommande(String _user, String _mdp, String _url, SharedPreferences _prefs, DBHelper _bdd) {
        this.user = _user;
        this.mdp = _mdp;
        this.url = _url;
        this.prefs = _prefs;
        this.bdd = _bdd;
        this.lstCommande = getCommande();
        if (lstCommande.size() > 0)
            lstAction = lstCommande.get(0).generateSubCommande();
    }

    public void Reload() {
        this.lstCommande = getCommande();
        if (lstCommande.size() > 0)
            lstAction = lstCommande.get(0).generateSubCommande();
    }

    public ArrayList<Commande> getCommande() {
        ArrayList<Commande> retour = new ArrayList<>();
        String[] projection = {
                CommandeEntry.COL_ID,
                CommandeEntry.COL_ACTION,
                CommandeEntry.COL_VILLAGE,
                CommandeEntry.COL_ON_ATTACK,
                CommandeEntry.COL_MINUTE,
                CommandeEntry.COL_LAST_TIME,
                CommandeEntry.COL_INFO_COMP,
                CommandeEntry.COL_ACTIF
        };
        Cursor cursor = bdd.getReadableDatabase().query(CommandeEntry.TABLE_NAME,
                projection,
                null,
                null, null, null, CommandeEntry.COL_ID + " ASC ");
        while(cursor.moveToNext()) {
            Commande tmp = new Commande();
            tmp.setID(cursor.getInt(cursor.getColumnIndex(CommandeEntry.COL_ID)));
            tmp.setAction(cursor.getInt(cursor.getColumnIndex(CommandeEntry.COL_ACTION)));
            tmp.setMinute(cursor.getInt(cursor.getColumnIndex(CommandeEntry.COL_MINUTE)));
            tmp.setOnAttack(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(CommandeEntry.COL_ON_ATTACK))));
            tmp.setVillage(cursor.getInt(cursor.getColumnIndex(CommandeEntry.COL_VILLAGE)));
            tmp.setInfo_comp(cursor.getString(cursor.getColumnIndex(CommandeEntry.COL_INFO_COMP)));
            tmp.setLasttime(Long.parseLong(cursor.getString(cursor.getColumnIndex(CommandeEntry.COL_LAST_TIME))));
            tmp.setActifInt(cursor.getInt(cursor.getColumnIndex(CommandeEntry.COL_ACTIF)));;
            if (tmp.isActif())
                if ((tmp.getLasttime() + (60000 * tmp.getMinute())) < System.currentTimeMillis())
                    retour.add(tmp);
        }
        cursor.close();

        return retour;
    }

    public void onPageFinished(WebView view, String url) {

        //recup extension url
        String urlPage = "";
        if (url.contains("/")) {
            urlPage = url.replace(this.url + "/", "");
        }
        Log.e("URL", urlPage + " / " + url);
        int tmp = urlPage.indexOf("?");
        Map<String, String> test;
        if (tmp != -1) {
            test = Function.getQueryMap(urlPage.substring(tmp + 1));
            urlPage = urlPage.substring(0, tmp);
            if (test.size() > 0) {
                String param = "";
                urlPage += "?";
                if (test.containsKey("id"))
                    param += "id=" + test.get("id") + "&";
                if (test.containsKey("tt"))
                    param += "tt=" + test.get("tt") + "&";

                if (!param.isEmpty())
                    urlPage += param.substring(0, param.length() - 1);
            }
        }

        switch(urlPage) {
            case Page.DORF1:
                Ressource(view);
                ListeVillage(view);
                DoAction(view);
                //view.loadUrl(this.url + "/" + Page.ALLSEE);
                break;
            case Page.LOGIN:
                Login(view);
                break;
            default:
                DoAction(view);
                break;
        }
        previous = urlPage;
    }



    private void Login(WebView view) {
        if (previous.equals(Page.DEFAULT) || previous.equals(Page.LOGIN))
        {
            //TODO: error
        }

        view.evaluateJavascript("document.getElementsByName('name')[0].value = '" + this.user + "'", null);
        view.evaluateJavascript("document.getElementsByName('password')[0].value = '" + this.mdp + "'", null);
        view.evaluateJavascript("document.getElementsByName('login')[0].submit();", null);
    }

    private void Ressource(WebView view) {
        view.evaluateJavascript("Android.ressource('" + ID.entrepot + "', (function() { return (document.getElementById('" + ID.entrepot + "').innerHTML); })())", null);
        view.evaluateJavascript("Android.ressource('" + ID.silo + "', (function() { return (document.getElementById('" + ID.silo + "').innerHTML); })())", null);
        view.evaluateJavascript("Android.ressource('" + ID.bois + "', (function() { return (document.getElementById('" + ID.bois + "').innerHTML); })())", null);
        view.evaluateJavascript("Android.ressource('" + ID.argile + "', (function() { return (document.getElementById('" + ID.argile + "').innerHTML); })())", null);
        view.evaluateJavascript("Android.ressource('" + ID.fer + "', (function() { return (document.getElementById('" + ID.fer + "').innerHTML); })())", null);
        view.evaluateJavascript("Android.ressource('" + ID.cc + "', (function() { return (document.getElementById('" + ID.cc + "').innerHTML); })())", null);
    }

    private void ListeVillage(WebView view) {
        Villages.getInstance().clear();
        String fonction = "(function() { " +
                " var villages = document.getElementById(\"sidebarBoxVillagelist\").getElementsByTagName(\"li\");" +
                "    var index;" +
                "    for (index = 0; index < villages.length; index++) {" +
                "    Android.village(villages[index].getElementsByClassName(\"name\")[0].innerHTML, villages[index].children[0].href, villages[index].className);" +
                "    } " +
                " })(); Android.endVillage();";

        view.evaluateJavascript(fonction, null);
        //view.evaluateJavascript("Android.endVillage();", null);
    }

    private void DoAction(WebView view) {
        if(lstAction.size() > 0) {
            SubCommande sc = lstAction.get(0);
            lstAction.remove(0);
            switch(sc.getAction()) {
                case SubAction.LOAD_URL:
                    view.loadUrl(this.url + "/" + sc.getInfocomp());
                    Log.e("BTG", "loadurl ==> " + this.url + "/" + sc.getInfocomp());
                    break;
                case SubAction.EXEC_JS:
                    view.evaluateJavascript(sc.getInfocomp(), null);
                    Log.e("BTG", "execjs ==> " + sc.getInfocomp());
                    break;
            }
        } else {
            if (lstCommande.size() > 0) {
                lstCommande.get(0).updateLastTime(this.bdd);
                lstCommande.remove(0);
                if (lstCommande.size() > 0) {
                    if (System.currentTimeMillis() >= (lstCommande.get(0).getLasttime() + (60000 * lstCommande.get(0).getMinute()))) {
                        lstAction = lstCommande.get(0).generateSubCommande();
                        DoAction(view);
                    } else {
                        lstCommande.remove(0);
                        DoAction(view);
                    }
                }
            } else {
                if (!view.getUrl().equals(this.url + "/" + Page.ALLSEE))
                    view.loadUrl(this.url + "/" + Page.ALLSEE);
            }
        }
    }
}
