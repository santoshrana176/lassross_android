package com.mindiii.lasross.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.mindiii.lasross.R;
import com.mindiii.lasross.app.session.Session;

import org.json.JSONException;
import org.json.JSONObject;

public class Util {

    private Session session;


    public static void parseError(Context context, ANError anError) {

        if (anError.getErrorBody() != null) {

            try {
                JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                String status = jsonObject.getString("status");
                String message = "";
                if (jsonObject.has("message")) message = jsonObject.getString("message");

// {"status":"fail","message":"Invalid token","authToken":"","responseCode":300,"isActive":0}
                if (message.equals("Invalid token")) {
//OutLoud.getDataManager().logout(activity);
                    showSessionCustomAlertt((Activity) context, "Session Expeired");
//showSessionAlert((Activity) context, "Session Expeired", "You have to login again");
                }

            } catch (JSONException e) {
                e.printStackTrace();

                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * for formatter text watcher
     */
    public static String removeNonnumeric(String text) {
        return text.replaceAll("[^\\d]", "");
    }

    private static void showSessionCustomAlertt(final Activity activity, String message) {
        final Dialog dialog = new Dialog(activity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        dialog.setContentView(R.layout.custom_dilog);

        TextView tvMessages = dialog.findViewById(R.id.tvMessages);
        tvMessages.setText(message);
        TextView tvPopupOk = dialog.findViewById(R.id.tvPopupOk);

        tvPopupOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Session.getDataManager().logout(activity);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
