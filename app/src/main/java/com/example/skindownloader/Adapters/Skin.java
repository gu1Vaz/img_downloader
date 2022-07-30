package com.example.skindownloader.Adapters;

import java.io.Serializable;

public class Skin implements Serializable  {
    private final Integer id;
    private final Integer uploaderId;
    private final Integer purchaseCount;
    private final String image_url;

    public Skin(Integer id, Integer uploaderId, Integer purchaseCount) {
        this.id = id;
        this.uploaderId = uploaderId;
        this.purchaseCount = purchaseCount;
        this.image_url = "https://s3.amazonaws.com/simplicialsoftware.skins/" + id;
    }

    public int getId() {
        return id;
    }

    public int getUploaderId() {
        return uploaderId;
    }
    public int getPurchaseCount() {
        return purchaseCount;
    }

    public String getImageUrl() {
        return image_url;
    }

}
