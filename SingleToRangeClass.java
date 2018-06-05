package TNTool;

import java.util.ArrayList;
import static TNTool.SharedFunctions.*;
public class SingleToRangeClass {
	public static String toRange(String input) {
		StringBuilder outputText = new StringBuilder();
		int lines = countLines(input);
		input = sortNumbers(input);
		ArrayList<Long> inputArray = stringToArray(input);
		long tmpRangeStart = inputArray.get(0);
		long tmpRangeEnd;

		for (int i = 1; i < lines;i++) {
			if (inputArray.get(i - 1) != inputArray.get(i) - 1) {

				tmpRangeEnd = inputArray.get(i - 1);
				if (tmpRangeEnd == tmpRangeStart) {
					outputText.append(tmpRangeStart);
					outputText.append("\n");
				} else {
					outputText.append(tmpRangeStart);
					outputText.append(" - ");
					outputText.append(tmpRangeEnd);
					outputText.append("\n");
				}
				tmpRangeStart = inputArray.get(i);
			}
		}
		tmpRangeEnd = inputArray.get(lines - 1);
		if (tmpRangeEnd == tmpRangeStart) {
			outputText.append(tmpRangeStart);
			outputText.append("\n");
		} else {
			outputText.append(tmpRangeStart);
			outputText.append(" - ");
			outputText.append(tmpRangeEnd);
			outputText.append("\n");
		}

		return outputText.toString();
	}
}
