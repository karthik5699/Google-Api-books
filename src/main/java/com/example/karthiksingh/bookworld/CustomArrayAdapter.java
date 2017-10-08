package com.example.karthiksingh.bookworld;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by karthiksingh on 2017-10-04.
 */

public class CustomArrayAdapter extends ArrayAdapter<Book> {


    public CustomArrayAdapter(Context context, ArrayList<Book> books){
        super(context,0,books);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_element, parent, false);
        }

        Book current = getItem(position);

        ImageView bookimage = (ImageView)listItemView.findViewById(R.id.bookimage);

        Bitmap[] bookimages = BookNetwork.imageconverter();



        if(current.getimage()!=null)

        bookimage.setImageBitmap(bookimages[position]);

//        if(current.getBitmapFromURL()!=null) {
//            bookimage.setImageBitmap(current.getBitmapFromURL());
//        }
       else{
            bookimage.setImageResource(R.drawable.ic_launcher);
        }




        TextView titleview = (TextView)listItemView.findViewById(R.id.booktitle);
        titleview.setText(current.gettitle());

        TextView subtitleview = (TextView)listItemView.findViewById(R.id.booksubtitle);
        subtitleview.setText(current.getsubTitle());

        TextView authorview = (TextView)listItemView.findViewById(R.id.bookauthor);
        authorview.setText(current.getauthors());





        return listItemView;
    }
}
