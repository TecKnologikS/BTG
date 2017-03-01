package fr.tecknologiks.btg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.tecknologiks.btg.classObject.Action;
import fr.tecknologiks.btg.classObject.Commande;

/**
 * Created by robin on 23/09/2016.
 */


public class Adapteur extends BaseAdapter {


    private Context c;
    private ArrayList<Commande> commandes;
    private LayoutInflater inflater;



    public Adapteur(Context context, ArrayList<Commande> _comm) {
        c = context;
        inflater = LayoutInflater.from(context);
        this.commandes = _comm;
    }



    @Override
    public int getCount() {
        return commandes.size();
    }

    @Override
    public Object getItem(int index) {
        return commandes.get(index);
    }

    @Override
    public long getItemId(int index) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_commande, null);

        ((TextView)convertView.findViewById(R.id.tvMinute)).setText(commandes.get(position).getMinute() + "");
        String tmp = "";
        switch(commandes.get(position).getAction()) {
            case Action.PILLAGE:
                tmp = "Pillage village " + commandes.get(position).getVillage();
                break;
            case Action.TROUPE_ECURIE:
                tmp = "Troupe écurie village " + commandes.get(position).getVillage();
                break;
            case Action.TROUPE_CASERNE:
                tmp = "Troupe caserne village " + commandes.get(position).getVillage();
                break;
        }
        ((TextView)convertView.findViewById(R.id.tvNom)).setText(tmp);
        return convertView;
    }


}
