package com.example.karthiksingh.bookworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }


    public void searchBooks(View view) {
        EditText searchitem = (EditText)findViewById(R.id.editText);
        Intent result = new Intent(getApplicationContext(),BookResult.class);
        String s = searchitem.getText().toString().toLowerCase();
        if(!s.equals("")&&!s.equals(" ")) {
            result.putExtra("yourkey", s);
            startActivity(result);
            searchitem.setText("");
        }
        else {
            Toast.makeText(getBaseContext(),"Please Enter valid text",Toast.LENGTH_SHORT).show();
        }

    }
    public void titledisplayer(View view){
        if(view==(TextView)findViewById(R.id.apptitle)){
            TextView app2 = (TextView)findViewById(R.id.apptitle2);
            app2.setVisibility(View.VISIBLE);
            view.setVisibility(View.INVISIBLE);
        }
        else{
            if(view==(TextView)findViewById(R.id.apptitle2));
            TextView app1 = (TextView)findViewById(R.id.apptitle);
            app1.setVisibility(View.VISIBLE);
            view.setVisibility(View.INVISIBLE);
        }
        }

    }

