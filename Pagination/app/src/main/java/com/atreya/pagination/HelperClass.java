package com.atreya.pagination;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bitjini.krishi_udyog.AggregatorModule.AggregatorHome;
import com.bitjini.krishi_udyog.AggregatorModule.BuyFragment;
import com.bitjini.krishi_udyog.DataClasses.ProduceOnSaleData;
import com.bitjini.krishi_udyog.FarmerModule.FarmerHome;
import com.bitjini.krishi_udyog.FarmerModule.ProductDetail;
import com.bitjini.krishi_udyog.FarmerModule.Select_Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.bitjini.krishi_udyog.SharedPref.sharedpreferences;

/**
 * Created by bitjini on 13/4/17.
 */

public class HelperClass {

    ArrayList<ProduceOnSaleData> productList=new ArrayList<>();
    static ArrayList<String> categoryArray=new ArrayList<>();
    Context context;
    public int status=0;

    public HelperClass(Context context) {
            this.context=context;
    }

    public void SavePreferences(String key, String value) {
// TODO Auto-generated method stub
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public void SavePreferencesInt(String key, int value) {
// TODO Auto-generated method stub
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public ArrayList<ProduceOnSaleData> parseJson(String result) {
        String quality, quantity, rate,availability_date,location_id,image_id, address="N/A", lat = "", longi = "", availabilityUpto, mainCategory="", sellerID, available_quantity, aq_unit;
        String sc_type="", r_unit, q_unit, image_url, pk,seller_num = "",min_orderQty,min_orderUnit;
        ProduceOnSaleData obj = null;
        try {
//            Log.e("result", "" + result);
            if (result != null) {

                JSONObject json = new JSONObject(result);

                if (json.has("status")) {   // check if status obj is present
                    status = json.getInt("status");
                }

                else {

                    if(json.has("count")) {
                        int countOfFeeds = json.getInt("count");
                        Log.e("count of feeds=", "" + countOfFeeds);
                    }
                    JSONArray json_array = json.getJSONArray("data");
                    if (json_array.length() != 0) {
                        for (int i = 0; i < json_array.length(); i++) {
                            JSONObject jobj = json_array.getJSONObject(i);
                            image_url = jobj.getString("image_details");

                            JSONObject productj_obj = jobj.getJSONObject("product_details");
                            quality = productj_obj.getString("quality");
                            quantity = productj_obj.getString("quantity");
                            rate = productj_obj.getString("rate");
                            r_unit = productj_obj.getString("r_unit");
                            q_unit = productj_obj.getString("q_unit");
                            min_orderQty=productj_obj.getString("min_order_quantity");
                            min_orderUnit=productj_obj.getString("mq_unit");
                            pk = productj_obj.getString("pk");
                            sellerID = productj_obj.getString("user_id");
                            availabilityUpto = productj_obj.getString("available_upto");
                            available_quantity = productj_obj.getString("available_quantity");// avail qty
                            aq_unit = productj_obj.getString("aq_unit");// avail qty
                            location_id = productj_obj.getString("location_id");
                            image_id = productj_obj.getString("image_id");
                            availability_date = productj_obj.getString("availability_date");

                            JSONObject subCatj_array = jobj.getJSONObject("sub_category_details");
                            sc_type = subCatj_array.getString("sc_type");

                            JSONObject mainCatObj = jobj.getJSONObject("category");
                            mainCategory = mainCatObj.getString("c_type");

                            if(jobj.has("seller")) {
                                JSONObject seller_info = jobj.getJSONObject("seller");
                                seller_num = seller_info.getString("mobile");
                            }


                            JSONObject locObjArray = jobj.getJSONObject("location_details");
//                            Log.e("address"," "+locObjArray);
                            if (locObjArray.has("address")) {
                               String city  = locObjArray.getString("city");
                               String state  = locObjArray.getString("state");
                                lat = locObjArray.getString("latitude");
                                longi = locObjArray.getString("longitude");
                                Log.e("lat", lat);

                            } else {
                                if(locObjArray.has("city")) {

                                    String city = locObjArray.getString("city");
                                    String state = locObjArray.getString("state");
                                    address = city + " " + state;
                                }
                            }


                            double aq = Double.parseDouble(available_quantity);//Check the available quantity is greater than 0
                            if (aq >= 1) {
                                obj = new ProduceOnSaleData();

                                obj.setproductName(sc_type);
                                obj.setquality(quality);
                                obj.setquantity(quantity);
                                obj.setAvailable_quantity(available_quantity);
                                obj.setrate(rate);
                                obj.setAq_unit(aq_unit);
                                obj.setAvailabilityUpto(availabilityUpto);
                                obj.setAddress(address);
                                obj.setlat(lat);
                                obj.setlongi(longi);
                                obj.setpk(pk);
                                obj.setLocId(location_id);
                                obj.setAvailabilityFrom(availability_date);
                                obj.setimage_id(image_id);
                                obj.setSellerId(sellerID);
                                obj.setr_unit(r_unit);
                                obj.setq_unit(q_unit);
                                obj.setMainCategory(mainCategory);
                                obj.setSellerMob(seller_num);
                                obj.setimgUrl(image_url);
                                obj.setMin_orderUnit(min_orderUnit);
                                obj.setMin_orderQty(min_orderQty);
                                productList.add(obj);

                            }

                        }
                    }

                }
            }
        } catch(JSONException e){
            e.printStackTrace();
        }

        return productList;

    }





    public void clearStaticValues()
    {
        FarmerHome.name="";
        Select_Category.NewItemAdded=0;
        Select_Category.fromAggrtr=0;

        AggregatorHome.tabSelected=0;
        BuyFragment.duplicateproductListforBuyers.clear();
        BuyFragment.isFilterEnabled=false;

        ProductDetail.fromAggregator=0;

        BuyerHome.isFilterEnabled=false;
        BuyerHome.duplicateproductListforBuyers.clear();

        FiltersLayout.location.clear();
        FiltersLayout.filterStringsList.clear();


    }
}
