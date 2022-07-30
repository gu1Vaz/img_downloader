package com.example.skindownloader.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.skindownloader.R;

import java.util.List;

public class ListSkinsAdapter extends ArrayAdapter<Skin> {

    private final int layoutResource;
    Context context;

    public ListSkinsAdapter(Context _context, int layoutResource, List<Skin> dadosItem){
        super(_context, layoutResource, dadosItem);
        context = _context;
        this.layoutResource = layoutResource;

    }
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResource, null);
        }

        Skin dados = getItem(position);

        if (dados != null) {
            ImageView img = (ImageView) view.findViewById(R.id.imgSkin);
            if (img != null) {
                Glide.with(context)
                        .load(dados.getImageUrl())
                        .placeholder(R.mipmap.loading)
                        .into(img);
            }
        }
        return view;
    }
    
}

