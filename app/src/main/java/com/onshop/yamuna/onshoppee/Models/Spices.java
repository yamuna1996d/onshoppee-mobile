package com.onshop.yamuna.onshoppee.Models;

public class Spices {
    private String Name,Price,Image,Menuid;

    public Spices() {
    }

    public Spices(String name, String price, String image, String menuid) {
        Name = name;
        Price = price;
        Image = image;
        Menuid = menuid;
    }

    public String getMenuid() {
        return Menuid;
    }

    public void setMenuid(String menuid) {
        Menuid = menuid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
