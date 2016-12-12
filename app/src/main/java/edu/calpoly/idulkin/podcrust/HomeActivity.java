package edu.calpoly.idulkin.podcrust;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import edu.calpoly.idulkin.podcrust.searchedList.SearchListActivity;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MediaPlayerService mediaService;
    private boolean bound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        createDrawer();

        if(!isChangingConfigurations()) {
            //Loads the trending fragment on activity created
            Fragment fragment = null;
            Class fragmentClass = null;
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentClass = TrendingFragment.class;
            //Replace the fragment
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }
    }

    @Override
    public void onStart(){
        super.onStart();

        //Bind to the media player service
        Intent intent = new Intent(this, MediaPlayerService.class);
        startService(intent);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        bound = true;

    }

    @Override
    public void onResume(){
        super.onResume();

        //Floating action button for play/pause
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(mediaService == null) {
            Log.e("Media Service Null","Hiding FAB");
            fab.hide();
        } else {
            fab.show();
                switch (mediaService.getState()) {
                    case STOPPED:
                        fab.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                                R.mipmap.ic_play));
                        break;
                    case PAUSED:
                        fab.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                                R.mipmap.ic_play));
                        break;
                    case PLAYING:
                        fab.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                                R.mipmap.ic_pause));
                        break;
                }
            }
        if(bound) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.e("Home Activity FAB click", "MP State:" + mediaService.getState());

                    switch (mediaService.getState()) {
                        case STOPPED:
                            Snackbar.make(view, "Choose an episode from Search", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                            break;
                        case PAUSED:
                            mediaService.start();

                            fab.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                                    R.mipmap.ic_pause));
                            break;
                        case PLAYING:
                            mediaService.pause();

                            fab.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                                    R.mipmap.ic_play));
                            break;
                    }
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (bound) {
            unbindService(mConnection);
            bound = false;
        }
    }

    @Override
    protected void onDestroy(){
        if(!isChangingConfigurations())
            mediaService.stop();

            mediaService.stopForeground(true);
            mediaService.onDestroy();
    }

    protected void createDrawer() {
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        drawer.openDrawer(Gravity.LEFT);
    }

    @Override
    public void onBackPressed() {
        //TODO: Fix. Nav drawer doesn't appear over fragments.
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
            super.onBackPressed();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Context context = getApplicationContext();
        Fragment fragment = null;
        Class fragmentClass = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //Nav Drawer Search
        if (id == R.id.nav_search) {
            fragmentClass = SearchFragment.class;
            //Replace the fragment
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

            //Nav Bar Trending
        } else if (id == R.id.nav_trending) {
            fragmentClass = TrendingFragment.class;
            //Replace the fragment
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        //Nav Bar Share
        } else if (id == R.id.nav_share) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            sharePodcast();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            mediaService = binder.getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };


    //Share the podcast currently in the Media Player Service. If null, displays a toast.
    private void sharePodcast(){

        if(mediaService == null){
            Snackbar.make(findViewById(R.id.fab),"Share the podcast you're listening to", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, mediaService.getSource());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Send with..."));
    }
}
