package gof.scut.cwh.models.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cwh on 15-4-30.
 */
public class LabelListObj implements Serializable {
	List<String> labels;

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public LabelListObj(List labels) {
		this.labels = labels;
	}

	public LabelListObj() {

		this.labels = new ArrayList<>();
	}

	public void addLabel(String label) {
		labels.add(label);
	}

	public void removeLabel(String label) {
		labels.remove(label);
	}

	public void removeAllMember() {
		labels.clear();
	}

	public boolean inList(String label) {
		return labels.contains(label);
	}

	@Override
	public String toString() {
		String strLabels = "";
		for (int i = 0; i < labels.size(); i++) {
			strLabels += labels.get(i);
		}
		return strLabels;
	}
}
