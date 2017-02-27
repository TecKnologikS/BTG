package fr.tecknologiks.btg;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by robin on 11/22/2016.
 */

public class JSInterface {

    public interface Callback {
        void onEvasionAdded();
    }

    private Context context;
    private Callback callback;

    public JSInterface(Context context, Callback call){
        this.context = context;
        this.callback = call;
    }

    @JavascriptInterface
    public void ressource(String _id, String value){
        switch (_id) {
            case ID.entrepot:
                //Ressources.getInstance().setEntrepot(Web.getInteger(value));
                break;
            case ID.silo:
                //Ressources.getInstance().setSilo(Web.getInteger(value));
                break;
            case ID.bois:
                //Ressources.getInstance().setBois(Web.getInteger(value));
                break;
            case ID.argile:
                //Ressources.getInstance().setArgile(Web.getInteger(value));
                break;
            case ID.fer:
                //Ressources.getInstance().setFer(Web.getInteger(value));
                break;
            case ID.cc:
                //Ressources.getInstance().setCc(Web.getInteger(value));
                break;
        }
    }

    @JavascriptInterface
    public void village(String name, String id, String classe) {
        Log.e("Village", "Nom: " + name + ", id: " + id + ", attack: " + classe);
        if (classe.contains("attack"))
            Villages.getInstance().add(new Villages(name, id, true));
        else
            Villages.getInstance().add(new Villages(name, id, false));
    }

    @JavascriptInterface
    public void endVillage() {
        Log.e("FUNCTION", "endVillage");
        String message = "";
        for (int i = 0; i < Villages.getInstance().size(); i++) {
            if (Villages.getInstance().get(i).isAttack()) {
                /*** Evasion bitch ***/
                //Evasion.getInstance().add(new Evasion(Villages.getInstance().get(i), 143, -141));
                //EnvoiRess.getInstance().add(new Evasion(Villages.getInstance().get(i), 135, -158));
                if (!message.isEmpty())
                    message += ", ";
                message += Villages.getInstance().get(i).getName();
            }
        }
        if (!message.isEmpty()) {
            Log.e("ATTACK", "on " + message);
            if ((System.currentTimeMillis() - PreferenceManager.getDefaultSharedPreferences(context).getLong("last", 0)) > 1800000) {
                Function.Notification(context, "ATTACK ON " + message);
                PreferenceManager.getDefaultSharedPreferences(context).edit().putLong("last", System.currentTimeMillis()).commit();
            }
            callback.onEvasionAdded();
        } else {
            Log.e("ATTACK", "NOTHING EVERYTHING IS GOOOOOOD");
        }

    }
}