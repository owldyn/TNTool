package TNTool;
import static TNTool.SharedFunctions.*;
import java.util.ArrayList;

public class TelcoveToolClass {
	private ArrayList<String> inputArray;
	private int sectionCount;
	private String betweenInput;
	private boolean hasRange;


	TelcoveToolClass(String input, int sectionSize, String between, boolean usesRange) {
		inputArray = stringToStringArray(sortNumbers(input));
		sectionCount = sectionSize;
		betweenInput = between;
		hasRange = usesRange;
	}

	String get() {
		return addBetween(inputArray, betweenInput, sectionCount, hasRange);
	}

}
