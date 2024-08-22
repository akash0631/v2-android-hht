package com.v2retail.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesData {
    Context context;
    SharedPreferences preferences = null;
    SharedPreferences.Editor editor = null;
    AlertBox box;
    public SharedPreferencesData(Context con) {
        context = con;

        box = new AlertBox(context);
        preferences = context.getSharedPreferences("login", 0);
    }

    public String read(String key) {

        String data = null;
        if (key != null || key.length() > 0 || !key.equals("")) {

            data = preferences.getString(key, null);
        } else {
            box.getBox("Alert!", "Empty Key ");
        }
        return data;
    }

    public void write(String key, String value) {
        if (key != null || key.length() > 0 || !key.equals("")) {
            if (value != null || value.length() > 0 || !value.equals("")) {
                editor = preferences.edit();
                editor.putString(key, value);
                editor.commit();
            }
        } else {
            box.getBox("Alert!", "Empty Login Key-Value");
        }
    }

    public void delete(String key) {
        if (key != null || key.length() > 0 || !key.equals("")) {
            editor = preferences.edit();

            editor.remove(key);

            editor.commit();
        } else {
            box.getBox("Alert!", "Empty Key, Can't delete null value ");
        }
    }

    public void clearAll() {
        editor = preferences.edit();

        editor.clear();

        editor.commit();
    }
}
