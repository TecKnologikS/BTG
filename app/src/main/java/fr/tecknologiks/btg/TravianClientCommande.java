package fr.tecknologiks.btg;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.Map;

import fr.tecknologiks.btg.DAO.CommandeDAO;
import fr.tecknologiks.btg.DAO.CompteDAO;
import fr.tecknologiks.btg.bdd.CommandeContract;
import fr.tecknologiks.btg.bdd.DBHelper;
import fr.tecknologiks.btg.classObject.Commande;
import fr.tecknologiks.btg.classObject.Compte;
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
    private String url;
    private String previous = "FIRSTPAGE";
    private boolean troupage = false;
    private int evasion = 0;
    private int envoi_ress = 0;
    private SharedPreferences prefs;
    public static final String LAUNCHED = "service_lancer";
    public static final String TIME = "service_interval";
    public static ArrayList<Compte> lstComptes = new ArrayList<>();
    public static ArrayList<Commande> lstCommande = new ArrayList<>();
    public static ArrayList<SubCommande> lstAction = new ArrayList<>();
    boolean ressInit = false;
    boolean villageInit = false;
    private Callback callback;

    public interface Callback {
        void onLoginErrorShowError();
    }

    public TravianClientCommande() {
        this.url = "";
    }

    public TravianClientCommande(SharedPreferences _prefs, DBHelper _bdd, Callback callback) {
        this.prefs = _prefs;
        this.bdd = _bdd;
        this.callback = callback;
        Reload();
    }

    public String getUrl() {
        return this.url;
    }

    public boolean hasActionToDo() {
        Log.e("has action to do", "" + (this.lstComptes.size() > 0));
        return (boolean)(this.lstComptes.size() > 0);
    }

    public void Reload() {
        this.lstComptes = CompteDAO.getListComptes(bdd);
        for(int i = lstComptes.size() - 1; i >= 0;  i--) {
            this.lstCommande = CommandeDAO.getCommandeByIdCompte(bdd, lstComptes.get(i).getId());
            for(int j = lstCommande.size() - 1; j >= 0; j--) {
                if (System.currentTimeMillis() < (lstCommande.get(j).getLasttime() + (60000 * lstCommande.get(j).getMinute()))) {
                    lstCommande.remove(j);
                } else {
                    this.lstComptes.get(i).getLstCommande().add(lstCommande.get(j));
                }
            }
            if (lstCommande.size() <= 0)
                lstComptes.remove(i);
        }
        if (this.lstComptes.size() > 0) {
            Log.d("RELOAD" ,  "compte " + lstComptes.get(0).getLogin() + " actions : " + lstCommande.size());
            this.url = this.lstComptes.get(0).getServer();
            this.lstAction = lstComptes.get(0).getLstCommande().get(0).generateSubCommande();
        }
        lstAction.clear();
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
                Log.e("login", "previous page: " + previous + " actual " + urlPage);
                if (previous == Page.LOGIN) {
                    Log.e("login page", "erooroooooorr");
                    this.callback.onLoginErrorShowError();
                } else {
                    Login(view);
                }
                break;
            default:
                DoAction(view);
                break;
        }
        previous = urlPage;
    }



    private void Login(WebView view) {
        Log.e("login", "do login");
        if (lstComptes.size() > 0) {
            Log.e("login", "url : "+ view.getUrl() + " - login : " + this.lstComptes.get(0).getLogin() + " - pwd : " + this.lstComptes.get(0).getPassword());
            view.evaluateJavascript("document.getElementsByName('name')[0].value = '" + this.lstComptes.get(0).getLogin() + "'", null);
            view.evaluateJavascript("document.getElementsByName('password')[0].value = '" + this.lstComptes.get(0).getPassword() + "'", null);
            view.evaluateJavascript("document.getElementsByName('login')[0].submit();", null);
        }
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
        if (lstComptes.size() > 0) {
            if (lstAction.size() > 0) {
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
                if (lstComptes.get(0).getLstCommande().size() > 0) {
                    lstComptes.get(0).getLstCommande().get(0).updateLastTime(this.bdd);
                    lstComptes.get(0).getLstCommande().remove(0);
                    if (lstComptes.get(0).getLstCommande().size() > 0) {
                        if (System.currentTimeMillis() >= (lstComptes.get(0).getLstCommande().get(0).getLasttime() + (60000 * lstComptes.get(0).getLstCommande().get(0).getMinute()))) {
                            lstAction = lstComptes.get(0).getLstCommande().get(0).generateSubCommande();
                            DoAction(view);
                        } else {
                            lstComptes.get(0).getLstCommande().remove(0);
                            DoAction(view);
                        }
                    } else {
                        lstComptes.remove(0);
                        if (lstComptes.size() > 0) {
                            this.lstCommande = CommandeDAO.getCommandeByIdCompte(bdd, lstComptes.get(0).getId());
                            if (lstCommande.size() > 0)
                            {
                                while(lstCommande.size() > 0) {
                                    if (System.currentTimeMillis() >= (lstComptes.get(0).getLstCommande().get(0).getLasttime() + (60000 * lstComptes.get(0).getLstCommande().get(0).getMinute()))) {
                                        lstAction = lstComptes.get(0).getLstCommande().get(0).generateSubCommande();
                                        break;
                                    } else {
                                        lstComptes.get(0).getLstCommande().remove(0);
                                    }
                                }
                                this.url = this.lstComptes.get(0).getServer();
                                if (lstComptes.get(0).getLstCommande().size() > 0)
                                    view.loadUrl(this.url + "/" + Page.LOGIN);
                            }
                        }
                    }
                }
            }
        } else {
            if (!view.getUrl().equals(this.url + "/" + Page.ALLSEE))
                view.loadUrl(this.url + "/" + Page.ALLSEE);
        }
    }
}
