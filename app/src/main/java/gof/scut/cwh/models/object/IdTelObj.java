package gof.scut.cwh.models.object;

/**
 * Created by cwh on 15-5-2.
 */
public class IdTelObj {
	String id, tel;

	public IdTelObj(String id, String tel) {
		this.id = id;
		this.tel = tel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
}
