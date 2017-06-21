package com.atreya.pagination;

/**
 * Created by atreya on 31/3/17.
 */

public class ProduceOnSaleData {
    public String productName;
    public String rate;
    public String address;
    public String latitude;
    public String quality;
    public String longitude;
    public String pk,r_unit;
    public  String quantity;
    public String mainCategory;
    public  String imgUrl,q_unit,image_id;
    public  String availabilityUpto;
    public  String availabilityFrom;
    public  String locId;

    public  String buyer_num;
    public  String buyername;
    public  String buyer_loc;
    public  String buyerpincode;

    public  String buyer_city;



    public String getBuyer_num() {
        return buyer_num;
    }

    public void setBuyer_num(String buyer_num) {
        this.buyer_num = buyer_num;
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername;
    }

    public String getBuyer_loc() {
        return buyer_loc;
    }

    public void setBuyer_loc(String buyer_loc) {
        this.buyer_loc = buyer_loc;
    }

    public String getBuyerpincode() {
        return buyerpincode;
    }

    public void setBuyerpincode(String buyerpincode) {
        this.buyerpincode = buyerpincode;
    }


    public  String date;
    public  String sellerId;
   public String available_quantity;
    public String aq_unit,SellerMob;
    String min_orderQty,min_orderUnit;
    int status;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getMin_orderQty() {
        return min_orderQty;
    }

    public void setMin_orderQty(String min_orderQty) {
        this.min_orderQty = min_orderQty;
    }

    public String getMin_orderUnit() {
        return min_orderUnit;
    }

    public void setMin_orderUnit(String min_orderUnit) {
        this.min_orderUnit = min_orderUnit;
    }

    public String getAq_unit() {
        return aq_unit;
    }

    public void setAq_unit(String aq_unit) {
        this.aq_unit = aq_unit;
    }

    public String getAvailable_quantity() {
        return available_quantity;
    }

    public void setAvailable_quantity(String available_quantity) {
        this.available_quantity = available_quantity;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    public String getLocId() {
        return locId;
    }

    public void setLocId(String locId) {
        this.locId = locId;
    }

    public String getAvailabilityFrom() {
        return availabilityFrom;
    }

    public void setAvailabilityFrom(String availabilityFrom) {
        this.availabilityFrom = availabilityFrom;
    }

    public String getAvailabilityUpto() {
        return availabilityUpto;
    }

    public void setAvailabilityUpto(String availabilityUpto) {
        this.availabilityUpto = availabilityUpto;
    }

    public String getproductName() {
        return productName;
    }

    public void setproductName(String productName) {
        this.productName = productName;
    }

    public String getquality() {
        return quality;
    }

    public void setquality(String quality) {
        this.quality = quality;
    }
    public String getquantity() {
        return quantity;
    }

    public void setquantity(String quantity) {
        this.quantity = quantity;
    }

    public String getrate() {
        return rate;
    }

    public void setrate(String rate) {
        this.rate = rate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getlat() {
        return latitude;
    }

    public void setlat(String latitude) {
        this.latitude = latitude;
    }
    public String getlongi() {
        return longitude;
    }

    public void setlongi(String longitude) {
        this.longitude = longitude;
    }
    public void setr_unit(String r_unit) {
        this.r_unit = r_unit;
    }
    public String getr_unit() {
        return r_unit;
    }

    public void setimgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getimgUrl() {
        return imgUrl;
    }
    public void setq_unit(String q_unit) {
        this.q_unit = q_unit;
    }
    public String getq_unit() {
        return q_unit;
    }

    public void setpk(String pk) {
        this.pk = pk;
    }
    public String getpk() {
        return pk;
    }


    public void setimage_id(String image_id) {
        this.image_id = image_id;
    }
    public String getimage_id() {
        return image_id;
    }

    public void setSellerMob(String SellerMob) {
        this.SellerMob = SellerMob;
    }
    public String getSellerMob() {
        return SellerMob;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
