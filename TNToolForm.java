package TNTool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static TNTool.SharedFunctions.*;
import java.util.ArrayList;
import java.util.Date;

public class TNToolForm {

	private JPanel panel1;
	private JButton convertToSingleButton;
	private JTextPane out;
	private JTextPane in;
	private JScrollPane outScroll;
	private JLabel Count;
	private JButton convertToRangesButton;
	private JScrollPane inScroll;
	private JCheckBox portoutFormatCheckBox;
	private JTabbedPane tabbedPane1;
	private JTextPane input1;
	private JTextPane input2;
	private JTextPane output1;
	private JTextPane output2;
	private JTextPane output3;
	private JTextPane output4;
	private JButton compareButton;
	private JComboBox comboBox1;
	private JButton splitButton;
	private JComboBox splitSelection;
	private JTextPane splitInput;
	private JTextPane splitOutput;
	private JTextField inputAmountOfNumbersTextField;

	private ArrayList<HistoryClass> history = new ArrayList<HistoryClass>();
	private ArrayList<Split> splitArray = new ArrayList<Split>();
	private int splitCount = -1;
	private TNToolForm() {


		convertToSingleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = in.getText();
				if (!input.isEmpty()) {
					if (portoutFormatCheckBox.isSelected()) {
						try {
							input = portoutSingles(input);
						}
						catch (Exception except) {
							Count.setText(except.getMessage());
							System.out.println(except.getMessage());
							return;
						}
					}
					String outputText = RangeToSingleClass.toSingle(input);
					outputText = sortNumbers(outputText);
					out.setText(outputText);
					out.setSelectionStart(0);
					out.setSelectionEnd(0);
					String labelCount = String.valueOf(countLines(outputText));
					Count.setText("Count: " + labelCount);
					newSortHistory(in.getText(),out.getText(),Integer.parseInt(labelCount),0);

				}
			}
		});
		convertToRangesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = in.getText();
				in.setText(sortNumbers(input));
				if (!input.isEmpty()) {
					String outputText = SingleToRangeClass.toRange(input);

					if (portoutFormatCheckBox.isSelected()) {
						try {
							out.setText(portoutFormat(outputText));
							String labelCount = String.valueOf(countLines(input));
							Count.setText("Count: " + labelCount);
							newSortHistory(in.getText(),out.getText(),Integer.parseInt(labelCount),1);
						}
						catch (Exception except) {
							Count.setText(except.getMessage());
							System.out.println(except.getMessage());
						}
					} else {
						out.setText(outputText);
						String labelCount = String.valueOf(countLines(input));
						Count.setText("Count: " + labelCount);
						newSortHistory(in.getText(),out.getText(),Integer.parseInt(labelCount),1);

					}
					out.setSelectionStart(0);
					out.setSelectionEnd(0);


				}
			}
		});
		compareButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> compared = Compare.compareLists(input1.getText(),input2.getText());
				output1.setText(compared.get(0));
				output2.setText(compared.get(1));
				output3.setText(compared.get(2));
				output4.setText(compared.get(3));

			}
		});
		comboBox1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = comboBox1.getSelectedIndex();
				ArrayList<Object> hist = history.get(index).get();
				in.setText(hist.get(0).toString());
				out.setText(hist.get(1).toString());
				Count.setText("Count: " + hist.get(2));
			}
		});
		splitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				splitCount++;
				splitSelection.removeAllItems();
				splitOutput.setText("");
				try {
					splitArray.add(splitCount, new Split(splitInput.getText(), Integer.parseInt(inputAmountOfNumbersTextField.getText())));
					for (int i = 0; i < splitArray.get(splitCount).size(); i++) {
						splitSelection.addItem(String.valueOf(i + 1));
					}
					//splitSelection.setSelectedIndex(0);
					splitOutput.setText(splitArray.get(splitCount).get(0));
				}
				catch (Exception ex){
					System.out.println("Error creating splits: " + ex);
					splitOutput.setText("Please put only numbers in the amount to splits textbox.");
				}

			}
		});
		splitSelection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (splitSelection.getSelectedIndex() >= 0) {
					splitOutput.setText(splitArray.get(splitCount).get(splitSelection.getSelectedIndex()));

				}
			}
		});
	}

	public static void main(String[] args) {

		JFrame frame = new JFrame("Range To Single");
		frame.setContentPane(new TNToolForm().panel1);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		try {
			// Set System L&F
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
		}
		catch (UnsupportedLookAndFeelException e) {
			// handle exception
		}
		catch (ClassNotFoundException e) {
			// handle exception
		}
		catch (InstantiationException e) {
			// handle exception
		}
		catch (IllegalAccessException e) {
			// handle exception
		}
		frame.setVisible(true);
	}

	private void newSortHistory(String i, String o, int c, int type){ //type: 0 is toSingle, 1 is toRange
		String typeStr = "";
		if (type == 0) {
			typeStr = "Range to Single";
		} else if (type == 1) {
			typeStr = "Single to Range";
		}
		Date date = new Date();
		String historyString = date.toString() + " " + typeStr;

		boolean exists = false;
		for (int j = 0; j < comboBox1.getItemCount();j++) {
			if (comboBox1.getItemAt(j).equals(historyString)) {
				exists = true;
				break;
			}
		}
		if (!exists) {
			history.add(new HistoryClass(i,o,c));
			comboBox1.addItem(historyString);
			comboBox1.setSelectedItem(historyString);
		}
	}
}
