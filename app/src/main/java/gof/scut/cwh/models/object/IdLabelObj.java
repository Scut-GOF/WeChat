package gof.scut.cwh.models.object;

/**
 * Created by cwh on 15-5-2.
 */
public class IdLabelObj {
	String id, label;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public IdLabelObj(String id, String label) {

		this.id = id;
		this.label = label;
	}
}
