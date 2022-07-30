package com.example.skindownloader.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.skindownloader.Convert;
import com.example.skindownloader.Adapters.ListSkinsAdapter;
import com.example.skindownloader.R;
import com.example.skindownloader.Adapters.Skin;
import com.example.skindownloader.SkinViewActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllFragment extends Fragment {

    Activity activity;
    View is;

    private final String apiUrl = "https://www.simplicialsoftware.com/api/account/RequestCommunitySkinData";
    private final RequestQueue Queue;
    private final com.example.skindownloader.Convert Convert = new Convert();

    ListSkinsAdapter adapter;
    private final String type;
    private Integer index = 0;

    private Boolean loading;



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        is = inflater.inflate(R.layout.fragment_all,container,false);
        return  is;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        enableScrollListener();
        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressWarnings("deprecation")
    public AllFragment(Activity _activity, String _type) {
        activity = _activity;
        type = _type;

        Queue = Volley.newRequestQueue(activity);
        Queue.add(listSkins);
        loading = true;
        Queue.addRequestFinishedListener(listSkins-> loading = false);
    }

    private void enableScrollListener() {
        GridView v = is.findViewById(R.id.listSkins);
        v.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean pos = (firstVisibleItem == (totalItemCount - visibleItemCount)) &&
                              (firstVisibleItem !=0);
                if(!loading && pos){
                    loading = true;
                    Queue.add(listSkins);
               }
            }
        });
    }

    public void loadListSkins(String listskins){
        View loading =  is.findViewById(R.id.loadingSkins);
        activity.runOnUiThread(()-> loading.setVisibility(View.GONE));

        GridView listView =  is.findViewById(R.id.listSkins);
        List<Skin> skinsList = Convert.toListSkins(listskins);

        if(index == 0) {
            adapter = new ListSkinsAdapter(activity, R.layout.skin_layout, skinsList);
            listView.setAdapter(adapter);
            Queue.add(listSkins);
        }
        else adapter.addAll(skinsList);
        index +=100;

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Skin skin =  adapter.getItem(position);
            Intent intent = new Intent(activity, SkinViewActivity.class);
            intent.putExtra("skin", skin);
            activity.startActivity(intent);
        });

    }

    StringRequest listSkins = new StringRequest(Request.Method.POST,
            apiUrl,
            this::loadListSkins,
            error -> { }
    ){
        @Override
        protected Map<String,String> getParams(){
            Map<String,String> payload = new HashMap<>();
            payload.put("Game","Nebulous");
            payload.put("Version","1105");
            payload.put("Ticket","");
            payload.put("searchOrder",type);
            payload.put("count","100");
            payload.put("index",Integer.toString(index));
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
