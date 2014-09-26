package com.example.admin.assignment2b.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.admin.assignment2b.Data.GalleryDbHelper;
import com.example.admin.assignment2b.Adapters.PicassoAdapter;
import com.example.admin.assignment2b.R;



public class MainActivity extends ListActivity {

    PicassoAdapter picassoAdapter;
    GalleryDbHelper galleryDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an instance of Gallery Database
        galleryDbHelper = new GalleryDbHelper(this);

        // Get latest data
        Cursor galleryCursor = galleryDbHelper.get();

        // Create an instance of adapter, pass context and galleryCursor from database
        picassoAdapter = new PicassoAdapter(this, galleryCursor, false);

        // Set the adapter on the listview
        setListAdapter(picassoAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.add_contact) {
            Intent addContactIntent = new Intent(this, AddContact.class);
            startActivityForResult(addContactIntent,1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //When a item is clicked it will open in a new window
    protected void onListItemClick (ListView l, View v, int position, long id) {
        //profileIntent.putExtra send the value of "id" to ContactProfile activity
        Intent profileIntent = new Intent(this, ContactProfile.class);
        profileIntent.putExtra("id",(int) id);
        startActivity(profileIntent);
    }

    @Override
    /*Called when an activity you launched exits, giving you the requestCode
    you started it with, the resultCode it returned, and any additional data from it.*/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Get latest data
        Cursor galleryCursor = galleryDbHelper.get();

        // Set new cursor data on the adapter.
        picassoAdapter.changeCursor(galleryCursor);

        // Notify data set changed
        picassoAdapter.notifyDataSetChanged();

    }

}