package com.onshop.yamuna.onshoppee.Models;

public class Rating {
    private String userphone;
    private String spiceName;
    private String rateValue;
    private String comment;

    public Rating() {
    }

    public Rating(String userphone, String spiceId, String rateValue, String comment) {
        this.userphone = userphone;
        this.spiceName = spiceId;
        this.rateValue = rateValue;
        this.comment = comment;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getSpiceName() {
        return spiceName;
    }

    public void setSpiceName(String spiceName) {
        this.spiceName = spiceName;
    }

    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
