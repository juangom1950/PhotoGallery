package bignerdranch2nded.com.photogallery.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import bignerdranch2nded.com.photogallery.GalleryItem;

/**
 * Created by Juan on 9/29/2015.
 */
public class Children {

    @SerializedName("page")
    private String page;
    @SerializedName("photo")
    private List<GalleryItem> mChildrenList;

    public String getPage() {
        return page;
    }

    public List<GalleryItem> getChildrenList() {

        for (GalleryItem item : mChildrenList) {

            if(item.getUrl() != null) {
                return mChildrenList;
            }
        }

        return null;
    }
}
