package TNTool;
import java.util.Scanner;

import static TNTool.SharedFunctions.*;

public class RangeToSingleClass  {


	public static String toSingle(String input){
		Scanner userInput = new Scanner(input);			 //Scanner to pull each line
		StringBuilder output = new StringBuilder();		 //StringBuilder to make string through loop

		int lines = countLines(input);					  //Number of lines to determine how many loops to run
		int rangeInd;									   //Will be assigned to where the colon or dash separating the range
		int length;										 //Will be assigned to the length of the range string to determine the end index of the end number in the string
		long start;										 //Will be the first number in the range
		long end;										   //Will be the last number in the range
		long print;										 //Will be the temporary variable that is the current number in the loop, to be added to StringBuilder output

		String tmp;										 //Creating temp string that will change each loop
		for (int i = 0; i < lines; i++) {				   //Run for each line
			tmp = userInput.nextLine();					 //Pull next line of input for use
			if (tmp.contains("(") || tmp.contains(")")) {
				return "Error 1: Please remove any parenthesis. Input should be just TNs separated by : or -. Example: 8005551234:8005552345";
			} else if (!tmp.contains(":") && !tmp.contains("-")) {
				output.append(tmp);						 //If it doesn't have a range indicator, assume input is just one number, so output that into the string without changing anything
				output.append("\n");
			} else if (hasMultipleDashes(tmp)) {
				return "Error 2: Too many dashes. Input should be just TNs separated by : or -. Example: 8005551234:8005552345";
			} else {
				if (tmp.contains(" ")) {					//Need to remove spaces to easily parse string
					tmp = removeSpaces(tmp);
				}
				if (tmp.contains(":")) {					//Determine if it uses a colon or dash as a range indicator
					rangeInd = tmp.indexOf(":");
				} else {
					rangeInd = tmp.indexOf("-");
				}
				length = tmp.length();
				start = Long.parseLong(tmp.substring(0, rangeInd));
				end = Long.parseLong(tmp.substring(rangeInd + 1, length));
				print = start;
				if (start > end) {
					return "Error 3: Start of range cannot be lower than end of range.";
				} else {
					output.append(String.valueOf(print));
					output.append("\n");
					while (print != end) {
						print++;
						output.append(String.valueOf(print));
						output.append("\n");
					}
				}


			}
		}
		return output.toString();
	}


	private static boolean hasMultipleDashes(String input) {
		if (input.contains("-")) {
			int startLen = input.length();
			input = input.replaceAll("-", "");
			int endLen = input.length();

			return (endLen < startLen - 1);
		} else {
			return false;
		}
	}
}


