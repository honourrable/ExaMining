package tr.edu.OnurPorsuk;

import java.util.ArrayList;
import java.io.Serializable;

public class User implements Serializable{

    private String fName;
    private String lName;
    private String phoneNumber;
    private String email;
    private String password;
    private String photoPath;
    private static final long serialVersionUID = -45638982928391L;

    public User(String fName, String lName, String email, String phoneNumber, String password, String photoPath) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.photoPath = photoPath;
    }

    public String getFName() {return fName;}
    public void setFName(String fName) {this.fName = fName;}
    public String getLName() {return lName;}
    public void setLName(String lName) {this.lName = lName;}
    public String getEmail() {return email;}
    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public void setEmail(String email) {this.email = email;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getPhotoPath() {return photoPath;}
    public void setPhotoPath(String photoPath) {this.photoPath = photoPath;}

    public static ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Onur","Porsuk","on","+90 554 568 8703",
                "123", Integer.toString(R.drawable.user_onur)));
        users.add(new User("Kadir","Tuna","kadirp@hotmail.com",
                "+90 531 023 4554", "kDr-3443", Integer.toString(R.drawable.user_kadir)));
        users.add(new User("Sevim","Uslu","us.sevim@gmail.com",
                "+90 531 023 4554", "darave??T", Integer.toString(R.drawable.user_sevim)));
        users.add(new User("Sertap","Kaya","serta_pp@hotmail.com",
                "+90 535 775 1232", "123Sert321.", Integer.toString(R.drawable.user_sertap)));
        users.add(new User("Rena","Ovacı","rena_aner@outlook.com",
                "+90 545 128 1409",  "-9876Ar-", Integer.toString(R.drawable.user_rena)));
        users.add(new User("Salih","Yıldız","slhyld@hotmail.com",
                "+90 546 602 2322", "Slhyl&1999", Integer.toString(R.drawable.user_salih)));
        users.add(new User("Murat","Avcı","muratAV@gmail.com",
                "+90 545 802 6596",  "m.M12456", Integer.toString(R.drawable.user_murat)));
        users.add(new User("Melek","Tamer","mlktmr@hotmail.com",
                "+90 532 747 1008",  "9800.Mmlk", Integer.toString(R.drawable.user_melek)));
        users.add(new User("Ali","Aktaş","ALIa@hotmail.com",
                "+90 531 555 0119",  "alIa.77%", Integer.toString(R.drawable.user_ali)));
        users.add(new User("Pelin","Sarnıç","pelin.12@outlook.com",
                "+90 531 023 4554", "12.pelinnnN", Integer.toString(R.drawable.user_pelin)));
        return users;
    }

}
