package com.sparenparts.sparenparts;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

//Vars

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    /**Variables needed for drawer and listview**/
    private DrawerLayout mDrawerLayout;
    private int lastExpandedPosition = -1;
    private ExpandableListView mDrawerList;

    private LinearLayout navDrawerView;

    CustomExpandAdapter customAdapter;

    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ArrayList<String> mPlanetTitles=new ArrayList<>();
    private int selectedPosition;

    List<SampleTO> listParent;

    HashMap<String, List<String>> listDataChild;
    /**End**/
    /**Search bar resources**/
    TextView searchProduct;
    ImageButton searchProductbutton;
    /**END**/
    Context context;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    int i = 0;
    /**For image auto scrolling and stuff**/
    private static final int TOTAL_IMAGES = 5;
    private static final int TOTAL_PRODUCT_TABS = 2;
    int currentPage=0;
    /**END**/
    TabHost productHost;
    ArrayList <ProductListModel> items=new ArrayList<>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    /** this here my friend will make dots below images**/
    private int dotsCount=TOTAL_IMAGES;
    private ImageView[] dots;
    LinearLayout linearLayout;
    List<Integer> layoutISD=new ArrayList<>();

    private void drawPageSelectionIndicators(int mPosition){
        if(linearLayout!=null) {
            linearLayout.removeAllViews();
        }
        linearLayout=(LinearLayout)findViewById(R.id.viewPagerCountDots);
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(context);
            if(i==mPosition)
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.item_selected));
                else
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.item_unselected));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);
            linearLayout.addView(dots[i], params);
            layoutISD.add(linearLayout.getId());
        }
    }
    /**END**/
    public void onItemClick(int mPosition)
    {
        ProductListModel tempValues = ( ProductListModel) items.get(mPosition);
        Toast.makeText(context, "Pressed Item No : " + mPosition, Toast.LENGTH_SHORT).show();
    }
    private void workerProductTab() {

        ArrayList<String> tabNames=new ArrayList<String>();
        tabNames.add("Shop By Category");
        tabNames.add("Shop By Brand");
        productHost=(TabHost)findViewById(R.id.tabHost);
        productHost.setup();
        TabHost.TabSpec spec=productHost.newTabSpec("tab1");
        spec.setContent(R.id.listViewTab1);
        spec.setIndicator(tabNames.get(0));
        productHost.addTab(spec);
        spec=productHost.newTabSpec("tab2");
        spec.setContent(R.id.listViewTab2);
        spec.setIndicator(tabNames.get(1));
        productHost.addTab(spec);
        productHost.setCurrentTab(0);


        int currentTab = productHost.getCurrentTab();
        TabWidget widget = productHost.getTabWidget();
        //This is initial house keeping.
        for (int i = 0; i < widget.getChildCount(); i++) {
            View v = widget.getChildAt(i);
            // Look for the title view to ensure this is an indicator and not a divider.
            TextView tv = (TextView) v.findViewById(android.R.id.title);
            //float textSize=(tv.getTextSize())/(getResources().getDisplayMetrics().density);
            //tv.setTextSize(textSize+textSize*.15f);
            //tv.setTextSize(getResources().getDimension(R.dimen.tab_header_size));
            if (tv == null) {
                continue;
            }
            if (currentTab == i) {
                v.setBackgroundResource(R.drawable.tab_switch_style);
                tv.setTextColor(getResources().getColor(R.color.colorWhite));
            }
            else {
                v.setBackgroundResource(R.drawable.tab_switch_blank_style);
                tv.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        }
        productHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int currentTab = productHost.getCurrentTab();
                TabWidget widget = productHost.getTabWidget();
                for (int i = 0; i < widget.getChildCount(); i++) {
                    View v = widget.getChildAt(i);
                    // Look for the title view to ensure this is an indicator and not a divider.
                    TextView tv = (TextView) v.findViewById(android.R.id.title);
                    if (tv == null) {
                        continue;
                    }
                    if (currentTab == i) {
                        v.setBackgroundResource(R.drawable.tab_switch_style);
                        tv.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    else {
                        v.setBackgroundResource(R.drawable.tab_switch_blank_style);
                        tv.setTextColor(getResources().getColor(R.color.colorBlack));
                    }
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        mDrawerList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(groupPosition));
                parent.setItemChecked(index, true);
                String parentTitle = ((SampleTO) customAdapter.getGroup(groupPosition)).getTitle();
                return false;
            }
        });

        mDrawerList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Log.d("CHECK", "child click");

                int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
                parent.setItemChecked(index, true);
                parent.setItemChecked(index, true);
                String parentTitle = customAdapter.getGroup(groupPosition).getTitle();
                String childTitle=customAdapter.getChild(groupPosition,childPosition);
                Toast.makeText(context,"Clicked at parent\t"+parentTitle+"\tChild at \t"+childTitle,Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        /** Search bar actions**/
        View searchLayout=findViewById(R.id.include);
        searchProduct=(TextView)searchLayout.findViewById(R.id.search_bar);
        searchProductbutton=(ImageButton)searchLayout.findViewById(R.id.search_button);
        searchProductbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,searchProduct.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
        /**END**/
        /** some image banner scrolling and filling stuff**/
        // Instantiate a ViewPager and a PagerAdapter.
        //getSupportActionBar().setCustomView(getResources().getDrawable(R.drawable.ic_action_menu));


        mPager = (ViewPager) findViewById(R.id.banner_pager);
        mPagerAdapter = new ImageSliderFrag(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        drawPageSelectionIndicators(0);

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        currentPage=mPager.getCurrentItem();
                        if (currentPage == TOTAL_IMAGES-1) {
                            currentPage = 0;
                        }
                        mPager.setCurrentItem(currentPage++, true);
                    }
                });
            }
        }, 500, 3000);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                drawPageSelectionIndicators(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        /**END**/
        workerProductTab();


        for(int i=1;i<100;i++){
            items.add(new ProductListModel("Name"+i,"Meta"+i,"ic_action_user"));
        }
        ListView listViewTab1 = (ListView) findViewById(R.id.listViewTab1);
        ListView listViewTab2 = (ListView) findViewById(R.id.listViewTab2);
        Resources res =getResources();
        /**************** Create Custom Adapter *********/
        ProductListAdapter adapter;
        adapter=new ProductListAdapter(this,items,res );
        listViewTab1.setAdapter(adapter);
        listViewTab2.setAdapter(adapter);


        /**Filling Drawer List View**/
        //navDrawerView = (LinearLayout) findViewById(R.id.navDrawerView);
        for(int i=0;i<10;i++){
            mPlanetTitles.add("Sub Product\t"+i);
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList = (ExpandableListView) findViewById(R.id.nav_drawer_list);

        // set a custom shadow that overlays the main content when the drawer
        // opens
        //mDrawerLayout.setDrawerShadow(R.drawable.ic_action_billing, GravityCompat.START);

        listParent = new ArrayList<SampleTO>();
        listDataChild = new HashMap<String, List<String>>();

        // Navigation Drawer of Flight starts
        listParent.add(new SampleTO("Main Product", R.drawable.ic_action_cart));
        listParent.add(new SampleTO("Main Product 2", R.drawable.ic_action_checklist));
        listParent.add(new SampleTO("Main Product 3", R.drawable.ic_action_logout));

        listDataChild.put("Main Product", mPlanetTitles);
        listDataChild.put("Main Product 2", mPlanetTitles);
        listDataChild.put("Main Product 3", mPlanetTitles);

        customAdapter = new CustomExpandAdapter(this, listParent, listDataChild);
        // setting list adapter
        mDrawerList.setAdapter(customAdapter);
        mDrawerList.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);
        mDrawerList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    mDrawerList.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
        /**END**/




        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


        /** To solve Scrolling problems**/
        ScrollView mainScrollView=(ScrollView)findViewById(R.id.parentScroll);
        mainScrollView.smoothScrollTo(0, 0);
        mainScrollView.fullScroll(ScrollView.FOCUS_UP);
        mainScrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
        /**END**/
    }


    /** Built in overrides**/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.sparenparts.sparenparts/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.sparenparts.sparenparts/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
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
}
