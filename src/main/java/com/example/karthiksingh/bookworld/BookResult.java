package com.example.karthiksingh.bookworld;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.example.karthiksingh.bookworld.BookNetwork.bookimages;

/**
 * Created by karthiksingh on 2017-10-04.
 */

public class BookResult extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book>>{

    public static String Web_url;
    public static CustomArrayAdapter mAdapter;
    private static final int BOOK_ID=1;
    private ProgressBar progress;
    private TextView emptytextview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list_view);

        Bundle extras = getIntent().getExtras();

        String keyword = extras.getString("yourkey");
        try {

            Web_url = "https://www.googleapis.com/books/v1/volumes?q=" + URLEncoder.encode(keyword, "UTF-8") + "&maxResults=8&minResults=4";
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();





        mAdapter = new CustomArrayAdapter(getBaseContext(),new ArrayList<Book>());


        Log.d(TAG, "onCreate: new adapter created");



        ListView listView = (ListView)findViewById(R.id.list2);
         emptytextview = (TextView)listView.findViewById(R.id.empty_view);
        listView.setEmptyView(emptytextview);
         progress = (ProgressBar)findViewById(R.id.progress);

        listView.setAdapter(mAdapter);
        if(isConnected) {

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOK_ID, null, this);
        }
        else{
            progress.setVisibility(View.GONE);
            emptytextview.setText("No Internet Connection");
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),BookDetailed.class);
                Book current = mAdapter.getItem(i);

                intent.putExtra("booktitle",current.gettitle());
                intent.putExtra("booksubtitle",current.getsubTitle());
                intent.putExtra("bookauthors",current.getauthors());
                intent.putExtra("bookpagecount",current.getpageCount());
                intent.putExtra("bookimage",bookimages[i]);
                intent.putExtra("bookpublish",current.getpublishedDate());
                intent.putExtra("bookdescription",current.getDescription());
                intent.putExtra("bookreadlink",current.getpreviewLink());
                intent.putExtra("bookpdfis",current.getpdfisAvailable());
                intent.putExtra("bookdownload",current.getdownloadLink());

                startActivity(intent);
            }
        });




    }
    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int i, Bundle bundle){

        return new BookLoader(this,Web_url);



    }
    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader,ArrayList<Book> books){
        mAdapter.clear();



        if(books!=null){
            mAdapter.addAll(books);

        }
        else {
            emptytextview.setText("No Books Available");
        }
        progress.setVisibility(View.GONE);


    }
    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {
        // Loader reset, so we can clear out our existing data.

        mAdapter.clear();



    }
    @Override
    public void onStop(){
        super.onStop();
    }
    @Override
    public void onResume(){
        super.onResume();

        Log.d(TAG, "onResume: ");
    }
    public void onmenuclick(){
        Intent back = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(back);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_bar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.searchicon:
                onmenuclick();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }










}
