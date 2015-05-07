package gof.scut.cwh.models.object;

import java.io.Serializable;
import java.util.ArrayList;


public class IdObj implements Serializable {
	private int id;
	private String name;
	private String lPinYin;
	private String sPinYin;
    private ArrayList<String> tels = new ArrayList<>();
	private String address;
	private String notes;

	public IdObj(int id) {
		this.id = id;
	}

	public IdObj() {

	}

    public IdObj(String name, String id) {
        this.name = name;
        this.id = Integer.parseInt(id);
    }

    public IdObj(String name, String address, String notes) {
        this.name = name;
        this.address = address;
        this.notes = notes;
    }

	public IdObj(int id, String name, String lPinYin, String sPinYin, String address, String notes) {
		this.id = id;
		this.name = name;
		this.lPinYin = lPinYin;
		this.sPinYin = sPinYin;

		this.address = address;
		this.notes = notes;
	}

    public void setId(int id) {
		this.id = id;
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

	public String getAddress() {
		return address;
	}

	public String getNotes() {
		return notes;
	}

    public ArrayList getTels() {
        return tels;
    }

    public void setTels(ArrayList<String> tels) {
        this.tels = tels;
    }

	@Override
	public String toString() {
		return "IdObj{" +
				"id=" + id +
				", name='" + name + '\'' +
				", lPinYin='" + lPinYin + '\'' +
				", sPinYin='" + sPinYin + '\'' +
                ", tels='" + tels.toString() + '\'' +
				", address='" + address + '\'' +
				", notes='" + notes + '\'' +
				'}';
	}
}