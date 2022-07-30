package com.example.skindownloader.Fragments.Skin;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.skindownloader.Adapters.ListSkinsAdapter;
import com.example.skindownloader.Adapters.Skin;
import com.example.skindownloader.R;
import com.example.skindownloader.SkinViewActivity;


import java.util.ArrayList;
import java.util.List;

public class S_FindById extends Fragment {

    Activity activity;
    View is;

    private final RequestQueue Queue;

    String skinId = "";

    ProgressBar loading;
    ListSkinsAdapter adapter;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        is = inflater.inflate(R.layout.fragment_find,container,false);
        return  is;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        Button b = is.findViewById(R.id.btnFind);
        b.setOnClickListener(this::find);

        loading = is.findViewById(R.id.loadingSkins);

        TextView info  = is.findViewById(R.id.infos);
        activity.runOnUiThread(()-> info.setText(R.string.info_skin));

        super.onViewCreated(view, savedInstanceState);
    }

    public S_FindById(Activity _activity) {
        activity = _activity;
        Queue = Volley.newRequestQueue(activity);
    }

    public void find(View v){
        TextView msg  = is.findViewById(R.id.msg);
        EditText t = is.findViewById(R.id.editTextFind);
        skinId = t.getText().toString();

        if(adapter != null) adapter.clear();
        activity.runOnUiThread(()-> loading.setVisibility(View.VISIBLE));
        activity.runOnUiThread(()-> msg.setVisibility(View.GONE));
        StringRequest user = new StringRequest(Request.Method.GET,
                "https://s3.amazonaws.com/simplicialsoftware.skins/"+skinId,
                this::loadSkinPlayerPerfil,
                error -> {
                    msg.setText(R.string.notFound2);
                    activity.runOnUiThread(()-> msg.setVisibility(View.VISIBLE));
                }
        );
        Queue.add(user);
    }

    private void loadSkinPlayerPerfil(String s) {
        GridView listView =  is.findViewById(R.id.listSkins);

        List<Skin> arr = new ArrayList<>();
        arr.add(new Skin(Integer.valueOf(skinId),0,0));

        adapter = new ListSkinsAdapter(activity, R.layout.skin_layout, arr);
        listView.setAdapter(adapter);

        activity.runOnUiThread(()-> loading.setVisibility(View.GONE));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Skin skin =  adapter.getItem(position);
            Intent intent = new Intent(activity, SkinViewActivity.class);
            intent.putExtra("skin", skin);
            activity.startActivity(intent);
        });
    }
}
