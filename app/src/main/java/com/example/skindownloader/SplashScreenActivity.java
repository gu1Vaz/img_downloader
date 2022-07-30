package com.example.skindownloader;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class SplashScreenActivity extends AppCompatActivity {

    String url = "https://skindonwloader.online/version";
    String version = "1.0";
    String link = "https://play.google.com/store/apps/details?id=zorya.software.skindownloader";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        StringRequest req = new StringRequest(Request.Method.GET,
                url,
                this::verify,
                error -> inicia()
        );
        RequestQueue Queue = Volley.newRequestQueue(this);
        Queue.add(req);

    }
    private void inicia(){
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();
    }
    private void link() {
        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        finish();
    }
    private void finish(DialogInterface dialogInterface) {
        finish();
    }
    private void pause(){
        AlertDialog dialog = new AlertDialog.Builder(this).setMessage(R.string.up)
                .setPositiveButton(
                        R.string.up2,
                        (dialog1, which) -> {
                            dialog1.dismiss();
                            link();
                        })
                        .setOnCancelListener(this::finish)
                .create();
        dialog.show();
    }


    private void verify(String s){
        if(version.equals(s)) inicia();
        else pause();
    }

}