package fr.tecknologiks.btg;

import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import fr.tecknologiks.btg.classObject.Function;
import fr.tecknologiks.btg.classObject.ID;
import fr.tecknologiks.btg.classObject.Page;
import fr.tecknologiks.btg.classObject.SubCommande;
import fr.tecknologiks.btg.classObject.Villages;

/**
 * Created by robin on 11/22/2016.
 */

public class TravianClient extends WebViewClient{

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
    public static ArrayList<SubCommande> lstAction = new ArrayList<>();



    public TravianClient() {
        this.user = "";
        this.mdp = "";
        this.url = "";
    }

    public TravianClient(String _user, String _mdp, String _url, SharedPreferences _prefs) {
        this.user = _user;
        this.mdp = _mdp;
        this.url = _url;
        this.prefs = _prefs;
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
                view.loadUrl(this.url + "/" + Page.PILLAGE_DIRECT);
                //TODO: lancer pillage document.getElementById('raidListMarkAll4716').click()
                break;
            case Page.DEFAULT:
            case Page.LOGIN:
                Login(view);
                break;
            case Page.ENVOI_TROUPES:
              //  Evasion(view);
                break;
            case Page.SITUA_TROUPES:
                /*** Si encore a evader, reload url ***/
                evasion = 0;
              /*  if (Evasion.getInstance().size() > 0)
                    view.loadUrl(url + "/" + Page.ENVOI_TROUPES + "&newdid=" + Evasion.getInstance().get(0).getFrom().getLink());
                else if (EnvoiRess.getInstance().size() > 0)
                    view.loadUrl(url + "/" + Page.ENVOI_RESS + "&newdid=" + EnvoiRess.getInstance().get(0).getFrom().getLink());
                else
                    view.loadUrl(url + "/" + Page.DORF1);*/
                break;
            case Page.ENVOI_RESS:
                //EvasionRessource(view);
                break;
            case Page.PILLAGE:
            case Page.PILLAGE_DIRECT:
            case Page.PILLAGE2:
                Pillage(view);
                break;
            case Page.TROUPAGES:
            //case Page.TROUPAGES2:
                Troupages(view);
                break;
            default:
                Login(view);
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
/*
    private void Evasion(WebView view) {
        //TODO: find next village < 1h sans attaques
        switch(evasion) {
            case 0:
                if (Evasion.getInstance().size() > 0) {
                    /*** Mettre troupe + coordonnées ***//*
                    view.evaluateJavascript("document.snd.t1.value=9999", null);
                    view.evaluateJavascript("document.snd.t2.value=9999", null);
                    view.evaluateJavascript("document.snd.t3.value=9999", null);
                    view.evaluateJavascript("document.snd.t4.value=9999", null);
                    view.evaluateJavascript("document.snd.t5.value=9999", null);
                    view.evaluateJavascript("document.snd.t6.value=9999", null);
                    view.evaluateJavascript("document.snd.t7.value=9999", null);
                    view.evaluateJavascript("document.snd.t8.value=9999", null);
                    view.evaluateJavascript("document.snd.t9.value=9999", null);
                    view.evaluateJavascript("document.snd.t10.value=9999", null);
                    view.evaluateJavascript("document.snd.c.value=2", null); //Mode assistance
                    view.evaluateJavascript("document.snd.x.value=" + Evasion.getInstance().get(0).getToX(), null); //X
                    view.evaluateJavascript("document.snd.y.value=" + Evasion.getInstance().get(0).getToY(), null); //Y
                    view.evaluateJavascript("document.snd.s1.click()", null); //Valider envoi
                    evasion++;
                } else {
                    view.loadUrl(url + "/" + Page.DORF1);
                }
                break;
            case 1:
                /*** Valider ***//*
                Evasion.getInstance().remove(0);
                evasion++;
                view.evaluateJavascript("document.getElementById('btn_ok').click();", null);
                break;
        }
    }*/
/*
    private void EvasionRessource(WebView view) {
        Log.e("Evasion", "Ressources");
        switch(envoi_ress) {
            case 0:
                if (EnvoiRess.getInstance().size() > 0) {
                    envoi_ress++;
                    for (int i = 0; i < 20; i++) {
                        view.evaluateJavascript("marketPlace.addRessources(1);marketPlace.addRessources(2);marketPlace.addRessources(3);marketPlace.addRessources(4);", null);
                    }
                    view.evaluateJavascript("document.snd.x.value=" + EnvoiRess.getInstance().get(0).getToX(), null); //X
                    view.evaluateJavascript("document.snd.y.value=" + EnvoiRess.getInstance().get(0).getToY(), null); //Y
                    view.evaluateJavascript("document.snd.enabledButton.click()", null);
                    Log.e("Envoi ress", "preparation");
                } else {
                    view.loadUrl(url + "/" + Page.DORF1);
                }
                break;
            case 1:
                envoi_ress++;
                EnvoiRess.getInstance().remove(0);
                view.evaluateJavascript("document.snd.enabledButton.click()", null);
                Log.e("Envoi ress", "envoi");
                break;
            case 2:
                Log.e("Envoi ress", "verif ");
                envoi_ress = 0;
                if (EnvoiRess.getInstance().size() > 0)
                    view.loadUrl(url + "/" + Page.ENVOI_RESS + "&newdid=" + EnvoiRess.getInstance().get(0).getFrom().getLink());
                else if (Evasion.getInstance().size() > 0)
                    view.loadUrl(url + "/" + Page.ENVOI_TROUPES + "&newdid=" + Evasion.getInstance().get(0).getFrom().getLink());
                else
                    view.loadUrl(url + "/" + Page.DORF1);
                break;
        }
    }
*/

    private void Troupages(WebView view) {
        if (troupage) {
        Log.e("on troupes", "on essaye");
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        view.evaluateJavascript("document.getElementsByClassName('action first')[0].children[1].children[5].click();", null);
        view.evaluateJavascript("document.snd.s1.click();", null);
        prefs.edit().putString("logPillage",  sdf.format(new Date(System.currentTimeMillis())) + " troupage " + "\n " + prefs.getString("logPillage", "").toString()).commit();
            troupage = false;
        }
    }

    private void Pillage(WebView view) {
        Log.e("on pille", "on essaye");
        Random rand = new Random();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        if ((prefs.getLong("timeList1", 0) + 300000) < System.currentTimeMillis()) {
            view.evaluateJavascript("Travian.Game.RaidList.markAllSlotsOfAListForRaid(99, true);", null);
            view.evaluateJavascript("document.getElementById('list99').children[0].submit();", null);
            prefs.edit().putLong("timeList1", System.currentTimeMillis() + rand.nextInt(60000)).commit();
            prefs.edit().putString("logPillage",  sdf.format(new Date(System.currentTimeMillis())) + " liste 1 " + "\n " + prefs.getString("logPillage", "").toString()).commit();
            System.err.println("Pillage: liste 1");
            troupage = true;
        } else {

            view.loadUrl(this.url + "/" + Page.TROUPAGES);
            /*if ((prefs.getLong("timeList2", 0) + 2700000) < System.currentTimeMillis()) {
                view.evaluateJavascript("Travian.Game.RaidList.toggleList(4444);", null);
                view.evaluateJavascript("Travian.Game.RaidList.markAllSlotsOfAListForRaid(4444, true);", null);
                view.evaluateJavascript("document.getElementById('list4444').children[0].submit()", null);
                prefs.edit().putLong("timeList2", System.currentTimeMillis() + rand.nextInt(600000)).commit();
                prefs.edit().putString("logPillage",  sdf.format(new Date(System.currentTimeMillis())) + " liste 2  " + "\n " + prefs.getString("logPillage", "").toString()).commit();
                System.err.println("Pillage: liste 2");
            }*/
        }
    }



    }
