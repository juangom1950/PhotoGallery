package bignerdranch2nded.com.photogallery.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import bignerdranch2nded.com.photogallery.GalleryItem;

/**
 * Created by Juan on 9/29/2015.
 */
public class Listing {

    @SerializedName("photos")
    private Children mChildren;

    public String getPage() {
        return mChildren.getPage();
    }

    public List<GalleryItem> getPostList(){

        List<GalleryItem> galleryList = new ArrayList<GalleryItem>();

        for (GalleryItem children : mChildren.getChildrenList()){
            galleryList.add(children);
        }

        return galleryList;
    }
}
