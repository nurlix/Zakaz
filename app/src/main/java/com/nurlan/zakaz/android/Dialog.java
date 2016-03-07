package com.nurlan.zakaz.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


/**
 * Created by NURLAN on 01.08.2015.
 */

public class Dialog {

    private Context context;
    private boolean result;

    public Dialog(Context context, String title, String message, Integer dlg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);

        if(dlg == 1) { //OK
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result = true;
                    dialog.dismiss();
                }
            });
        }

        if(dlg == 2) { //YesNo
            builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result = true;
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result = false;
                    dialog.dismiss();
                }
            });
        }
        if(dlg == 3) { //Close
            builder.setNeutralButton("Закрыть", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public boolean isResult() {
        return result;
    }
}
