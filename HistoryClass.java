package TNTool;

import java.util.ArrayList;

public class HistoryClass {
	private ArrayList<Object> outputArray = new ArrayList<>();

	HistoryClass(String i, String o, int c) {
		outputArray.add(i);
		outputArray.add(o);
		outputArray.add(String.valueOf(c));
	}

	public void set(String i, String o, int c) {
		outputArray.add(i);
		outputArray.add(o);
		outputArray.add(String.valueOf(c));
	}

	public ArrayList<Object> get() {
		return outputArray;
	}
}
