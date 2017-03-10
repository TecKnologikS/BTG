package fr.tecknologiks.btg.classObject;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

/**
 * Created by robin on 3/10/2017.
 */

public class IntPreference extends EditTextPreference {

    public IntPreference(Context context) {
        super(context);
    }

    public IntPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IntPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected String getPersistedString(String defaultReturnValue) {
        return String.valueOf(getPersistedInt(-1));
    }

    @Override
    protected boolean persistString(String value) {
        return persistInt(Integer.valueOf(value));
    }
}
