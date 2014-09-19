package com.example.admin.ass1a;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


public class changebg extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changebg);

        /* Finds views from the ids defined in the XML file */
        final Button Green = (Button) findViewById(R.id.button1);
        final Button Black = (Button) findViewById(R.id.button2);
        final Button Grey = (Button) findViewById(R.id.button3);
        final RelativeLayout a = (RelativeLayout) findViewById(R.id.main);

        Green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.setBackgroundColor(Color.GREEN);//Sets the background to GREEN when the button is clicked
            }
        });

        Black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.setBackgroundColor(Color.BLACK);//Sets the background to BLACK when the button is clicked
            }
        });

        Grey.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                a.setBackgroundColor(Color.GRAY);//Sets the background to GREY when the button is clicked
             }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.changebg, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify phonebook parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_contact) {
            finish();
        }
        else if (id == R.id.action_random) {
            finish();
            Intent intent = new Intent(this, randomnbr.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}


