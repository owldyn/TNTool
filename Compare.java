package TNTool;

import static TNTool.SharedFunctions.*;
import java.util.ArrayList;

public class Compare {
	static ArrayList<String> compareLists(String input1, String input2) {
		input1 = sortNumbers(input1);
		input2 = sortNumbers(input2);
		ArrayList<Long> inputArray1 = stringToArray(input1);
		ArrayList<Long> inputArray2 = stringToArray(input2);
		ArrayList<String> output = new ArrayList<String>();		//First string in array (0) will be what isn't in input1, second (1) will be what isn't in input2, third (2) will be all numbers that match, fourth (3) will be what doesn't a match.
		ArrayList<StringBuilder> builder = new ArrayList<StringBuilder>();	//Corresponds to the output string
		for (int i = 0; i < 4;i++){
			builder.add(new StringBuilder());
		}

		for (int i = 0; i < inputArray1.size(); i++) {
			if (inputArray2.contains(inputArray1.get(i))) {
				builder.get(2).append(inputArray1.get(i));
				builder.get(2).append("\n");
			} else {
				builder.get(1).append(inputArray1.get(i));
				builder.get(1).append("\n");

				builder.get(3).append(inputArray1.get(i));
				builder.get(3).append("\n");
			}
		}
		for (int i = 0; i < inputArray2.size(); i++) {
			if (!inputArray1.contains(inputArray2.get(i))) {
				builder.get(0).append(inputArray2.get(i));
				builder.get(0).append("\n");

				builder.get(3).append(inputArray2.get(i));
				builder.get(3).append("\n");
			}
		}
		for (int i = 0; i < builder.size(); i++) {
			output.add(builder.get(i).toString());
		}
		return output;
	}
}
