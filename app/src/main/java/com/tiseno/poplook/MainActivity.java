package com.tiseno.poplook;

//import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import androidx.annotation.NonNull;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.core.view.GravityCompat;
//import android.support.v7.app.ActionBarActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.transition.Explode;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tiseno.poplook.functions.Artist;
import com.tiseno.poplook.functions.Genre;
import com.tiseno.poplook.functions.GenreAdapter;
import com.tiseno.poplook.functions.FontUtil;
import com.tiseno.poplook.functions.HackyDrawerLayout;
import com.tiseno.poplook.functions.RegistrationIntentService;
import com.tiseno.poplook.functions.sideMenuItem;
import com.tiseno.poplook.webservice.AsyncTaskCompleteListener;
import com.tiseno.poplook.webservice.WebServiceAccessGet;
import com.useinsider.insider.Insider;
import com.useinsider.insider.MessageCenterData;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity implements AsyncTaskCompleteListener<JSONObject> , ChangeEditAddressList.selectAddress,AddtoCartFragment.addToCartMethod, BottomNavCollectionFragment.selectedBottomCategoryMenu, HomeBottomFragment.selectBottomCollectionMenu, BottomNavMyAccFragment.selectBottomMyAcc{
    String backOrderID,backOrderName;
    public FirebaseAnalytics mFirebaseAnalytics;
    String UserID, CartID, LanguageID, selectedSide;
    String cartItem,apikey;
    ArrayList<sideMenuItem> itemArray = new ArrayList<sideMenuItem>();
    ArrayList<sideMenuItem> parentSideMenuArray = new ArrayList<sideMenuItem>();

    String selectedCategory = "";

    // GCM Config -----------------------------------------------------

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String TAG = "GCM POPLOOK";

//------------------------------------------------------------------


    private Toolbar toolbar,toolbar2;                              // Declaring the Toolbar Object

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    HackyDrawerLayout Drawer;

    private GenreAdapter mmAdapter;
    private List<Genre>genres;// Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle

    TextView top,bagNoti,wishlistNoti;
    ImageButton backBtn,shoppingBagBtn,wishlistBtn,searchBtn,xCloseBtn, homeButton;
    ImageView logo;

    RelativeLayout side_menu_user_rl;
    LinearLayout topBarLL;

    TextView side_menu_userTV;
    private String numberInBag= "0";
    private String numberInWishlist = "0";
    String tickTock="",tickTockID="",SelectedCountryName="";
    boolean isAppInstalled=false;
    String SelectedShopID="1";
    ProductInfoFragment productFrag;

    BottomNavigationView bottomView;

    AppBarLayout appBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);

        mFirebaseAnalytics =FirebaseAnalytics.getInstance(this);

        Insider.Instance.getMessageCenterData(25, new Date(), new Date(1546300800), new MessageCenterData() {
            @Override
            public void loadMessageCenterData(JSONArray messageCenterData) {
                System.out.println("Retrieve data: " + messageCenterData);
            }
        });

//        Object categoryName =  Insider.Instance.getDeepLinkData("categoryName");
//        Object appUrl = Insider.Instance.getDeepLinkData("updateNotification");
//        Object productID = Insider.Instance.getDeepLinkData("productId");
//        Object productName = Insider.Instance.getDeepLinkData("productName");
//        Object searchKeyword = Insider.Instance.getDeepLinkData("searchKeyword");
//        if(appUrl != null && appUrl.toString().length() >0){
//            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
//            try {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//            } catch (android.content.ActivityNotFoundException anfe) {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//            }
//        }else if(productID != null && productID.toString().length() >0 && productName != null && productName.toString().length() > 0){
//
//            SharedPreferences.Editor editor = pref.edit();
//            editor.putString("comeFromNotification", "1");
//            editor.putString("productID", productID.toString());
//            editor.putString("productName", productName.toString());
//            editor.apply();
//            System.out.println("COME FROM INSIDER");
//        }else if(searchKeyword != null && searchKeyword.toString().length() >0){
//
//            System.out.print("Heloooooooo cariiiiii" + searchKeyword.toString());
//            SharedPreferences.Editor editor = pref.edit();
//            editor.putString("comeFromNotification", "1");
//            editor.putString("searchKeyword", searchKeyword.toString());
//            editor.apply();
//            System.out.println("COME FROM INSIDER");
//        }else
//            if (categoryID != null && categoryID.toString().length() > 0 && categoryName != null && categoryName.toString().length() > 0) {
//                SharedPreferences.Editor editor = pref.edit();
//                editor.putString("comeFromNotification", "1");
//                editor.putString("categoryID", categoryID.toString());
//                editor.putString("categoryName", categoryName.toString());
//                editor.apply();
//                System.out.println("COME FROM INSIDER");
//
//        }else {
//                SharedPreferences.Editor editor = pref.edit();
//                editor.putString("comeFromNotification", "0");
//                editor.putString("categoryID", "");
//                editor.putString("categoryName", "");
//                editor.putString("searchKeyword", "");
//                editor.putString("productID", "");
//                editor.putString("productName", "");
//                editor.apply();
//                System.out.println("NOT COME FROM INSIDER");
//
//            }
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Call some material design APIs here

            Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
            window.setStatusBarColor(this.getResources().getColor(R.color.lightgrey));


            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

// set an exit transition
            window.setExitTransition(new Explode());
            window.setEnterTransition(new Explode());

        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        onNewIntent(getIntent());
        //**************************** PUSH NOTIFICATION CONFIGURATIONS

        try{
            if (checkPlayServices()) {

                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);

            } else {
                Log.i("GCM", "No valid Google Play Services APK found.");
            }

        }

        catch(Exception e){
            System.out.println(e.getMessage());
        }
//****************************END PUSH NOTIFICATION CONFIGURATIONS
//        try{
//            Intent intent = getIntent();
//            String comeFromNotification = intent.getStringExtra("comeFromNotification");
//            String categoryID = intent.getStringExtra("categoryID");
//            String categoryName = intent.getStringExtra("categoryName");
//            SharedPreferences.Editor editor = pref.edit();
//            editor.putString("comeFromNotification", comeFromNotification);
//            editor.putString("categoryID", categoryID);
//            editor.putString("categoryName", categoryName);
//            editor.apply();
//        }catch (Exception e){
//            SharedPreferences.Editor editor = pref.edit();
//            editor.putString("comeFromNotification", "0");
//            editor.putString("categoryID", "");
//            editor.putString("categoryName", "");
//            editor.apply();
//        }



        UserID = pref.getString("UserID", "");
        CartID = pref.getString("CartID", "0");
        LanguageID = pref.getString("LanguageID", "");
        SelectedCountryName = pref.getString("SelectedCountryName", "");
        cartItem = pref.getString("cartItem", "0");
        isAppInstalled = pref.getBoolean("isAppInstalled", false);
        //multishop
        SelectedShopID  = pref.getString("SelectedShopID", "1");
        //
        //apikey
        apikey =pref.getString("apikey","");
//        if(!isAppInstalled){
//            // add shortcutIcon code here
//            System.out.println("COME SHORTCUT");
//            Intent intent = this.getIntent();
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            this.overridePendingTransition(0, 0);
//            this.finish();
//
//            this.overridePendingTransition(0, 0);
//            startActivity(intent);
//
//        }
    /* Assinging the toolbar object ot the view
    and setting the the Action bar to our toolbar
     */
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
//        toolbar2= (Toolbar) findViewById(R.id.tool_bar1);
//        toolbar2.setVisibility(View.GONE);

        setSupportActionBar(toolbar);
        top = (TextView) toolbar.findViewById(R.id.toolbar_title);
        top.setTypeface(FontUtil.getTypeface(this, FontUtil.FontType.AVENIR_BLACK_FONT));
        logo = (ImageView) toolbar.findViewById(R.id.toolbar_title_image);
        backBtn = (ImageButton) toolbar.findViewById(R.id.backBtn);
        shoppingBagBtn = (ImageButton) toolbar.findViewById(R.id.shoppingBagBtn);
        wishlistBtn = (ImageButton) toolbar.findViewById(R.id.wishlistBtn);
        searchBtn =  (ImageButton) toolbar.findViewById(R.id.searchBtn);
        xCloseBtn =  (ImageButton) toolbar.findViewById(R.id.xCloseBtn);
        bagNoti =  (TextView) toolbar.findViewById(R.id.bagNoti);
        wishlistNoti =  (TextView) toolbar.findViewById(R.id.wishlistNoti);

        homeButton = (ImageButton)toolbar.findViewById(R.id.btnHome);
        /* Assinging the toolbar object ot the view
    and setting the the Action bar to our toolbar
     */

//        side_menu_user_rl = (RelativeLayout)findViewById(R.id.side_menu_user_rl);
//        side_menu_userTV = (TextView)findViewById(R.id.side_menu_userTV);

//        if(UserID.length() == 0)
//        {
//            side_menu_user_rl.setVisibility(View.GONE);
//        }
//        else
//        {
//            side_menu_user_rl.setVisibility(View.VISIBLE);
//            side_menu_userTV.setText("Hello, "+pref.getString("Name", ""));
//        }

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);



        top = (TextView) findViewById(R.id.toolbar_title);
        top.setTypeface(FontUtil.getTypeface(this, FontUtil.FontType.AVENIR_BLACK_FONT));


//        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);

//        mRecyclerView.setHasFixedSize(true);



        bottomView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        appBar = (AppBarLayout)findViewById(R.id.tabanim_appbar);

        bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.bottom_home:

                        FragmentManager fm3 = getFragmentManager();
                        HomeBottomFragment alertDialog3 = HomeBottomFragment.newInstance(selectedCategory);
                        alertDialog3.show(fm3, "fragment_alert3");

                        return true;

                    case R.id.bottom_product:

                        FragmentManager fm = getFragmentManager();
                        BottomNavCollectionFragment alertDialog = BottomNavCollectionFragment.newInstance(selectedCategory);
                        alertDialog.show(fm, "fragment_alert");
                        return true;

                    case R.id.bottom_account:

                        FragmentManager fm2 = getFragmentManager();
                        BottomNavMyAccFragment alertDialog2 = BottomNavMyAccFragment.newInstance("Testing2");
                        alertDialog2.show(fm2, "fragment_alert2");
//                        Fragment fragment = new LoginFragment();
//
//                        FragmentManager fragmentManager = getFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.commit();
                        return true;
                }
                return false;
            }
        });



        backBtnControl("");
        wishlistBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Fragment fragment = new SavedItemsFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.replace(R.id.fragmentContainer, fragment, "SavedItemsFragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }

        });
        shoppingBagBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    Fragment fragment = new ShoppingBagFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.replace(R.id.fragmentContainer, fragment, "ShoppingBagFragment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

            }

        });

        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Fragment fragment = new SearchFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,R.anim.slide_in_right, R.anim.slide_out_left);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.replace(R.id.fragmentContainer, fragment, "SearchFragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }

        });

        homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                openFragment(new HomeFragment(), "HomeFragment");


            }

        });

        xCloseBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    Log.i("MainActivity", "popping backstack");
                    fm.popBackStack();
                }

            }

        });



        final GestureDetector mGestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {

            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });



//        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
//                View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());
//
//
//
//                if(child!=null && mGestureDetector.onTouchEvent(motionEvent)){
//                    onTouchDrawer(mRecyclerView.getChildPosition(child));
//
//                    return true;
//
//                }
//
//
//                return false;
//            }
//
//
//
//            @Override
//            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
//
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//            }
//        });


//        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

//        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager


//        Drawer = (HackyDrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
//        Drawer.setScrimColor(Color.TRANSPARENT);

//        mDrawerToggle.setDrawerIndicatorEnabled(true);
//
//        toolbar.setNavigationIcon(R.drawable.ic_launcher);


//        getSupportActionBar().setTitle("");
////
////
////        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.drawer_open,R.string.drawer_close){
////
////            @Override
////            public void onDrawerOpened(View drawerView) {
////                super.onDrawerOpened(drawerView);
////
////                System.out.println("laluuuuuuu");
////
////                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
////                // open I am not going to put anything here)
////                toolbar.setVisibility(View.GONE);
////                toolbar2.setVisibility(View.VISIBLE);
////                toolbar2.setNavigationIcon(R.drawable.btn_menu_pressed);
////
////                SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref", 0);
////                if(UserID.length() == 0)
////                {
////                    side_menu_user_rl.setVisibility(View.GONE);
////                }
////                else
////                {
////                    side_menu_user_rl.setVisibility(View.VISIBLE);
////                    side_menu_userTV.setText("Hello, "+pref1.getString("Name", ""));
////                }
////                try{
////                    InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
////                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);}catch (Exception e){}
////            }
////
////            @Override
////            public void onDrawerClosed(View drawerView) {
////                super.onDrawerClosed(drawerView);
////                // Code here will execute once drawer is closed
////                toolbar.setVisibility(View.VISIBLE);
////                toolbar2.setVisibility(View.GONE);
////                toolbar.setNavigationIcon(R.drawable.btn_menu_pressed);
////
////                try{
////                    InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
////                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);}catch (Exception e){}
////
////
////            }
////
////
////
////        }; // Drawer Toggle Object Made
////        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
////        mDrawerToggle.syncState();
////        toolbar.setNavigationIcon(R.drawable.btn_menu_pressed);// Finally we set the drawer toggle sync State
////
//        getSideMenuList();

        SharedPreferences.Editor editor = pref.edit();
        //editor.putBoolean("atMyVoucher", false);
        editor.putBoolean("atMyVoucher", true);
        editor.putBoolean("isAppInstalled",true);
        editor.putBoolean("notFirstTime", true);
        editor.apply();

        String NeedToGoBackToCart = pref.getString("NeedToGoBackToCart","0");
        if(NeedToGoBackToCart.equals("1")){
        shoppingBagBtn.performClick();

        }

        System.out.println("sininini");

    }


//    public void onTouchDrawer(String title , String cattId) {
//
//        System.out.println("sininiinii = " + cattId);
//
//        String categoryID = cattId;
//        String categoryName = title;
//
//        selectedSide = title;
//
//
//        if(categoryName.equals("Home"))
//        {
//            Drawer.closeDrawers();
//
//            openFragment(new HomeFragment(), "HomeFragment");
//        }
//        if(categoryName.equals("Visit Our Store"))
//        {
//            Drawer.closeDrawers();
//
//            openFragment(new VisitOurStoreFragment(), "VisitOurStoreFragment");
//        }
//        if(categoryName.equals("My Account"))
//        {
//            Drawer.closeDrawers();
//
//            openFragment(new MyAccountFragment(), "MyAccountFragment");
//        }
//        if(categoryName.contains("Shipping To"))
//        {
//            Drawer.closeDrawers();
//
//            openFragment(new ChangeCountryFragment(), "ChangeCountryFragment");
//        }
//        else if(categoryName.equals("Settings"))
//        {
//            Drawer.closeDrawers();
//
//            openFragment(new SettingsFragment(), "SettingsFragment");
//        }
//        else if(categoryName.equals("Log In"))
//        {
//            Drawer.closeDrawers();
//
//            openFragment(new LoginFragment(), "LoginFragment");
//
//        }
//
//        else if(categoryName.equals("POPLOOK Loyalty Rewards"))
//        {
//            Drawer.closeDrawers();
//
//            openFragment(new LoyaltyMainPageFragment(), "LoyaltyMain");
//
//        }
//
//
//        else if(categoryName.equals("My Member ID"))
//        {
//            Drawer.closeDrawers();
//
//            openFragment(new MyMemberIDFragment(), "MemberIDFragment");
//
//        }
//        else if(categoryName.equals("Log Out"))
//        {
//            Drawer.closeDrawers();
//
//            new AlertDialog.Builder(MainActivity.this)
//                    .setTitle("Log Out")
//                    .setMessage("Are you sure you want to log out?")
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
//                    {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            SharedPreferences pref = MainActivity.this.getSharedPreferences("MyPref", 0);
//                            SharedPreferences.Editor editor = pref.edit();
//
//                            editor.putString("UserID", "");
//                            editor.putString("CartID", "");
//                            editor.putString("LanguageID", "1");
//                            editor.putString("Name", "");
//                            editor.putString("LastName", "");
//                            editor.putString("Email", "");
//                            editor.putString("WishlistID", "");
//                            editor.putString("cartItem", "0");
//                            editor.putString("wishlistItem", "0");
//                            editor.putString("loyalty_id", "");
//                            editor.putString("popup_show", "");
//                            editor.putString("tier_level","");
//                            editor.putString("entity_id", "");
//
//                            editor.apply();
//
//                            Insider.Instance.tagEvent(MainActivity.this,"logout");
//
//                            new Handler().post(new Runnable() {
//
//                                @Override
//                                public void run() {
//                                    Intent intent = MainActivity.this.getIntent();
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                    MainActivity.this.overridePendingTransition(0, 0);
//                                    MainActivity.this.finish();
//
//                                    MainActivity.this.overridePendingTransition(0, 0);
//                                    startActivity(intent);
//                                }
//                            });
//
//                        }
//
//                    })
//                    .setNegativeButton("Cancel", null)
//                    .show();
//        }
//        else
//        {
//
//            for(int i=0; i < parentSideMenuArray.size(); i++)
//            {if(parentSideMenuArray.get(i).gettitle() == categoryName)
//
//
//            categoryID = parentSideMenuArray.get(i).getcategoryID();
//
//            }
//
//
//            if(categoryID.length() > 0)
//            {
//                Drawer.closeDrawers();
//
//
//
//                Fragment fragment = new ProductListFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("prodID", categoryID);
//                bundle.putString("catName",categoryName);
//                bundle.putString("fromHome", "Home");
//                fragment.setArguments(bundle);
//
//                openFragment(fragment, "ProductListFragment");
//            }
//        }
//
//
//
//
//
//
//    }

    private void openFragment(final Fragment fragment, final String fragmentTag) {

        FragmentManager fm = getFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = fm.beginTransaction();
//                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,R.anim.slide_in_right, R.anim.slide_out_left);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.fragmentContainer, fragment, fragmentTag);
//                ft.addToBackStack(null);
        ft.commit();


    }
    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    public void onBackPressed(){
//        if (Drawer.isDrawerOpen(GravityCompat.START)) {
//            Drawer.closeDrawer(GravityCompat.START);
//        } else {
            FragmentManager fm = getFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                Log.i("MainActivity", "popping backstack");
                ProductListFragment productListFragment = (ProductListFragment)fm.findFragmentByTag("ProductListFragment");

                PaymentFragment paymentFragment = (PaymentFragment)fm.findFragmentByTag("PaymentFragment");



                NewAddressBillingFragment addressFragment = (NewAddressBillingFragment)fm.findFragmentByTag("NewAddressBillingFragment");

                if (productListFragment != null && productListFragment.isVisible()) {
                    // add your code here
//                    FragmentTransaction trans = fm.beginTransaction();
//                    trans.remove(productListFragment);
//                    trans.commit();
//                    fm.popBackStack();
                    FragmentManager fmHome = getFragmentManager();
                    fmHome.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    FragmentTransaction ft = fm.beginTransaction();
//                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,R.anim.slide_in_right, R.anim.slide_out_left);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.replace(R.id.fragmentContainer, new HomeFragment(), "HomeFragment");
//                ft.addToBackStack(null);
                    ft.commit();
                }


                else if (addressFragment != null && addressFragment.isVisible()) {
                    // add your code here

                    toolbar = (Toolbar) findViewById(R.id.tool_bar);

                    AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
                    params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                            | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);

                    Fragment fragment = new ShoppingBagFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("forWishlist", "0");
//                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.replace(R.id.fragmentContainer, fragment, "ShoppingBagFragment");
                    fragmentTransaction.commit();
                }

                else {
                    fm.popBackStack();
                }

                showBottomBar(true);

            } else {
                Log.i("MainActivity", "nothing on backstack, calling super");
                HomeFragment homeFragment = (HomeFragment)fm.findFragmentByTag("HomeFragment");

                if (homeFragment != null && homeFragment.isVisible()) {
                    new AlertDialog.Builder(this)
                            .setIcon(R.drawable.ic_launcher_noti)
                            .setTitle("Closing POPLOOK")
                            .setMessage("Are you sure you want to close this application?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }

                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                }else {
                    FragmentManager fmHome = getFragmentManager();
                    fmHome.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    FragmentTransaction ft = fm.beginTransaction();
//                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,R.anim.slide_in_right, R.anim.slide_out_left);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.replace(R.id.fragmentContainer, new HomeFragment(), "HomeFragment");
//                ft.addToBackStack(null);
                    ft.commit();
                }
            }

            getSupportActionBar().show();
        }

//    }

    public void switchContent(int id, Fragment fragment) {

        System.out.println("switch content");

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(id, fragment, "ProductInfoFragment");
        ft.addToBackStack(null);
        ft.commit();

    }

    public void switchContent2(int id, Fragment fragment) {

        FragmentManager fm = getFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = fm.beginTransaction();
//                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,R.anim.slide_in_right, R.anim.slide_out_left);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(id, fragment, "ProductInfoFragment");
        ft.addToBackStack(null);
        ft.commit();

        System.out.println("aaaaaaa");
    }

    public void getSideMenuList(){

//        String action="Menus/mobile?apikey="+apikey+"&shop="+SelectedShopID+"&lang=1";
        String action="Menus/webcategories/lang/1/shop/"+SelectedShopID+"?apikey="+apikey;

        WebServiceAccessGet callws = new WebServiceAccessGet(this, this);
        callws.execute(action);

        genres = new ArrayList<>(1000);

    }

    @Override
    public void onTaskComplete(JSONObject result) {

        if(result!=null){

            try{

                if (result.getString("action").equals("Menus_webcategories")) {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                    UserID = pref.getString("UserID", "");
                    SelectedCountryName = pref.getString("SelectedCountryName", "");
                    itemArray.clear();
                    parentSideMenuArray.clear();

//                    JSONArray dta = result.ge;

                    JSONObject test = result.getJSONObject("data");

                    JSONArray Titles = test.getJSONArray("children");

                    if(genres.size() == 0) {


//                        artists.add(new Artist("Paramore" , "Scarves"));
//                        artists.add(new Artist("Flyleaf" , "Tops"));
//                        artists.add(new Artist("The Script", "Shoes"));


                        genres.add(new Genre("Shipping To " + SelectedCountryName + "", null));


                        if (UserID.length() == 0) {

                        } else {
                            genres.add(new Genre("Shipping To " + SelectedCountryName + "", null));

                        }

                        genres.add(new Genre("Home", null));

                        for (int i = 0; i < Titles.length(); i++) {

                            String sideMenuTitle = Titles.getJSONObject(i).getString("name");
                            String sideMenuCatID = Titles.getJSONObject(i).getString("id");

                            JSONArray subSideMenuArray = Titles.getJSONObject(i).getJSONArray("children");

                            parentSideMenuArray.add(new sideMenuItem(sideMenuCatID, sideMenuTitle, "",false,false,false));

                            List<Artist> artists = new ArrayList<>(subSideMenuArray.length());

                            System.out.println("SideArray" + subSideMenuArray);

                            if (subSideMenuArray.length() > 0) {

                                for (int j = 0; j < subSideMenuArray.length(); j++) {
                                    String catID = subSideMenuArray.getJSONObject(j).getString("id");
                                    String catName = subSideMenuArray.getJSONObject(j).getString("name");
                                    itemArray.add(new sideMenuItem(catID, catName, "", false, false, false));

                                    artists.add(new Artist(catID, catName));


                                }

                                genres.add(new Genre(sideMenuTitle, artists));

                            } else {
                                String catID = Titles.getJSONObject(i).getString("id");
                                String catName = Titles.getJSONObject(i).getString("name");
                                itemArray.add(new sideMenuItem(catID, catName, "", false, false, false));


                                genres.add(new Genre(catName, null));


                            }



                        }

                        genres.add(new Genre("Visit Our Store", null));
                        genres.add(new Genre("Follow Us", null));

                        if (UserID.length() > 0) {
                            genres.add(new Genre("My Account", null));

                            side_menu_user_rl.setVisibility(View.VISIBLE);
                            side_menu_userTV.setText("Hello, " + pref.getString("Name", ""));
                        }



                        genres.add(new Genre("Settings", null));

                        genres.add(new Genre("POPLOOK Loyalty Rewards", null));


                        if (UserID.length() == 0) {
                            genres.add(new Genre("Log In", null));

                        } else {

                            if(SelectedShopID.equals("1"))
                            {
                                genres.add(new Genre("My Member ID", null));
                            }

                            genres.add(new Genre("Log Out", null));

                        }
                    }

////                    getGenres();
//                    mmAdapter = new GenreAdapter(genres,new OnItemClickListener()
//                    {
//                        @Override
//                        public void onItemClick(View v, int position) {
//                            if(UserID.length()!=0){
//                                onTouchDrawer(position);
//                            }else{
//                                onTouchDrawer(position);
//                            }
//                        }
//                    });

//                    mmAdapter = new GenreAdapter( this,selectedSide,genres, new OnItemClickListener() {
//                        @Override
//                        public void onItemClick(String title, String catId) {
//
//                            System.out.println("getMainTitle" + title);
////
//                            if(UserID.length()!=0){
//                                onTouchDrawer(title , catId);
//                            }else{
//                                onTouchDrawer(title , catId);
//                            }
//                        }
//                    });


//                    mAdapter = new MyAdapter(this, itemArray);
//                    mAdapter= new MyAdapter(this, itemArray, new OnItemClickListener() {
//                        @Override
//                        public void onItemClick(View v, int position) {
//                            if(UserID.length()!=0){
//                                onTouchDrawer(position);
//                            }else{
//                                onTouc
//                             hDrawer(position);
//                            }
//                        }
//                    });
                    // Make Bookends
//                    Bookends<RecyclerView.Adapter> mBookends = new Bookends<>(mAdapter);
//
//
//                    if (UserID.length() > 0) {
//                        // Inflate footer view
//                        LayoutInflater inflater1 = LayoutInflater.from(this);
//
//                        RelativeLayout header = (RelativeLayout) inflater1.inflate(R.layout.side_menu_header_layout, mRecyclerView, false);
//
//                        mBookends.addHeader(header);
//                    }

                    mRecyclerView.setAdapter(mmAdapter);

                    System.out.println("sizeeeee is " + itemArray.size());
                }

            }
            catch (Exception e){

            }

        }
        else{

//            new AlertDialog.Builder(this)
//                    .setTitle("Internet Connection Problem")
//                    .setMessage("We are unable to connect to the server due to connection problem. Please check your connectivity and try again")
//                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    }).show();

        }
    }
    public void pressShoppingBag(){
       shoppingBagBtn.performClick();


    }
    public void changeToolBarText(String txt){
        try {
            if (txt != null && !txt.isEmpty()) {
                top.setText(txt);
            } else {
                top.setText("");
            }
        }catch (Exception e){

        }
    }
    public void changeToolBarTextView(Boolean view) {
        if (view) {
            top.setVisibility(View.VISIBLE);
        } else {
            top.setVisibility(View.GONE);
        }
    }

    public void changeBtnBackView(Boolean view) {
        if (view) {
            backBtn.setVisibility(View.INVISIBLE);
        } else {
            backBtn.setVisibility(View.INVISIBLE);
        }
    }
    public void changeToolBarImageView(Boolean view){
        if(view){
            logo.setVisibility(View.VISIBLE);
        }
        else{
            logo.setVisibility(View.GONE);
        }


    }
    public void changeBtnSearchView(Boolean view){
        if(view){
            searchBtn.setVisibility(View.VISIBLE);
        }
        else{
            searchBtn.setVisibility(View.INVISIBLE);
        }


    }
    public void changeBtnBagView(Boolean view){
        if(view){
            shoppingBagBtn.setVisibility(View.VISIBLE);
            bagNoti.setVisibility(View.VISIBLE);
        }
        else{
            shoppingBagBtn.setVisibility(View.GONE);
            bagNoti.setVisibility(View.GONE);
        }


    }

    public void changeBtnWishlistView(Boolean view){
        if(view){
            wishlistBtn.setVisibility(View.VISIBLE);
            wishlistNoti.setVisibility(View.VISIBLE);
        }
        else{
            wishlistBtn.setVisibility(View.GONE);
            wishlistNoti.setVisibility(View.GONE);
        }


    }

    public void changeBtnCloseXView(Boolean view){
        if(view){
            xCloseBtn.setVisibility(View.VISIBLE);
        }
        else{
            xCloseBtn.setVisibility(View.GONE);
        }


    }

    public void showBottomBar(Boolean view){
        if(view){
            bottomView.setVisibility(View.VISIBLE);
        }
        else{
            bottomView.setVisibility(View.GONE);
        }


    }

    public void disableExpandToolbar(Boolean view){
        if(view){
            appBar.setExpanded(true,true);
        }
        else{
            appBar.setExpanded(false,true);
        }


    }

    public void changeToolBarBagNotiText(String txt){
        numberInBag=txt;
        if (numberInBag.equals("-1")||numberInBag.equals("-2")||numberInBag.equals("0")||numberInBag.isEmpty()||numberInBag.equalsIgnoreCase("") || numberInBag.equals(null)||numberInBag.equals("null"))
        {
            bagNoti.setVisibility(View.INVISIBLE);
            bagNoti.setText("");
        } else {

            bagNoti.setVisibility(View.VISIBLE);
            bagNoti.setText(numberInBag);
        }
    }

    public void changeToolBarWishNotiText(String txt){
        numberInWishlist=txt;
        if (numberInWishlist.equals("-1")||numberInWishlist.equals("-2")||numberInWishlist.equals("0")||numberInWishlist.isEmpty()||numberInWishlist.equalsIgnoreCase("") || numberInWishlist.equals(null)||numberInWishlist.equals("null"))
        {
            wishlistNoti.setVisibility(View.INVISIBLE);
            wishlistNoti.setText("");
        } else {

            wishlistNoti.setVisibility(View.VISIBLE);
            wishlistNoti.setText(numberInWishlist);
        }
    }

    public void setDrawerState(Boolean isEnabled) {
        if ( isEnabled ) {
//            Drawer.setDrawerLockMode(HackyDrawerLayout.LOCK_MODE_UNLOCKED);
//            mDrawerToggle.setDrawerIndicatorEnabled(true);
//            mDrawerToggle.syncState();
//            toolbar.setNavigationIcon(R.drawable.btn_menu_pressed);

        }
        else {
//            Drawer.setDrawerLockMode(HackyDrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//            mDrawerToggle.setDrawerIndicatorEnabled(false);
//            mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onSupportNavigateUp();
//                }
//            });
//            mDrawerToggle.syncState();
        }
    }


    /**
     * GCM Configurations -------------------------------------------------------------
     */

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
            }
            return false;
        }
        return true;
    }

/**
 * END of GCM Configurations -------------------------------------------------------
 */
public void backBtnControl(String change){
    FragmentManager fm1 = getFragmentManager();
    final NewFilterFragment filterFragment = (NewFilterFragment)fm1.findFragmentByTag("NewFilterFragment");

    if (change.equalsIgnoreCase("CHANGE")) {
        System.out.println("LALU SINI ATAU");
        backBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                filterFragment.backBtn();

            }

        });
    }else {
        System.out.println("LALU SINI SANA");
        backBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    Log.i("MainActivity", "popping backstack");
                    ProductListFragment productListFragment = (ProductListFragment)fm.findFragmentByTag("ProductListFragment");

                    if (productListFragment != null && productListFragment.isVisible()) {
                        // add your code here
                        FragmentTransaction trans = fm.beginTransaction();
                        trans.remove(productListFragment);
                        trans.commit();

                        fm.popBackStack();
                    }else {
                        fm.popBackStack();
                    }

                    SharedPreferences pref = getSharedPreferences("MyPref", 0);
                    SharedPreferences.Editor editor = pref.edit();
                    //editor.putBoolean("atMyVoucher", false);
                    editor.putBoolean("atMyVoucher", true);
                    editor.apply();
                }

            }

        });
    }
}

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);//must store the new intent unless getIntent() will return the old one
        processExtraData();
    }

    private void processExtraData(){
        try {
            Intent intent = getIntent();
            String action = intent.getAction();
            String data = intent.getDataString();

            if (Intent.ACTION_VIEW.equals(action) && data != null) {
                String dataStr = data.substring(data.lastIndexOf("/") + 1);
                StringTokenizer tokens = new StringTokenizer(dataStr, "-");
                String prodID = tokens.nextToken();
                String prodName = tokens.nextToken();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);

//                SharedPreferences.Editor editor = pref.edit();
//                editor.putString("comeFromNotification", "1");
//                editor.putString("categoryID", prodID);
//                editor.putString("categoryName", prodName);
//                editor.apply();
//                System.out.println("COME FROM INSIDER INAPPS");

                Fragment fragment = new ProductInfoFragment();
                Bundle bundle = new Bundle();
                bundle.putString("prodID", prodID);
                bundle.putString("catName", "Home");
                fragment.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                FragmentTransaction ft = fm.beginTransaction();
//                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,R.anim.slide_in_right, R.anim.slide_out_left);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.replace(R.id.fragmentContainer, fragment, "ProductInfoFragment");
//                ft.addToBackStack(null);
                ft.commit();

            }
        }catch (Exception e){}    }
    public void onResume()
    {
        super.onResume();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);

        long millis = new Date().getTime();

        Insider.Instance.getMessageCenterData(1, new Date(millis), new Date(1546300800), new MessageCenterData() {
            @Override
            public void loadMessageCenterData(JSONArray messageCenterData) {
                System.out.println("Sini la macibai: " + messageCenterData);
            }
        });

//        Object categoryID = Insider.Instance.getDeepLinkData("categoryId");
//        Object categoryName = Insider.Instance.getDeepLinkData("categoryName");
//        Object appUrl = Insider.Instance.getDeepLinkData("updateNotification");
//        Object productID = Insider.Instance.getDeepLinkData("productId");
//        Object productName = Insider.Instance.getDeepLinkData("productName");
//        Object searchKeyword = Insider.Instance.getDeepLinkData("searchKeyword");
//
//        Object lastVisitPage = Insider.Instance.getDeepLinkData("lastVisitedPage");
//        Object lastVisitPageID = Insider.Instance.getDeepLinkData("lastVisitPage_ID");
//
//        Object salesPage = Insider.Instance.getDeepLinkData("salesPage");
//        Object salesPageID = Insider.Instance.getDeepLinkData("salesPage_ID");
//
//
//
//        Object cartPage = Insider.Instance.getDeepLinkData("cartPage");

//        if(appUrl != null && appUrl.toString().length() >0){
//            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
//            try {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//            } catch (android.content.ActivityNotFoundException anfe) {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//            }
//        }
//
//        else if(lastVisitPage != null && lastVisitPage.toString().length() >0 && lastVisitPageID !=null && lastVisitPageID.toString().length()>0)
//        {
//
//            SharedPreferences.Editor editor = pref.edit();
//            editor.putString("comeFromNotification", "1");
//            editor.putString("categoryID", lastVisitPageID.toString());
//            editor.putString("categoryName", lastVisitPage.toString());
//            editor.apply();
//            System.out.println("COME FROM INSIDER");
//            Fragment fragment = new ProductListFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString("prodID", lastVisitPageID.toString());
//            bundle.putString("catName", lastVisitPage.toString());
//            bundle.putString("fromHome", "Home");
//            fragment.setArguments(bundle);
//            FragmentManager fm = getFragmentManager();
//            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            FragmentTransaction ft = fm.beginTransaction();
////                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,R.anim.slide_in_right, R.anim.slide_out_left);
//            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//            ft.replace(R.id.fragmentContainer, fragment, "ProductListFragment");
////                ft.addToBackStack(null);
//            ft.commit();
//        }
//
//        else if(cartPage != null && cartPage.toString().length() >0)
//        {
//
//            Fragment fragment = new ShoppingBagFragment();
//            FragmentManager fm = getFragmentManager();
//            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            FragmentTransaction ft = fm.beginTransaction();
////                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,R.anim.slide_in_right, R.anim.slide_out_left);
//            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//            ft.replace(R.id.fragmentContainer, fragment, "ShoppingBagFragment");
////                ft.addToBackStack(null);
//            ft.commit();
//
//        }
//
//        else if(salesPage != null && salesPage.toString().length() >0 &&  salesPageID != null && salesPageID.toString().length() >0)
//        {
//
//            SharedPreferences.Editor editor = pref.edit();
//            editor.putString("comeFromNotification", "1");
//            editor.putString("categoryID", salesPageID.toString());
//            editor.putString("categoryName", salesPage.toString());
//            editor.apply();
//            System.out.println("COME FROM INSIDER");
//            Fragment fragment = new ProductListFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString("prodID", salesPageID.toString());
//            bundle.putString("catName", salesPage.toString());
//            bundle.putString("fromHome", "Home");
//            fragment.setArguments(bundle);
//            FragmentManager fm = getFragmentManager();
//            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            FragmentTransaction ft = fm.beginTransaction();
////                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,R.anim.slide_in_right, R.anim.slide_out_left);
//            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//            ft.replace(R.id.fragmentContainer, fragment, "ProductListFragment");
////                ft.addToBackStack(null);
//            ft.commit();
//        }
//
//        else if(productID != null && productID.toString().length() >0 && productName != null && productName.toString().length() > 0){
//            Fragment fragment = new ProductInfoFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString("prodID", productID.toString());
//            bundle.putString("catName", productName.toString());
//            fragment.setArguments(bundle);
//            FragmentManager fm = getFragmentManager();
//            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            FragmentTransaction ft = fm.beginTransaction();
////                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,R.anim.slide_in_right, R.anim.slide_out_left);
//            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//            ft.replace(R.id.fragmentContainer, fragment, "ProductInfoFragment");
////                ft.addToBackStack(null);
//            ft.commit();
//        }else if(searchKeyword != null && searchKeyword.toString().length() >0){
//            changeToolBarText("Search ''" + searchKeyword.toString() + "''");
//            changeToolBarTextView(true);
//            changeToolBarImageView(false);
//            Fragment fragment = new ProductListFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString("fromHome","Search");
//            bundle.putString("search", searchKeyword.toString());
//            fragment.setArguments(bundle);
//
//            FragmentManager fragmentManager = getFragmentManager();
//            SearchFragment searchFragment = (SearchFragment)fragmentManager.findFragmentByTag("SearchFragment");
//
//            if (searchFragment != null && searchFragment.isVisible()) {
//                // add your code here
//                FragmentTransaction trans1 = fragmentManager.beginTransaction();
//                trans1.remove(searchFragment);
//                trans1.commit();
//                fragmentManager.popBackStack();
//            }
//            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            FragmentTransaction ft = fragmentManager.beginTransaction();
////                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,R.anim.slide_in_right, R.anim.slide_out_left);
//            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//            ft.replace(R.id.fragmentContainer, fragment, "ProductInfoFragment");
////                ft.addToBackStack(null);
//            ft.commit();
//            System.out.print("Heloooooooo cariiiiii" + searchKeyword.toString());
//        }else
//        {
//            if (categoryID != null && categoryID.toString().length() > 0 && categoryName != null && categoryName.toString().length() > 0) {
//                SharedPreferences.Editor editor = pref.edit();
//                editor.putString("comeFromNotification", "1");
//                editor.putString("categoryID", categoryID.toString());
//                editor.putString("categoryName", categoryName.toString());
//                editor.apply();
//                System.out.println("COME FROM INSIDER");
//                Fragment fragment = new ProductListFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("prodID", categoryID.toString());
//                bundle.putString("catName", categoryName.toString());
//                bundle.putString("fromHome", "Home");
//                fragment.setArguments(bundle);
//                FragmentManager fm = getFragmentManager();
//                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                FragmentTransaction ft = fm.beginTransaction();
////                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,R.anim.slide_in_right, R.anim.slide_out_left);
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                ft.replace(R.id.fragmentContainer, fragment, "ProductListFragment");
////                ft.addToBackStack(null);
//                ft.commit();
//            } else {
//                SharedPreferences.Editor editor = pref.edit();
//                editor.putString("comeFromNotification", "0");
//                editor.putString("categoryID", "");
//                editor.putString("categoryName", "");
//                editor.apply();
//                System.out.println("NOT COME FROM INSIDER");
//
//            }
//        }
    }

    @Override
    public void sendInputSaveItem(String data ,String data2, String data3) {


        FragmentManager fm = getFragmentManager();
        ProductInfoFragment productInfo = (ProductInfoFragment)fm.findFragmentByTag("ProductInfoFragment");

        if(productInfo == null)
        {

            System.out.println("get here");

        }

        else
            {

                productInfo.saveForLater(data,data2,data3);


            }

    }

    @Override
    public void sendInput(String data, String data2, String data3) {


        FragmentManager fm = getFragmentManager();
        ProductInfoFragment productInfo = (ProductInfoFragment) fm.findFragmentByTag("ProductInfoFragment");

        productInfo.addToCart(data, data2, data3);

    }

    @Override
    public void sendInput(String data, String data2) {


                selectedCategory = data2;

                Fragment fragment = new ProductListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("prodID", data2);
                bundle.putString("catName",data);
                bundle.putString("fromHome", "Home");
                fragment.setArguments(bundle);

                openFragment(fragment, "ProductListFragment");


    }

    @Override
    public void sendInput2(String data, String data2) {

        Fragment fragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("prodID", data2);
        bundle.putString("catName",data);
        bundle.putString("fromHome", "Home");
        fragment.setArguments(bundle);

        openFragment(fragment, "ProductListFragment");

    }

    @Override
    public void sendInput3(String data, String data2) {

        if(data.contains("Shipping To"))
        {
            openFragment(new ChangeCountryFragment(), "ChangeCountryFragment");
        }


    if(data.equals("Visit Our Store"))

        {
            openFragment(new VisitOurStoreFragment(), "VisitOurStoreFragment");
        }
    if(data.equals("My Account"))
        {
            openFragment(new MyAccountFragment(), "MyAccountFragment");
        }

     if(data.equals("Login"))
        {

            openFragment(new LoginFragment(), "LoginFragment");

        }

     if(data.equals("Poplook Loyalty Rewards"))
        {

            openFragment(new LoyaltyMainPageFragment(), "LoyaltyMain");

        }

        if(data.equals("Privacy Policy"))
        {

            Fragment fragment = new PrivacyPolicyFragment();
            Bundle bundle = new Bundle();
            bundle.putString("fromSignUp", "Nope");
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = this.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }

      if(data.equals("Settings")) {

          openFragment(new SettingsFragment(), "SettingsFragment");

      }

     if(data.equals("My Member ID"))
        {

            openFragment(new MyMemberIDFragment(), "MemberIDFragment");

        }

     if(data.equals("Facebook"))
     {
         Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
         String facebookUrl = getFacebookPageURL(this);
         facebookIntent.setData(Uri.parse(facebookUrl));
         this.startActivity(facebookIntent);

     }

        if(data.equals("Twitter"))
        {
            Intent intent = null;
            try {
                // get the Twitter app if possible
                this.getPackageManager().getPackageInfo("com.twitter.android", 0);
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=50962757"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            } catch (Exception e) {
                // no Twitter app, revert to browser
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/poplookshop"));
            }
            this.startActivity(intent);


        }

        if(data.equals("Instagram"))
        {

            Uri uri = Uri.parse("http://instagram.com/_u/poplook");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

            likeIng.setPackage("com.instagram.android");

            try {
                this.startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                this.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/poplook")));
            }


        }

     if(data.equals("Log Out"))
        {

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Log Out")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            SharedPreferences pref = MainActivity.this.getSharedPreferences("MyPref", 0);
                            SharedPreferences.Editor editor = pref.edit();

                            editor.putString("UserID", "");
                            editor.putString("CartID", "");
                            editor.putString("LanguageID", "1");
                            editor.putString("Name", "");
                            editor.putString("LastName", "");
                            editor.putString("Email", "");
                            editor.putString("WishlistID", "");
                            editor.putString("cartItem", "0");
                            editor.putString("wishlistItem", "0");
                            editor.putString("loyalty_id", "");
                            editor.putString("popup_show", "");
                            editor.putString("tier_level","");
                            editor.putString("entity_id", "");

                            editor.apply();

                            Insider.Instance.tagEvent("logout").build();

                            new Handler().post(new Runnable() {

                                @Override
                                public void run() {
                                    Intent intent = MainActivity.this.getIntent();
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    MainActivity.this.overridePendingTransition(0, 0);
                                    MainActivity.this.finish();

                                    MainActivity.this.overridePendingTransition(0, 0);
                                    startActivity(intent);
                                }
                            });

                        }

                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }

    }

    public static String FACEBOOK_URL = "https://www.facebook.com/POPLOOK";
    public static String FACEBOOK_PAGE_ID = "138171308208";

    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://profile/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }


    @Override
    public void selectedDeliveryAdd(Integer deliveryAdd, Integer billingAdd) {

        FragmentManager fm = getFragmentManager();
        NewAddressBillingFragment addressFragment = (NewAddressBillingFragment)fm.findFragmentByTag("NewAddressBillingFragment");

        if(addressFragment == null)
        {

            System.out.println("get here");

        }

        else
        {

            addressFragment.updateAddressDelivery(deliveryAdd,billingAdd);

        }

    }

    @Override
    public void selectedBillingAdd(Integer deliveryAdd, Integer billingAdd) {

        FragmentManager fm = getFragmentManager();
        NewAddressBillingFragment addressFragment = (NewAddressBillingFragment)fm.findFragmentByTag("NewAddressBillingFragment");

        if(addressFragment == null)
        {

            System.out.println("get here");

        }

        else
        {

            addressFragment.updateAddressBilling(deliveryAdd,billingAdd);

        }

    }
}



