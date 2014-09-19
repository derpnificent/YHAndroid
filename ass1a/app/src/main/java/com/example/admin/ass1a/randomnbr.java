package com.example.admin.ass1a;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class randomnbr extends Activity {

    /* Initializes variables */
    Button generateRandom;
    TextView randomResult;
    Random myRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomnbr);

        /* Finds views from the ids defined in the XML file */
        generateRandom = (Button)findViewById(R.id.generate);
        randomResult = (TextView)findViewById(R.id.randomresult);
    }

    public void generateRandom(View view) {
        /*Generates a random number*/
        myRandom = new Random();
        double random = myRandom.nextDouble()*100;// This makes the random number to go from 0-100
        String randomstr = String.format("%.2f", random);//This round off to two decimals

        randomResult.setText(randomstr);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.randomnbr, menu);
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
        else if (id == R.id.action_changeColor) {
            finish();
            Intent intent = new Intent(this, changebg.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
