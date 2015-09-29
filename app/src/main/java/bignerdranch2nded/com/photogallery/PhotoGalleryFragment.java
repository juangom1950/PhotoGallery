package bignerdranch2nded.com.photogallery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan on 9/26/2015.
 */
public class PhotoGalleryFragment extends Fragment {

    private static final String TAG = "PhotoGalleryFragment";

    private RecyclerView mPhotoRecyclerView;
    private List<GalleryItem> mItems = new ArrayList<>();

    public static PhotoGalleryFragment newInstance() {
        return new PhotoGalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Control whether a fragment instance is retained across Activity re-creation
        setRetainInstance(true);

        new FetchItemsTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);

        mPhotoRecyclerView = (RecyclerView) v
                .findViewById(R.id.fragment_photo_gallery_recycle_view);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        setupAdapter();

        return v;
    }

    private void setupAdapter() {

        //isAdded() Return true if the fragment is currently added to its activity
        //This is a method of the Fragment.java class. Page 424
        /*However, now that you are using an AsyncTask you are triggering some callbacks from a background thread.
          Thus you cannot assume that the fragment is attached to an activity. You must check to make sure that your fragment is still attached.
          If it is not, then operations that rely on that activity (like creating your PhotoAdapter, which in turn creates a TextView using the
          hosting activity as the context) will fail. This is why, in your code above, you check that isAdded() is true before setting the adapter.
         */
        if (isAdded()) {
            mPhotoRecyclerView.setAdapter(new PhotoAdapter(mItems));
        }
    }

    //First define a viewHolder us innerClass
    private class PhotoHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;

        public PhotoHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView;
        }

        public void bindGalleryItem(GalleryItem item) {
            mTitleTextView.setText(item.toString());
        }
    }

    //Next, add a RecyclerView.Adapter to provide PhotoHolders as needed based on a list of GalleryItems
    //This PhotoAdapter class uses the PhotoHolder class
    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

        private List<GalleryItem> mGalleryItems;

        public PhotoAdapter(List<GalleryItem> galleryItems) {
            mGalleryItems = galleryItems;
        }

        //To make it works you need to add these 3 methods
        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            TextView textView = new TextView(getActivity());
            return new PhotoHolder(textView);
        }

        @Override
        public void onBindViewHolder(PhotoHolder photoHolder, int position) {
            GalleryItem galleryItem = mGalleryItems.get(position);
            photoHolder.bindGalleryItem(galleryItem);
        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }
    }

    //3rd Paramter "Result", the type of the result of the background computation. Page 425
    //It sets the type of value returned by doInBackground(…) as well as the type of onPostExecute(…)’ s input parameter.
    private class FetchItemsTask extends AsyncTask<Void, Void, List<GalleryItem>> {

        @Override
        protected List<GalleryItem> doInBackground(Void... params) {

           /* try {
                String result = new FlickrFetchr().getUrlString("https://www.bignerdranch.com");
                Log.i(TAG, "Fetched contents of URL: " + result);

            } catch (IOException ioe) {
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }*/

            //Retrieving json data from the server

            return new FlickrFetchr().fetchItems();
        }


        /*onPostExecute(…). onPostExecute(…) is run after doInBackground(…) completes. More importantly, onPostExecute(…) is run on the main
          thread, not the background thread, so it is safe to update the UI within it.
          This method accepts as input the list you fetched and returned inside doInBackground(…), puts it in mItems, and updates your RecyclerView’s adapter.
        */
        @Override
        protected void onPostExecute(List<GalleryItem> items) {
            mItems = items;
            setupAdapter();
        }

    }

}
