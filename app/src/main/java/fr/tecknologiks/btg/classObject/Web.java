package fr.tecknologiks.btg.classObject;

/**
 * Created by robin on 11/22/2016.
 */

public class Web {





    public static int getInteger(String value) {
        value = value.replace("\"", "");
        value = value.replace(" ", "");
        return Integer.parseInt(value);
    }
}
