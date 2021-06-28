package gcit.edu.gcit_scan_login_registration;

public class userPush {
    public String Name,Email,Contact;

    public userPush(){}

    public userPush(String name, String email, String contact) {
        Name = name;
        Email = email;
        Contact = contact;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }
}
