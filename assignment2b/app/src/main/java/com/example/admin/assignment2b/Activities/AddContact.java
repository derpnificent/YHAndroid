package com.example.admin.assignment2b.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.admin.assignment2b.Data.GalleryDbHelper;
import com.example.admin.assignment2b.Models.GalleryFrame;
import com.example.admin.assignment2b.R;



public class AddContact extends Activity {

    /*create a new instance of your database*/
    GalleryDbHelper galleryDbHelper;

    /* Finds views from the ids defined in the XML file */
    EditText nameEt;
    EditText ageEt;
    EditText descEt;
    EditText urlEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        /* Finds views from the ids defined in the XML file */
        nameEt = (EditText)findViewById(R.id.addnameEt);
        ageEt = (EditText)findViewById(R.id.addageEt);
        descEt = (EditText)findViewById(R.id.adddescEt);
        urlEt = (EditText)findViewById(R.id.addurlEt);

    /*create a new instance of your database*/
    galleryDbHelper = new GalleryDbHelper(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_cancelAdd) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addContact(View view) {

        /*Create a new object of GalleryFrame*/
        GalleryFrame galleryFrame = new GalleryFrame(urlEt.getText() + "", nameEt.getText() + "", ageEt.getText() + "", descEt.getText() + "");

        /*Insert galleryFrame object*/
        galleryDbHelper.insert(galleryFrame);


        finish();
    }

}
