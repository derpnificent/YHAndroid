package com.example.admin.assignment2b.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.assignment2b.Data.GalleryDbHelper;
import com.example.admin.assignment2b.Models.GalleryFrame;
import com.example.admin.assignment2b.R;
import com.squareup.picasso.Picasso;

public class ContactProfile extends Activity {

    /* Initializes variables */
    GalleryDbHelper galleryDbHelper;
    GalleryFrame galleryFrame;

    ImageView iv;
    TextView nameTv;
    TextView ageTv;
    TextView descTv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_profile);

        /*create a new instance of your database*/
        galleryDbHelper = new GalleryDbHelper(this);

        /* Finds views from the ids defined in the XML file */
        iv = (ImageView)findViewById(R.id.profileUrlIv);
        nameTv = (TextView)findViewById(R.id.profileNameTv);
        ageTv = (TextView)findViewById(R.id.profileAgeTv);
        descTv = (TextView)findViewById(R.id.profileDescTv);

        /*Return the intent that start the current activity*/
        Intent profileIntent = getIntent();
        /*Get the value of ID from  previous activity.*/
        int id = profileIntent.getIntExtra("id",0);
        /*Get id from database*/
        galleryFrame = galleryDbHelper.getId(id);

        /*Get the value based on the item ID you clicked on*/
        Picasso.with(this).load(galleryFrame.getUrl()).placeholder(R.drawable.ic_launcher).into(iv);
        nameTv.setText(galleryFrame.getName());
        ageTv.setText(galleryFrame.getAge());
        descTv.setText(galleryFrame.getDescription());


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contact_profile, menu);
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
}