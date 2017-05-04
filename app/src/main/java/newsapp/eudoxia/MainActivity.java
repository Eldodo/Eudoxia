package newsapp.eudoxia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.PermissionChecker;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<String> tab;
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NewsAPI.context = getApplicationContext();
        setContentView(R.layout.activity_main);
        //NewsAPI.setContext(getApplicationContext());
        if (!(getSharedPreferences("Preferences",(MODE_PRIVATE)).getString("0", "Test").equals("Valide"))){
            Intent PremierLancement = new Intent(this, Preferences.class);
            PremierLancement.putExtra("settings","false");
            startActivity(PremierLancement);
            finish();
        }

        tab = new ArrayList<String>();
        tab.add("Home");
        tab.add("Trends");
        int i = 1;
        SharedPreferences p = getSharedPreferences("Preferences",(MODE_PRIVATE));
        String str = p.getString(""+i,"Test");
        while(!str.equals("Test")){
            tab.add(str);
            i++;
            str = p.getString(""+i, "Test");

        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        int j;
        for(j = 0; j<tab.size(); j++){
            menu.add(Menu.FIRST,j,j,tab.get(j)).setIcon(R.drawable.ic_arrow_forward_black);

        }
        menu.add(Menu.NONE,j,j,"Settings").setIcon(R.drawable.ic_menu_manage);

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ListFragment listFragment = new ListFragment();
            Bundle args = new Bundle();
            args.putString("tab",tab.get(position));
            listFragment.setArguments(args);
            return listFragment;
        }

        @Override
        public int getCount() {
            return tab.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tab.get(position);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(item.getTitle()=="Settings"){
            //Todopreference fragment
            Intent settings = new Intent(this, Preferences.class);
            settings.putExtra("settings","true");
            startActivity(settings);
            return true;
        }

        TabLayout.Tab tab = tabLayout.getTabAt(id);
        tab.select();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
