package gof.scut.cwh.models.object;

import java.io.Serializable;


public class IdObj implements Serializable {
    private int id;
    private String name;
    private String lPinYin;
    private String sPinYin;
    private String tel;
    private String address;
    private String notes;

    public IdObj(int id) {
        this.id = id;
    }

    public IdObj() {
    }

    public IdObj(int id, String name, String lPinYin, String sPinYin, String tel, String address, String notes) {
        this.id = id;
        this.name = name;
        this.lPinYin = lPinYin;
        this.sPinYin = sPinYin;
        this.tel = tel;
        this.address = address;
        this.notes = notes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public IdObj(String tel) {
        this.tel = tel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setlPinYin(String lPinYin) {
        this.lPinYin = lPinYin;
    }

    public void setsPinYin(String sPinYin) {
        this.sPinYin = sPinYin;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getlPinYin() {
        return lPinYin;
    }

    public String getsPinYin() {
        return sPinYin;
    }

    public String getTel() {
        return tel;
    }

    public String getAddress() {
        return address;
    }

    public String getNotes() {
        return notes;
    }
}