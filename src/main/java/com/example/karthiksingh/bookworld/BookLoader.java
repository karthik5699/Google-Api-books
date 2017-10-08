package com.example.karthiksingh.bookworld;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import static com.example.karthiksingh.bookworld.BookResult.Web_url;

/**
 * Created by karthiksingh on 2017-10-04.
 */

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {
    private String murl;
    private static final String TAG = "tag";

    public BookLoader(Context context, String url) {
        super(context);
        this.murl = url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();

    }

    @Override
    public ArrayList<Book> loadInBackground(){
        if(murl==null || murl==" "){
            return null;
        }

        Log.d(TAG, "loadInBackground: "+Web_url);
        Log.d(TAG, String.valueOf(BookNetwork.extractBooks(Web_url).size()));

        return BookNetwork.extractBooks(Web_url);

    }
}
