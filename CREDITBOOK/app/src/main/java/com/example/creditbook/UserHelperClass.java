package com.example.creditbook;

public class UserHelperClass {
    String adminemail,adminname,adminpassword,adminliscene,adminphone;

    public UserHelperClass(){

    }
    public UserHelperClass(String adminname, String adminemail, String adminpassword, String adminliscene, String adminphone){
        this.adminemail=adminemail;
        this.adminname=adminname;
        this.adminpassword=adminpassword;
        this.adminliscene=adminliscene;
        this.adminphone=adminphone;
    }

    public String getadminemail(){return adminemail;}
    public void setadminemail(String adminemail){
        this.adminemail=adminemail;
    }

    public String getadminname(){return adminname;}
    public void setadminname(String adminname){this.adminname=adminname;}

    public String getadminpassword(){return adminpassword;}
    public void setAdminpassword(String adminpassword){this.adminpassword=adminpassword;}

    public String getAdminliscene(){return adminliscene;}
    public void setAdminliscene(String adminliscene){this.adminliscene=adminliscene;}

    public String getAdminphone(){return adminphone;}
    public void setAdminphone(String adminphone){this.adminphone=adminphone;}
}

