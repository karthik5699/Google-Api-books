package com.example.karthiksingh.bookworld;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class BookDetailed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detailed);
        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();


        final String btitle=extras.getString("booktitle");
        String bsubtitle = extras.getString("booksubtitle");
        String bauthors = extras.getString("bookauthors");
        String bpagecount = String.valueOf(extras.getInt("bookpagecount"));
        Bitmap bbitmap = (Bitmap) intent.getParcelableExtra("bookimage");
        String bpublished = extras.getString("bookpublish");
        String bdescription = extras.getString("bookdescription");
        final String breadlink = extras.getString("bookreadlink");
        final Boolean isavailable = extras.getBoolean("bookpdfis");
        final String bdownload = extras.getString("bookdownload");

        TextView title = (TextView)findViewById(R.id.dtitle);
        TextView subtitle = (TextView)findViewById(R.id.dsubtitle);
        TextView authors = (TextView)findViewById(R.id.dauthors);
        TextView pagecount = (TextView)findViewById(R.id.dpagecount);
        TextView published = (TextView)findViewById(R.id.dpublished);
        TextView description = (TextView)findViewById(R.id.ddescription);
        Button read = (Button)findViewById(R.id.dread);
        final Button download = (Button)findViewById(R.id.ddownload);


        ImageView image = (ImageView)findViewById(R.id.dimage);
        image.setImageBitmap(bbitmap);
        title.setText(btitle);
        subtitle.setText(bsubtitle);
        authors.setText(bauthors);
        pagecount.setText("Pages : "+bpagecount);

        published.setText(bpublished);
        description.setText(bdescription);
        description.setMaxLines(12);


        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri readuri = Uri.parse(breadlink);
                Intent website = new Intent(Intent.ACTION_VIEW,readuri);
                startActivity(website);
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isavailable){
                    Uri downuri = Uri.parse(bdownload);
                    Intent dwebsite = new Intent(Intent.ACTION_VIEW);
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+btitle);
                    dwebsite.setDataAndType(downuri,"application/pdf");

                    Intent intent = Intent.createChooser(dwebsite,"Open File");
                    try {
                        startActivity(intent);
                    }catch (ActivityNotFoundException e){
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getBaseContext(),"No pdf available",Toast.LENGTH_SHORT).show();
                }
            }
        });






    }
}
