package gof.scut.cwh.models.object;

/**
 * Created by cwh on 15-5-1.
 */
public class SearchObj {

	String id;
	String name;
	String lPinyin;
	String sPinyin;
	String address;
	String note;

	public SearchObj(String id, String name, String lPinyin, String sPinyin, String address, String note, String label, String tel) {
		this.id = id;
		this.name = name;
		this.lPinyin = lPinyin;
		this.sPinyin = sPinyin;
		this.address = address;
		this.note = note;
		this.label = label;
		this.tel = tel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getlPinyin() {
		return lPinyin;
	}

	public void setlPinyin(String lPinyin) {
		this.lPinyin = lPinyin;
	}

	public String getsPinyin() {
		return sPinyin;
	}

	public void setsPinyin(String sPinyin) {
		this.sPinyin = sPinyin;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public SearchObj(String id) {

		this.id = id;
	}

	String label;
	String tel;
}
