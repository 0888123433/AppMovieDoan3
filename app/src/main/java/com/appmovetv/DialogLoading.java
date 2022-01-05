package com.appmovetv;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class DialogLoading {
     private Activity activity;
     private Dialog dialog;

    public DialogLoading(Activity activity) {
        this.activity = activity;
    }

    public void show(){
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(true);
        if(dialog.getWindow() != null){
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.show();
    }

    public void dismissDialog(){dialog.dismiss();}
}
