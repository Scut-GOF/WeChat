package gof.scut.cwh.models.object;

public class LightIdObj {
	String id;

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

	public LightIdObj(String id) {

		this.id = id;
	}

	public LightIdObj(String id, String name) {

		this.id = id;
		this.name = name;
	}

	String name;
}
