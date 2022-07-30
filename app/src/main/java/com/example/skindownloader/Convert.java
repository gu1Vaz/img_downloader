package com.example.skindownloader;


import com.example.skindownloader.Adapters.Skin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Convert {

    public List<Skin> toListSkins(String objS){
        List<Skin> arr = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(objS);
            JSONArray skins = (JSONArray) obj.get("items");
            for(int i=0;i<skins.length();i++){
                JSONObject skin= (JSONObject) skins.get(i);
                Skin value = new Skin(
                        (int) skin.get("skinID"),
                        (int) skin.get("uploaderID"),
                        (int) skin.get("purchaseCount")
                );
                arr.add(value);
            }
        }catch (Exception ignored){ }
        return arr;
    }
    public int toTam(String objS){
        JSONObject obj = null;
        int tam = 0;
        try {
            obj = new JSONObject(objS);
            JSONArray arr = (JSONArray) obj.get("items");
            tam =   arr.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tam;
    }

}
