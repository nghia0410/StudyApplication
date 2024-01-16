package uef.com.studyapplication.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseUser;

public class User implements Parcelable {
    ///Id User lay tu Firebase authentication
    private String uuid;
    private String username;
    //    private String password;
    private String fullname;
    private String image;
    private String email;
    private String phone;
    private String type;

    protected User(Parcel in) {
        uuid = in.readString();
        username = in.readString();
        fullname = in.readString();
        image = in.readString();
        email = in.readString();
        phone = in.readString();
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid);
        dest.writeString(username);
        dest.writeString(fullname);
        dest.writeString(image);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getFullname() {
        return fullname;
    }
    public String getNameOrEmail(){
        if(fullname == null || fullname.isEmpty())
            return  email;
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public String getPassword() {
//        return password;
//    }

//    public void setPassword(String password) {
//        this.password = password;
//    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
//        this.password = password;
    }

    public User(FirebaseUser firebaseUser) {
        this.uuid = firebaseUser.getUid();
        this.username = firebaseUser.getEmail();
//        this.password = password;
        this.email = firebaseUser.getEmail();
        this.fullname = firebaseUser.getDisplayName();
        this.phone = firebaseUser.getPhoneNumber();
        this.type = "user";
        if (firebaseUser.getPhotoUrl() != null)
            this.image = firebaseUser.getPhotoUrl().toString();
        else this.image = "";
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid='" + uuid + '\'' +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", image='" + image + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}