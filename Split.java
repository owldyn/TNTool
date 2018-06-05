package TNTool;
import java.util.ArrayList;
import static TNTool.SharedFunctions.*;


public class Split {
	private ArrayList<String> eachSplit = new ArrayList<String>();
	public Split(String input, int size) {
		splitStrings(input, size);
	}

	private void splitStrings(String input, int size) {
		StringBuilder tmpSplit = new StringBuilder();
		ArrayList<String> tmpArray = stringToStringArray(input);
		int numberOfSplits = (int)Math.ceil((double)tmpArray.size() / (double)size);
		int arrayIndex = 0;
		for (int i = 0; i < numberOfSplits; i++) {
			for (int j = 0; j < size; j++) {
				if (tmpArray.size() < arrayIndex + 1) {
					break;
				} else {
					tmpSplit.append(tmpArray.get(arrayIndex));
					tmpSplit.append("\n");
					arrayIndex++;
				}
			}
			eachSplit.add(tmpSplit.toString());
			tmpSplit.setLength(0);
		}
	}

	public String get(int index) {
		return eachSplit.get(index);
	}

	public int size() {
		return eachSplit.size();
	}

}
