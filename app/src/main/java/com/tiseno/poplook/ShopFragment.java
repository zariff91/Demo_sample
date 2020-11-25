package com.tiseno.poplook;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment {


    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_shop, container, false);
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
        String comeFromNotification = pref.getString("comeFromNotification", "0");
        String categoryID = pref.getString("categoryID", "");
        String categoryName = pref.getString("categoryName", "");
        String searchKeyword = pref.getString("searchKeyword","");
        String productID = pref.getString("productID","");
        String productName = pref.getString("productName","");
        try{
            if(comeFromNotification.equals("0")) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.replace(R.id.fragmentContainer, new HomeFragment(), "HomeFragment");
//                ft.addToBackStack(null);
                ft.commit();
            }else{
                if(!categoryID.equals("") && !categoryName.equals("")){

                    Fragment fragment = new ProductListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("prodID", categoryID);
                    bundle.putString("catName",categoryName);
                    bundle.putString("fromHome", "Home");
                    fragment.setArguments(bundle);
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.replace(R.id.fragmentContainer, fragment, "ProductListFragment");
//                ft.addToBackStack(null);
                    ft.commit();
                }else if(!searchKeyword.equals(""))
                {
                    Fragment fragment = new ProductListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("fromHome","Search");
                    bundle.putString("search", searchKeyword.toString());
                    fragment.setArguments(bundle);

                    FragmentManager fragmentManager = getFragmentManager();
                    SearchFragment searchFragment = (SearchFragment)fragmentManager.findFragmentByTag("SearchFragment");

                    if (searchFragment != null && searchFragment.isVisible()) {
                        // add your code here
                        FragmentTransaction trans1 = fragmentManager.beginTransaction();
                        trans1.remove(searchFragment);
                        trans1.commit();
                        fragmentManager.popBackStack();
                    }
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    FragmentTransaction ft = fragmentManager.beginTransaction();
//                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,R.anim.slide_in_right, R.anim.slide_out_left);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.replace(R.id.fragmentContainer, fragment, "SearchFragment");
//                ft.addToBackStack(null);
                    ft.commit();

                }else if(!productID.equals("") && !productName.equals("")){
                    Fragment fragment = new ProductInfoFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("prodID", productID);
                    bundle.putString("catName", productName);
                    fragment.setArguments(bundle);
                    FragmentManager fm = getFragmentManager();
                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    FragmentTransaction ft = fm.beginTransaction();
//                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,R.anim.slide_in_right, R.anim.slide_out_left);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.replace(R.id.fragmentContainer, fragment, "ProductInfoFragment");
//                ft.addToBackStack(null);
                    ft.commit();
                }else{
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.replace(R.id.fragmentContainer, new HomeFragment(), "HomeFragment");
//                ft.addToBackStack(null);
                    ft.commit();
                }


            }

            // Inflate the layout for this fragment
            return v;
        }catch (Exception e){
            return v;
        }

    }


}
