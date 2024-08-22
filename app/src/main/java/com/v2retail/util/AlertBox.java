package com.v2retail.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;

import java.io.PrintWriter;
import java.io.StringWriter;

public class AlertBox {
    Context con;

    public AlertBox(Context con) {
        this.con = con;

    }

    public void getBox(String Title, String message) {

        getBox( Title,  message, null);

    }

    public void getBox(String Title, String message, DialogInterface.OnClickListener listener) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(con);

        // Setting Dialog Title
        alertDialog.setTitle(Title);

        // Setting Dialog Message
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        // Setting Icon to Dialog
        // alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", listener);

        // Showing Alert Message
        alertDialog.show();
    }

    public void getBox(String Title, String message, DialogInterface.OnClickListener listenerPositive, DialogInterface.OnClickListener listenerNegative ) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(con);

        // Setting Dialog Title
        alertDialog.setTitle(Title);

        // Setting Dialog Message
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        // Setting Icon to Dialog
        // alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        if(listenerPositive!=null) {
            alertDialog.setPositiveButton("OK", listenerPositive);

        }

        if(listenerNegative!=null) {
            alertDialog.setNegativeButton("Cancel", listenerNegative);
        }

        // Showing Alert Message
        alertDialog.show();
    }

    public void getErrBox(final Exception exception) {

        String ex[] = new String[2];
        ex[0] = exception.getClass().getSimpleName();
        ex[1] = exception.getMessage();

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(con);

        // Setting Dialog Title
        alertDialog.setTitle(ex[0]);

        // Setting Dialog Message
        alertDialog.setMessage(ex[1]);

        // Setting Icon to Dialog
        //  alertDialog.setIcon(R.drawable.a);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", null);
        alertDialog.setNegativeButton("Show Details", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringWriter sw = new StringWriter();
                exception.printStackTrace(new PrintWriter(sw));
                String message = sw.toString();
                //  Toast.makeText(con, message, Toast.LENGTH_LONG).show();
                getBox("Details", message);

            }
        });


        // Showing Alert Message
        alertDialog.show();
    }




    public void getDialogBox(final Activity activity ) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    activity.finish();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };

    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
    builder.setCancelable(false);
    builder.setMessage("Logout! Are you sure?").
    setPositiveButton("Yes",dialogClickListener).

    setNegativeButton("No",dialogClickListener).

    show();
    }
    public void confirmBack(FragmentManager fm, Context con) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        fm.popBackStack();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setCancelable(false);
        builder.setMessage("GO Back! Are you sure?").
                setPositiveButton("Yes",dialogClickListener).
                setNegativeButton("No",dialogClickListener).
                show();
    }
}
