package com.example.admin.assignment3;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.assignment3.TrackListHelper;
import com.example.admin.assignment3.MusicService;
import com.example.admin.assignment3.R;
import com.example.admin.assignment3.Track;

import java.util.ArrayList;


public class MainActivity extends ListActivity implements View.OnClickListener{

    // Variables
    private int currentTrack = 0;
    private boolean isPlaying = false;
    boolean isPaused = false;
    ArrayList<Track> trackList;

    ImageButton playBtn;
    ImageButton nextBtn;
    ImageButton prevBtn;


    // Created method called when activity is under creation.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init instance of TrackListHelper.
        TrackListHelper trackListHelper = new TrackListHelper(this);
        // Check if storage is available using method in TrackListHelper class.
        if(trackListHelper.checkIfStorageAvailable()){
            Log.d("MainActivity", "Ja, den är tillgänglig!");

            // Using TrackListHelper to get playlist.
            trackList = trackListHelper.get();
            // Creating an custom array adapter the fast but VERY insufficient way which works with small lists, using android layout and the playlist.
            ArrayAdapter<Track> musicAdapter = new ArrayAdapter<Track>(this, android.R.layout.simple_list_item_2, android.R.id.text1, trackList){
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                    text1.setText(trackList.get(position).toString());
                    text2.setText(trackList.get(position).getArtist());
                    return view;
                };

            };
            // Sets the adapter created to the listView.
            setListAdapter(musicAdapter);

            // Getting all buttons
            playBtn = (ImageButton)findViewById(R.id.playBtn);
            nextBtn = (ImageButton)findViewById(R.id.nextBtn);
            prevBtn = (ImageButton)findViewById(R.id.prevBtn);

            // Setting listeners for each button
            playBtn.setOnClickListener(this);
            nextBtn.setOnClickListener(this);
            prevBtn.setOnClickListener(this);
        }

    }

    // Method used to handle onclick events in the listView.
    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {

        // Creating an intent to start MusicService.
        Intent intentService = new Intent(this, MusicService.class);
        // Get track clicked by position.
        currentTrack = position;
        // Setting click track to play and an which action to use in MusicService using putExtra with chaining.
        intentService.putExtra(TrackListHelper.TRACK, currentTrack).putExtra(TrackListHelper.ACTION, TrackListHelper.PLAY_SONG);
        // Starting the intent using startService method.
        startService(intentService);

        // Setting the icon from play to pause icon and isPlaying to true.
        playBtn.setImageResource(R.drawable.ic_action_pause);
        isPlaying = true;
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Method used to handle onClick events from the buttons
    @Override
    public void onClick(View view) {
        Intent intentService = new Intent(this, MusicService.class);
        switch (view.getId()){
            // Play or pause.
            case R.id.playBtn:
                if(isPlaying){
                    // Just pause playing
                    intentService.putExtra(TrackListHelper.ACTION, TrackListHelper.PAUSE);
                    // Show pause button.
                    playBtn.setImageResource(R.drawable.ic_action_play);
                    isPlaying = false;
                    //isPaused set to true
                    isPaused = true;
                    startService(intentService);
                }
                else {
                    if (isPaused) {
                        // continue the track at the time it was paused
                        intentService.putExtra(TrackListHelper.ACTION, TrackListHelper.PLAY);
                        // Show pause button.
                        playBtn.setImageResource(R.drawable.ic_action_pause);
                        isPlaying = true;
                        startService(intentService);
                    } else {
                        // Just start playing
                        intentService.putExtra(TrackListHelper.ACTION, TrackListHelper.PLAY_SONG);
                        // Show pause button.
                        playBtn.setImageResource(R.drawable.ic_action_pause);
                        isPlaying = true;
                        startService(intentService);
                    }
                }
                break;
            // Play next song in list.
            case R.id.nextBtn:
                // Checks that currentTrack is more than or equal to 0 and then it adds its own value with 1.
                //When the last track in the TrackList is selected and you press "next button"
                // it will jump to the first track in the list.
                if (currentTrack >= 0) {
                    currentTrack++;
                    if (currentTrack < TrackListHelper.numberOfTracks) {
                        intentService.putExtra(TrackListHelper.TRACK, currentTrack);
                        intentService.putExtra(TrackListHelper.ACTION, TrackListHelper.PLAY_SONG);
                    }
                    else {
                        currentTrack = 0;
                        intentService.putExtra(TrackListHelper.TRACK, currentTrack);
                        intentService.putExtra(TrackListHelper.ACTION, TrackListHelper.PLAY_SONG);
                    }
                }
                break;
            // Play previous song in list.
            case R.id.prevBtn:
                //Checks if currnetTrack is more then 0 and then it subtracts its own value with 1
                //If the first track in in the TrackList is selected and you press "Prev button"
                //it will restart the first track in the list.
                if (currentTrack > 0) {
                        currentTrack--;
                        intentService.putExtra(TrackListHelper.TRACK, currentTrack);
                        intentService.putExtra(TrackListHelper.ACTION, TrackListHelper.PLAY_SONG);
                    }
                    else {
                        currentTrack = 0;
                        intentService.putExtra(TrackListHelper.TRACK, currentTrack);
                        intentService.putExtra(TrackListHelper.ACTION, TrackListHelper.PLAY_SONG);
                }
                break;
        }
        startService(intentService);
    }
}