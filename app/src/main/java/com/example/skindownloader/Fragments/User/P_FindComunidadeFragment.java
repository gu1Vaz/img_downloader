package com.example.skindownloader.Fragments.User;


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
import com.example.skindownloader.Convert;
import com.example.skindownloader.R;
import com.example.skindownloader.SkinViewActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P_FindComunidadeFragment extends Fragment {

    Activity activity;
    View is;

    private final RequestQueue Queue;
    private final com.example.skindownloader.Convert Convert = new Convert();

    String accId;

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
        activity.runOnUiThread(()-> info.setText(R.string.info_comunidade));

        super.onViewCreated(view, savedInstanceState);
    }

    public P_FindComunidadeFragment(Activity _activity) {
        activity = _activity;
        Queue = Volley.newRequestQueue(activity);
    }

    public void find(View v){
        TextView msg  = is.findViewById(R.id.msg);
        EditText t = is.findViewById(R.id.editTextFind);
        accId = t.getText().toString();

        if(adapter != null) adapter.clear();
        activity.runOnUiThread(()-> loading.setVisibility(View.VISIBLE));
        activity.runOnUiThread(()-> msg.setVisibility(View.GONE));
        Queue.add(comunidade);
    }

    private void loadSkinsComunidade(String s) {
        TextView msg  = is.findViewById(R.id.msg);
        GridView listView =  is.findViewById(R.id.listSkins);
        int tam = Convert.toTam(s);
        if(tam <= 0){
            msg.setText(R.string.notFound2);
            activity.runOnUiThread(()-> msg.setVisibility(View.VISIBLE));
        }
        else{
            List<Skin> skinsList = Convert.toListSkins(s);
            adapter = new ListSkinsAdapter(activity, R.layout.skin_layout, skinsList);
            listView.setAdapter(adapter);
        }
        activity.runOnUiThread(()-> loading.setVisibility(View.GONE));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Skin skin =  adapter.getItem(position);
            Intent intent = new Intent(activity, SkinViewActivity.class);
            intent.putExtra("skin", skin);
            activity.startActivity(intent);
        });
    }
    StringRequest comunidade = new StringRequest(Request.Method.POST,
           "https://www.simplicialsoftware.com/api/account/RequestCommunitySkinData",
            this::loadSkinsComunidade,
            error -> { }
    ){
        @Override
        protected Map<String,String> getParams(){
            Map<String,String> payload = new HashMap<>();
            payload.put("Game","Nebulous");
            payload.put("Version","1105");
            payload.put("Ticket","");
            payload.put("searchOrder","POPULAR");
            payload.put("searchString",accId);
            payload.put("count","100");
            payload.put("index","0");
            payload.put("type","ACCOUNT");
            return payload;
        }

        @Override
        public Map<String, String> getHeaders() {
            Map<String,String> params = new HashMap<>();
            params.put("Content-Type","application/x-www-form-urlencoded");
            return params;
        }
    };

}
