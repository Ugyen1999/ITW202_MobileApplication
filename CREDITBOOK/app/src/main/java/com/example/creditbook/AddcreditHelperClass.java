package com.example.creditbook;

public class AddcreditHelperClass {
    String amount,date,userid,username;

    public AddcreditHelperClass(){

    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AddcreditHelperClass(String amount , String date, String userid , String username){
        this.amount=amount;
        this.date=date;
        this.userid = userid;
        this.username = username;

    }
}
