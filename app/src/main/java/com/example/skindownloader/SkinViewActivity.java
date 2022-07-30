package com.example.skindownloader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.skindownloader.Adapters.Skin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class SkinViewActivity extends AppCompatActivity {

    Skin skin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_view);

        skin = (Skin) getIntent().getSerializableExtra("skin");
        setDados();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void download(View v){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 128);
        }else{
            View pai = (View) v.getParent();
            ImageView i = pai.findViewById(R.id.skin);

            TextView t= (TextView) v;
            t.setEnabled(false);
            t.setText("...");

            save(i);
        }
    }
    public void save(ImageView imgV){
        Bitmap bitmap = ((BitmapDrawable) imgV.getDrawable()).getBitmap();
        String time =  new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(System.currentTimeMillis());
        File path = Environment.getExternalStorageDirectory();
        File dir = new File(path+"/Download");
        dir.mkdirs();
        String imageName = time+".png";
        File file =  new File(dir, imageName);
        OutputStream out;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100,out);
            out.flush();
            out.close();
            Toast.makeText(this,"Skin salva em /Download",Toast.LENGTH_SHORT).show();
        }catch (Exception e) {
            Toast.makeText(this,"Ocorreu um erro ao salvar a imagem",Toast.LENGTH_SHORT).show();
        }
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    }
    @SuppressLint("SetTextI18n")
    public void setDados(){
        TextView id = findViewById(R.id.dados_id);
        TextView idUser = findViewById(R.id.dados_iduser);
        TextView pCount = findViewById(R.id.dados_pcount);
        ImageView img = findViewById(R.id.skin);

        id.setText(Integer.toString(skin.getId()));
        idUser.setText(Integer.toString(skin.getUploaderId()));
        pCount.setText(Integer.toString(skin.getPurchaseCount()));
        Glide.with(this)
                .load(skin.getImageUrl())
                .placeholder(R.mipmap.loading)
                .into(img);
    }
}
