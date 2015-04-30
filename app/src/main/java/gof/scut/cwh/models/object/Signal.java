package gof.scut.cwh.models.object;

import java.io.Serializable;

/**
 * Created by cwh on 15-4-29.
 */
public class Signal implements Serializable {
	public static String NAME = "signal";
	private int from;
	private int to;

	public Signal(int from) {
		this.from = from;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}


	public Signal(int from, int to) {

		this.from = from;
		this.to = to;
	}

}
