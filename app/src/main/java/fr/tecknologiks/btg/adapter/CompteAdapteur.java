package fr.tecknologiks.btg.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.tecknologiks.btg.R;
import fr.tecknologiks.btg.classObject.Action;
import fr.tecknologiks.btg.classObject.Compte;

/**
 * Created by robin on 23/09/2016.
 */


public class CompteAdapteur extends BaseAdapter {


    private Context c;
    private ArrayList<Compte> lstcomptes;
    private LayoutInflater inflater;



    public CompteAdapteur(Context context, ArrayList<Compte> _comm) {
        c = context;
        inflater = LayoutInflater.from(context);
        this.lstcomptes = _comm;
    }



    @Override
    public int getCount() {
        return lstcomptes.size();
    }

    @Override
    public Object getItem(int index) {
        return lstcomptes.get(index);
    }

    @Override
    public long getItemId(int index) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_compte, null);
        ((TextView)convertView.findViewById(R.id.tvServer)).setText(lstcomptes.get(position).getServer() + "");
        ((TextView)convertView.findViewById(R.id.tvLogin)).setText(lstcomptes.get(position).getLogin() + "");
        ((TextView)convertView.findViewById(R.id.tvPassword)).setText(lstcomptes.get(position).getPassword() + "");
        return convertView;
    }


}

/*


public class CompteAdapteur extends ArrayAdapter<Compte> {

    private ArrayList<Compte> lstComptes;
    Context mContext;
    private LayoutInflater inflater;

    public CompteAdapteur(ArrayList<Compte> data, Context context) {
        super(context, R.layout.item_compte, data);
        this.lstComptes = data;
        this.mContext=context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_compte, null);
        }
        Log.e("adapter", "passage position " + position);
        ((TextView)convertView.findViewById(R.id.tvServer)).setText(lstComptes.get(position).getServer() + "");
        ((TextView)convertView.findViewById(R.id.tvLogin)).setText(lstComptes.get(position).getLogin() + "");
        ((TextView)convertView.findViewById(R.id.tvPassword)).setText(lstComptes.get(position).getPassword() + "");
        return convertView;
    }
}

 */
