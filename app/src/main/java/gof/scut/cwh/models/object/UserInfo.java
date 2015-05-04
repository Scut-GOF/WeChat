package gof.scut.cwh.models.object;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * Created by zm on 2015/5/4.
 */
public class UserInfo {
    private final static String TAG = UserInfo.class.getSimpleName();
    private static UserInfo instance;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static void init(Context context){
        if(instance ==null){
            sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            instance = new UserInfo();
            initUserInfo();
        }
    }

    public static UserInfo getInstance(){
        return instance;
    }

    private static void initUserInfo(){

        Set<String> set;
        instance.userId = sharedPreferences.getString("userId","");
        instance.name = sharedPreferences.getString("name","");
        instance.address = sharedPreferences.getString("address","");
        instance.notes = sharedPreferences.getString("notes","");
        set = sharedPreferences.getStringSet("tels",new HashSet<String>());

        for(String phone : set){
            instance.tels.add(phone);
        }
    }

    private String userId;
    private String name;
    private ArrayList<String> tels = new ArrayList<>();
    private String address;
    private String notes;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        editor.putString("userId", userId);
        editor.commit();
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        editor.putString("name", name);
        editor.commit();
        this.name = name;
    }

    public ArrayList<String> getTels() {
        return tels;
    }

    public void setTels(ArrayList<String> tels) {
        Set<String> set = new HashSet<>();
        for(String tel:tels){
            set.add(tel);
        }
        editor.putStringSet("tels", set);
        editor.commit();
        this.tels = tels;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        editor.putString("address", address);
        editor.commit();
        this.address = address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        editor.putString("notes", notes);
        editor.commit();
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", tels=" + tels +
                ", address='" + address + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
