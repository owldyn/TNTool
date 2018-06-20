package TNTool;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import org.w3c.dom.ranges.Range;

import java.util.ArrayList;
import java.util.Collections;

class SharedFunctions {
	static String addBetween(ArrayList<String> input, String between, int sections, boolean hasRange) {
			StringBuilder outputBuilder = new StringBuilder();
			int arrayIndex = 0;

		boolean atEnd = false;
		if (!hasRange) {
			int length = input.size();
			if (sections <= 0) {
				for (int i = 0; i < input.size(); i++) {
					outputBuilder.append(input.get(i));
					if (input.size() > i + 1) {
						outputBuilder.append(between);
					}
				}
			} else {

				while (!atEnd) {
					int remainingLines = length - arrayIndex;
					if (remainingLines < sections) {
						sections = remainingLines;
						atEnd = true;
					}

					for (int i = 0; i < sections; i++) {
						outputBuilder.append(input.get(arrayIndex));
						if (sections > i + 1) {
							outputBuilder.append(between);
						}
						arrayIndex++;
					}
					if (!atEnd) {
						outputBuilder.append("\n");
					}
				}
			}
		} else {
			ArrayList<String> inputRange = stringToStringArray(SingleToRangeClass.toRange(arrayToString(input)));
			ArrayList<String> separatedRanges = new ArrayList<>();
			ArrayList<String> separatedSingles = new ArrayList<>();

			for (int i = 0; i < inputRange.size(); i++) {
				String line = inputRange.get(i);
				if (line.contains("-")) {
					String range = convertIfLessThanSeven(line);
					if (range.contains(":")) {
						separatedRanges.add(range);
					} else {
						ArrayList<String> tmpArray = stringToStringArray(range);
						separatedSingles.addAll(tmpArray);
					}
				} else {
					separatedSingles.add(line);
				}
			}
			int length = separatedSingles.size();
			if (length > 0) {
				while (!atEnd) {
					int remainingLines = length - arrayIndex;
					if (remainingLines < sections) {
						sections = remainingLines;
						atEnd = true;
					}

					for (int i = 0; i < sections; i++) {
						outputBuilder.append(separatedSingles.get(arrayIndex));
						if (sections > i + 1) {
							outputBuilder.append(between);
						}
						arrayIndex++;
					}
					if (!atEnd) {
						outputBuilder.append("\n");
					}
				}
			}
			if (separatedRanges.size() > 0) {
				if (separatedSingles.size() > 0) {
					outputBuilder.append("\n");
				}
				for (String anSeparatedRanges : separatedRanges) {
					outputBuilder.append(anSeparatedRanges);
				}
			}
		}
		return outputBuilder.toString();

	}
	private static String convertIfLessThanSeven(String input) {
		String inputSingles = RangeToSingleClass.toSingle(input);
		if (countLines(inputSingles) < 7) {
			return inputSingles;
		} else {
			String tmpString;
			ArrayList<String> tmpArray = stringToStringArray(inputSingles);
			tmpString = tmpArray.get(0) + ":" + tmpArray.get(tmpArray.size() - 1);
			return tmpString;
		}
	}



	static int countLines(String str) {
		String[] lines = str.split("\r\n|\r|\n");
		return lines.length;
	}


	static String removeSpaces(String spaceless) {
		boolean hasSpace = true;
		while (hasSpace) {
			int spaceIndex = spaceless.indexOf(' ');
			if (spaceIndex == -1) {
				hasSpace = false;
			} else {
				spaceless = spaceless.replace(" ","");
			}
		}
		return spaceless;
	}



	static String sortNumbers(String input) {
		StringBuilder output = new StringBuilder();
		ArrayList<Long> inputArray = stringToArray(input);
		ArrayList<String> inputStringArray = stringToStringArray(input);
		ArrayList<Integer> leadingZerosArray = new ArrayList<>();
		int lines = inputArray.size();
		for (int i = 0; i < inputStringArray.size(); i++) {
			String number = inputStringArray.get(i);
			boolean isZero = true;
			int leadingZeros = 0;
			while (isZero) {
				if (number.charAt(leadingZeros) == '0') {
					leadingZeros++;
				} else {
					isZero = false;
				}
			}
			leadingZerosArray.add(i,leadingZeros);
		}
		Collections.sort(inputArray);

		for (int i = 0;i < lines;i++) {
			if (leadingZerosArray.get(i) > 0) {
				for (int j = 0; j < leadingZerosArray.get(i);j++) {
					output.append("0");
				}
			}
			output.append(inputArray.get(i));
			output.append("\n");
		}
		return output.toString();
	}



	static ArrayList<Long> stringToArray(String input) {
		String[] stringArray = input.split("\r\n|\r|\n");
		int lines = countLines(input);
		ArrayList<Long> unsortedArray = new ArrayList<Long>();

		for (int i = 0;i < lines;i++) {
			if (!stringArray[i].equals("")) {
				unsortedArray.add(Long.parseLong(stringArray[i]));
			}
		}
		return unsortedArray;
	}



	static ArrayList<String> stringToStringArray(String input) {
		String[] stringArray = input.split("\r\n|\r|\n");
		int lines = countLines(input);
		ArrayList<String> unsortedArray = new ArrayList<String>();

		for (int i = 0;i < lines;i++) {
			if (!stringArray[i].equals("")) {
				unsortedArray.add(stringArray[i]);
			}
		}

		return unsortedArray;
	}

	static String arrayToString(ArrayList<?> inputArray) {
		StringBuilder tmpBuilder = new StringBuilder();
		for (Object line : inputArray) {
			tmpBuilder.append(line.toString());
			tmpBuilder.append("\n");
		}
		return tmpBuilder.toString();
	}




	static String portoutFormat(String input) throws Exception{
		ArrayList<String> unformattedArray = stringToStringArray(input);
		StringBuilder formattedOutput = new StringBuilder();

		for (int i = 0;i < unformattedArray.size();i++) {
			if ((unformattedArray.get(i).length() == 23 && unformattedArray.get(i).contains(" - ")) ||		//If it's a 10 digit range (9185550123 - 9185550130)
					(unformattedArray.get(i).length() == 10 && !unformattedArray.get(i).contains(" - "))) { //Or if it's a single TN (9185550123)
				//I know this shouldn't be an empty if body but it works									//You're good.
			} else {																						//Otherwise,
				throw new Exception("All numbers should be 10 digits.");									//Throw an exception
			}
			StringBuilder tmp = new StringBuilder();
			tmp.append("(");
			tmp.append(unformattedArray.get(i).substring(0,3));
			tmp.append(")");
			tmp.append(unformattedArray.get(i).substring(3,6));
			tmp.append("-");
			tmp.append(unformattedArray.get(i).substring(6,10));
			if (unformattedArray.get(i).contains("-")) {
				tmp.append(" - ");
				tmp.append(unformattedArray.get(i).substring(19,23));
				tmp.append(":");
			} else {
				tmp.append("\t\t:");
			}
			formattedOutput.append(tmp.toString());
			formattedOutput.append("\n");
		}
		return formattedOutput.toString();
	}
	static String portoutSingles(String input) throws Exception{
		ArrayList<String> inputArray = stringToStringArray(input);				//Pull the string into an array to make it easier to manipulate each individual number
		StringBuilder output = new StringBuilder();


		for (int i = 0;i < inputArray.size();i++) {
			if (!inputArray.get(i).contains("(") || !inputArray.get(i).contains(")") || !inputArray.get(i).contains("-")) {
				throw new Exception("Portout Format not detected.");
			}
			Boolean single = true;
			String singleTest =  inputArray.get(i);
			if (singleTest.replaceAll("-","").length() < singleTest.length() - 1) {		// If it has 2 consecutive tabs in it, it should be a single. ( "(202)879-6757		:" )
				single = false;										//Example on how this next section works:
			}														//inputArray.get(i) is "	(202)879-6760 - 6761:" 	or "	(202)879-6757		:"
			String tmp = inputArray.get(i).replace("(","");	//Now it's "	202)879-6760 - 6761:"	or "	202)879-6757		:"
			tmp = tmp.replace(")","");						//Now it's "	202879-6760 - 6761:"	or "	202879-6757		:"
			tmp = tmp.replaceFirst("-","");					//Now it's "	2028796760 - 6761:'		or "	2028796757		:"
			tmp = tmp.replace(":","");						//Now it's "	2028796760 - 6761'		or "	2028796757		"
			tmp = tmp.replaceAll(" ","");						//Now it's "	2028796760-6761'		or "	2028796757		"
			tmp = tmp.replaceAll("\t","");						//Now it's "2028796760-6761"			or "2028796757"
			if (single) {
				output.append(tmp);												//If it's a single, just add the number "2028796757"
				output.append("\n");											//Append a newline for the next number
			} else {
				int startOfEnd = tmp.indexOf("-") + 1;							//Get the index of the end of the range, (6761) using the - as the separator

				output.append(tmp.substring(0,startOfEnd));						//Append the start (2028796760)
				output.append(tmp.substring(0,6));								//Get the first 6 numbers, (202879) so the end is a whole number
				output.append(tmp.substring(startOfEnd,tmp.length()));			//Get the last 4 numbers, (6761) to finish the end of the range
				output.append("\n");											//Append a newline for the next number
			}

		}
		return output.toString();

	}
}
