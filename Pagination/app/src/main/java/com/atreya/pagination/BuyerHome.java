package com.atreya.pagination;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class BuyerHome extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    RecyclerView products;
    ProgressDialog progressDialog;
    ProductsDetailsAdapter adapter;
    SwipeRefreshLayout swipeView;
    SearchView searchProduce;
    FloatingActionMenu rightLabels;
    RelativeLayout relativeLayout;
    View view1;
    AppBarLayout appBarLayout;

    public static ArrayList<ProduceOnSaleData> productListforBuyers = new ArrayList<>();
    public static ArrayList<ProduceOnSaleData> duplicateproductListforBuyers = new ArrayList<>();
    public static ArrayList<ProduceOnSaleData> anotherproductList = new ArrayList<>();
    public static Boolean isFilterEnabled=false;
    static Boolean isfromFAB=false;
    int backPress=0,progress=0;

    HelperClass helperClass;
    GrantPermission gp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_home);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        helperClass=new HelperClass(BuyerHome.this);



        if (Connectivity.isConnected(BuyerHome.this)) {
        }else{
            Toast.makeText(BuyerHome.this,"check_network",Toast.LENGTH_SHORT).show();
        }
        initViews();
        showListView();
        initListners();
        initListeners2();

    }


    private void showListView() {

        String result=sharedpreferences.getString("BuyerResult", null);
        if(result==null)
        {
            getProductsforSale();
        }
        else
        {
            LoadData();
        }

    }

    private void LoadData() {
        String apiResult = sharedpreferences.getString("BuyerResult", "");
        setValueToAdapter(apiResult);
    }

    private void initViews()
    {
        swipeView = (SwipeRefreshLayout) findViewById(R.id.swipeDownRefresh);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);

        searchProduce = (SearchView)findViewById(R.id.searchProduce);
        relativeLayout=(RelativeLayout)findViewById(R.id.filterLayout);
        view1 =(View) findViewById(R.id.view1);

        products = (RecyclerView) findViewById(R.id.productList);
        products.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        products.setLayoutManager(layoutManager);

    }








    @Override
    public void onRefresh() {
        swipeView.setRefreshing(true);
        Log.d("Swipe", "Refreshing Number");
        refreshData();
        backPress=0;
        // avoid focus on searchview
        searchProduce.clearFocus();

        swipeView.setRefreshing(false);
    }

    private void refreshData() {

        if (backPress == 0 && progress == 0) {
            backPress=1;
            if (Connectivity.isConnected(BuyerHome.this)) {
                clearArrays();

                getProductsforSale();
            } else {

                Toast.makeText(BuyerHome.this, R.string.check_network, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void clearArrays() {
        productListforBuyers.clear();
        if(adapter!=null)
            adapter.notifyDataSetChanged();
        duplicateproductListforBuyers.clear();

    }
    private void getProductsforSale()
    {
        progress=1;
        progressDialog = new ProgressDialog(BuyerHome.this,R.style.AppCompatAlertDialogStyle);
        if (progressDialog != null) {
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        new GetProductsOnSaleList()
        {

            @Override
            public void onPostExecute(String responseProductListforBuyers)
            {
                if (progressDialog.isShowing() && progressDialog != null) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
                progress=0;
                filterStringsList.clear();
                location.clear();
                if(responseProductListforBuyers!=null) {
                    setValueToAdapter(responseProductListforBuyers);// set value to adapter after parsing response
                    Log.e("data",""+responseProductListforBuyers);
                    isFilterEnabled=false;

                }else {

                    Toast.makeText(BuyerHome.this,R.string.failed_to_load,Toast.LENGTH_SHORT).show();
                    isFilterEnabled=false;
                }
            }

        }.execute(BaseURLs.getAllProductsSale+tokenSharedPref);
    }

    private void setValueToAdapter(String responseProductList) {
        clearArrays();
        isFilterEnabled = false;

        productListforBuyers=helperClass.parseJson(responseProductList);// parsing the response
        if (helperClass.status== 401) {
            createAlertDialog(); // authentication failure

        } else {
            if (!productListforBuyers.isEmpty()) {
                if (!isFilterEnabled) {
                    helperClass.SavePreferences("BuyerResult", responseProductList);
                }
                adapter = new ProductsDetailsAdapter(BuyerHome.this, productListforBuyers);
                products.setAdapter(adapter);

                setupSearchView();
            } else {

                Toast.makeText(BuyerHome.this,R.string.failed_to_load, Toast.LENGTH_SHORT).show();
                isFilterEnabled = false;

            }
        }


    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if ( TextUtils.isEmpty ( newText ) )
        {
            adapter.getFilter().filter("");
        }
        else {
            adapter.getFilter().filter(newText);

            Log.e("New text",newText);
        }
        return true;
    }


    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText("press_back_to_exit", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                    System.gc();
                }
            }, 3 * 1000);
        }
    }
    private void createAlertDialog() {
        new AlertDialog.Builder(BuyerHome.this)
                .setTitle("authentication_failed")
                .setMessage("please_login_again")
                .setPositiveButton("okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete

                        clearAllData();

                    }
                })
                .show();
    }

    private void clearAllData() {
        clearArrays();
        sharedpreferences.edit().clear().apply();
        sharedpreferences=null;
        helperClass.clearStaticValues();
        System.gc();

        Intent mainIntent = new Intent(BuyerHome.this,AppHomeActivity.class);
        startActivity(mainIntent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
        finish();
    }


    public static void setIsFromFAB(Boolean value){
        isfromFAB=value;

    }





    @Override
    protected void onPostResume() {
        super.onPostResume();

        Log.e("on resume called",""+2);

        if(isFilterEnabled)
        {
            getProductsforSale();
        }
    }
    public void sortProduct()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(BuyerHome.this);
        builder.setTitle("sort_by")
                .setItems(R.array.sort_array, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        rightLabels.close(true); // hide fab menu

                        if(which==0)
                        {
                            Log.e("you selected",""+which);
                        }
                        else if (which==1)
                        {
                            Log.e("you selected",""+which);
                            low_to_high();
                        }

                        //for high to low
                        else if (which==2)
                        {
                            Log.e("you selected",""+which);
                            high_to_low();
                        }

                    }
                });
        builder.show();
    }



    private void low_to_high()
    {
        Comparator<ProduceOnSaleData> comparator = new Comparator<ProduceOnSaleData>(){

            public int compare(ProduceOnSaleData emp1, ProduceOnSaleData emp2) {
                return Integer.parseInt(emp1.getrate())-Integer.parseInt(emp2.getrate());
            }

        };
        Collections.sort(productListforBuyers,comparator);
        Log.e("sorted??",""+productListforBuyers);
        anotherproductList = productListforBuyers;

        adapter = new ProductsDetailsAdapter(BuyerHome.this, productListforBuyers);
        products.setAdapter(adapter);

        // avoid focus on searchview
        searchProduce.clearFocus();


//
//        Collections.sort(productListforBuyers, new Comparator<ProduceOnSaleData>() {
//            @Override
//            public int compare(ProduceOnSaleData o1, ProduceOnSaleData o2) {
//                return Integer.parseInt(o1.getrate())-Integer.parseInt(o2.getrate());
//            }
//        });
    }

    private void high_to_low()
    {
        Comparator<ProduceOnSaleData> comparator = new Comparator<ProduceOnSaleData>(){

            public int compare(ProduceOnSaleData emp1, ProduceOnSaleData emp2) {
                return Integer.parseInt(emp1.getrate())-Integer.parseInt(emp2.getrate());
            }

        };
        Collections.sort(productListforBuyers,comparator);
        Collections.reverse(productListforBuyers);
        Log.e("sorted??",""+productListforBuyers);
        anotherproductList = productListforBuyers;

        adapter = new ProductsDetailsAdapter(BuyerHome.this, productListforBuyers);
        products.setAdapter(adapter);

        // avoid focus on searchview
        searchProduce.clearFocus();
    }






}
