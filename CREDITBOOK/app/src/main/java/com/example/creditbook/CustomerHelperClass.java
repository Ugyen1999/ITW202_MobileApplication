package com.example.creditbook;

public class CustomerHelperClass {
    String customeremail,customername,customerpassword,customerdepartment,customerid;

    public CustomerHelperClass(){

    }
    public CustomerHelperClass(String customername, String customeremail, String customerpassword, String customerdepartment, String customerid){
        this.customeremail=customeremail;
        this.customername=customername;
        this.customerpassword=customerpassword;
        this.customerdepartment=customerdepartment;
        this.customerid=customerid;
    }



    public String getcustomerdepartment(){return customerdepartment;}
    public void setcustomerdepartment(String customerdepartment){this.customerdepartment=customerdepartment;}

    public String getcustomeremail(){return customeremail;}
    public void setcustomeremail(String customeremail){
        this.customeremail=customeremail;
    }

    public String getcustomername(){return customername;}
    public void setcustomername(String customername){this.customername=customername;}

    public String getcustomerpassword(){return customerpassword;}
    public void setcustomerpassword(String customerpassword){this.customerpassword=customerpassword;}

    public String getcustomerid(){return customerid;}
    public void setcustomerid(String customerid){this.customerid=customerid;}

}


