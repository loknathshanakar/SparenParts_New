package com.sparenparts.sparenparts;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

//Vars

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
Context context;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private ViewPager mPager2;
    private PagerAdapter mPagerAdapter2;
    TabLayout tabLayout;
    LinearLayout tabStrip;
    View virtualView;
    int i=0;
    private static final int TOTAL_IMAGES=5;
    private static final int TOTAL_PRODUCT_TABS=2;



    private boolean initialTabs(){
        int offset=0;
        try {
            for (int i = 0; i < TOTAL_PRODUCT_TABS; i++) {
                offset = i;
                if ((offset) == 0) {
                    View v = View.inflate(context, R.layout.layout_tab_1, null);
                    TextView tvt = (TextView) v.findViewById(R.id.tab_tv_1);
                    tvt.setText("SHOP BY CATEGORY");
                    tvt.setGravity(Gravity.CENTER);
                    tabLayout.getTabAt(i).setCustomView(v);
                    //tabLayout.getTabAt(i).getCustomView().setBackgroundResource(R.drawable.tab_color_1);
                }
                if ((offset) == 1) {
                    View v = View.inflate(context, R.layout.layout_tab_1, null);
                    TextView tvt = (TextView) v.findViewById(R.id.tab_tv_1);
                    tvt.setTextColor(getResources().getColor(R.color.colorBlack));
                    tvt.setText("SHOP BY BRAND");
                    tvt.setGravity(Gravity.CENTER);
                    tabLayout.getTabAt(i).setCustomView(v);
                }
            }
        }catch (Exception e){
            return(false);
        }
        return(true);
    }

    public void resetOtherTabs(int skipTab){
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            if(i==skipTab) {
                continue;
            }
            else{
                tabStrip.getChildAt(i).setBackgroundResource(R.drawable.tab_color1_rised_uncolored);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=this;
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.banner_pager);
        mPagerAdapter = new ImageSliderFrag(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        //Product tabs
        mPager2 = (ViewPager) findViewById(R.id.product_pager);
        mPagerAdapter2 = new ProductSliderFrag(getSupportFragmentManager());
        mPager2.setAdapter(mPagerAdapter2);
        tabLayout = (TabLayout) findViewById(R.id.product_tab);
        tabLayout.setupWithViewPager(mPager2);

        initialTabs();

        tabStrip = (LinearLayout) tabLayout.getChildAt(0);
        virtualView=(View) findViewById(R.id.virtLine);
        virtualView.setBackgroundColor(getResources().getColor(R.color.colorRed));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager2.setCurrentItem(tab.getPosition());
                int x = tab.getPosition();
                if (x == 0) {
                    View rootview=tabStrip.getChildAt(0).getRootView();
                    TextView tv=(TextView)rootview.findViewById(R.id.tab_tv_1);
                    tv.setTextColor(getResources().getColor(R.color.colorWhite));
                    tabStrip.getChildAt(0).setBackgroundResource(R.drawable.tab_color_1);

                    View _rootview=tabStrip.getChildAt(1).getRootView();
                    tv=(TextView)_rootview.findViewById(R.id.tab_tv_1);
                    tv.setTextColor(getResources().getColor(R.color.colorBlack));
                    tabStrip.getChildAt(1).setBackgroundResource(R.drawable.tab_color_uncolored);

                    tabLayout.setSelectedTabIndicatorHeight(2);
                }
                if (x == 1) {
                    tabStrip.getChildAt(1).setBackgroundResource(R.drawable.tab_color_1);
                    View rootview=tabStrip.getChildAt(1).getRootView();
                    TextView tv=(TextView)rootview.findViewById(R.id.tab_tv_1);
                    tv.setTextColor(getResources().getColor(R.color.colorWhite));


                    View _rootview=tabStrip.getChildAt(0).getRootView();
                    tv=(TextView)_rootview.findViewById(R.id.tab_tv_1);
                    tv.setTextColor(getResources().getColor(R.color.colorBlack));
                    tabStrip.getChildAt(0).setBackgroundResource(R.drawable.tab_color_uncolored);

                    tabLayout.setSelectedTabIndicatorHeight(2);
                }
                //resetOtherTabs(x);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //////////////////
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ImageSliderFrag extends FragmentStatePagerAdapter {
        public ImageSliderFrag(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ImageSlider.newInstance(position);
        }

        @Override
        public int getCount() {
            return TOTAL_IMAGES;
        }
    }

    private class ProductSliderFrag extends FragmentStatePagerAdapter {
        public ProductSliderFrag(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ProductSlider.newInstance(position);
        }

        @Override
        public int getCount() {
            return TOTAL_PRODUCT_TABS;
        }
    }
}
