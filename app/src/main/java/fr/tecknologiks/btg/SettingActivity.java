package fr.tecknologiks.btg;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by robin on 3/1/2017.
 */

public class SettingActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

    }
}