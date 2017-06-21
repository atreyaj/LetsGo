package com.atreya.pagination;

/**
 * Created by atreya on 24/3/17.
 */

public class ProductOnSaleData
{
 public String productName;
    public String rate;
    public String address;

public String quality;

public String pk;
    public  String quantity;

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
}
