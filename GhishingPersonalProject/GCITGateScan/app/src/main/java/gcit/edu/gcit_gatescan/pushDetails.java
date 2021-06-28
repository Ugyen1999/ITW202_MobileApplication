package gcit.edu.gcit_gatescan;

public class pushDetails {
    public String name, contact,date,time;
    public pushDetails(){}

    public pushDetails(String name, String contact, String date,String time) {
      this.name = name;
      this. contact = contact;
      this.date= date;
       this. time=time;

    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }


}
