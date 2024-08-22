package com.v2retail.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditTextDate {

    DatePickerDialog date_Dialogue;
    SimpleDateFormat dateFormat;
    Context context;

    public EditTextDate(Context con) {
        context = con;
    }

    public void setDateOnView(final EditText editTextView) {
        dateFormat = getDateFormat();
        Date date = new Date();
        ;
        editTextView.setText(dateFormat.format(date));

    }

    public void setDateOnView(final TextView TextView) {
        dateFormat = getDateFormat();
        Date date = new Date();
        ;
        TextView.setText(dateFormat.format(date));

    }

    public SimpleDateFormat getDateFormat() {
        dateFormat = new SimpleDateFormat("dd\\MM\\yyyy", Locale.US);
        return dateFormat;
    }


}
