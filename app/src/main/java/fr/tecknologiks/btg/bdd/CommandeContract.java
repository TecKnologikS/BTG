package fr.tecknologiks.btg.bdd;

import android.provider.BaseColumns;

/**
 * Created by robin on 2/28/2017.
 */

public class CommandeContract {
    private CommandeContract() { }

    public static class CommandeEntry implements BaseColumns {
        public static final String TABLE_NAME = "commande";
        public static final String COL_ID = "id";
        public static final String COL_ID_COMPTE = "id_compte";
        public static final String COL_ACTION = "action";
        public static final String COL_INFO_COMP = "infocomp";
        public static final String COL_INFO_COMP2 = "infocomp2";
        public static final String COL_ON_ATTACK = "onattack";
        public static final String COL_MINUTE = "minute";
        public static final String COL_LAST_TIME = "lasttime";
        public static final String COL_VILLAGE = "village";
        public static final String COL_ACTIF = "actif";

    }

    public static class CompteEntry implements BaseColumns {
        public static final String TABLE_NAME = "comptes";
        public static final String COL_ID = "id";
        public static final String COL_LOGIN = "login";
        public static final String COL_PASSWORD = "pwd";
        public static final String COL_SERVEUR = "url";
        public static final String COL_ACTIF = "actif";
    }
}
