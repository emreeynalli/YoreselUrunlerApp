package com.example.yreselrnler;

public class Basket {

    String append_count;
    String product_name;
    String product_cost;
    String product_count;
    String product_image;


    public Basket(){

    }

    public Basket( String append_count, String product_name, String product_cost, String product_count, String product_image) {
        this.product_name = product_name;
        this.product_cost = product_cost;
        this.product_count = product_count;
        this.product_image = product_image;
        this.append_count = append_count;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_cost() {
        return product_cost;
    }

    public void setProduct_cost(String product_cost) {
        this.product_cost = product_cost;
    }

    public String getProduct_count() {
        return product_count;
    }

    public void setProduct_count(String product_count) {
        this.product_count = product_count;
    }

    public String getAppend_count() {
        return append_count;
    }

    public void setAppend_count(String append_count) {
        this.append_count = append_count;
    }
}
