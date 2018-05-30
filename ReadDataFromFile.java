import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadDataFromFile {
	String fileName = "C:\\Users\\gloriya.konova\\Desktop\\phonebook.txt";
	Map<String, String> phoneBook = new HashMap<String, String>();
	Scanner input = new Scanner(System.in);

	public void menue() {
		while (true) {
			System.out.println("-----------Welcome to PhoneBook.-------------");
			System.out.println("Chouse your option .");
			System.out.println("1. Add New Contact " + "\n" + "2. Search Number " + "\n" + "3. Delete Number" + "\n"
					+ "4. Show All contact" + "\n" + "5. Sort" + "\n" + "6. Call a number " + "\n"
					+ "7. View most wanted numbers " + "\n" + "8. Exit");
			int option = input.nextInt();

			switch (option) {
			case 1:
				addContact();
				break;
			case 2:
				searchNumber();
				break;
			case 3:
				try {
					deleteByName();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 4:
				showAll();
				break;
			case 5:
				Map<String, String> unsorted = showAll();
				sortPhoneBook(unsorted);
				break;
			case 7:
				mostWanted();
				break;
			case 8:
				System.out.println("Thank you for using PhoneBook ....");
				System.exit(0);
			}
		}
	}

	private Map<String, String> showAll() {
		try {
			BufferedReader lineReader = new BufferedReader(new FileReader(fileName));
			String lineText = null;

			while ((lineText = lineReader.readLine()) != null) {
				String[] personInformation = lineText.split(",");
				String name = personInformation[0];
				String num = personInformation[1];

				if (validateNumber(num) == true) {
					phoneBook.put(name.toLowerCase(), num);
				}
			}
			lineReader.close();

		} catch (IOException ex) {
			System.err.println(ex);
		}
		System.out.println("Phone book valid members " + phoneBook);
		return phoneBook;
	}

	private boolean validateNumber(String number) {
		Pattern p = Pattern.compile("(?:003598|08|\\+3598)([7-9]{1})([0-9]{7})");
		Matcher m = p.matcher(number);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

	private void deleteByName() throws FileNotFoundException, IOException {
		File inputFile = new File("C:\\Users\\gloriya.konova\\Desktop\\phonebook.txt");
		File tempFile = new File("C:\\\\Users\\\\gloriya.konova\\\\Desktop\\\\myTempFile.txt");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		System.out.println("Delete The Name : ");
		String name = input.next();
		String lineToRemove = name;
		String currentLine;

		while ((currentLine = reader.readLine()) != null) {
			// trim newline when comparing with lineToRemove
			String trimmedLine = currentLine.trim();
			System.out.println("Current line" + trimmedLine.startsWith(lineToRemove));
			if (trimmedLine.startsWith(lineToRemove))
				continue;
			writer.write(currentLine + System.getProperty("line.separator"));
		}

		writer.close();
		reader.close();

		tempFile.renameTo(inputFile);
	}

	private void searchNumber() {
		System.out.println("Enter The Name : ");
		String name = input.next();
		System.out.println(phoneBook.containsKey(name) ? "The Number of " + name + " is : " + phoneBook.get(name)
				: "The Number Not Present ");
	}

	private void addContact() {
		System.out.print("Enter the Name : ");
		String name = input.next();
		System.out.print("Enter the number : ");
		String number = input.next();
		if (validateNumber(number) == true) {

			try {
				Writer output = new BufferedWriter(new FileWriter(fileName, true));
				output.append('\n' + name + " , " + number);
				// phoneBook.put(name, number);
				output.close();
				System.out.println("Number Added Successfully ..");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Number is not valid! ");
		}
	}

	private void sortPhoneBook(Map<String, String> unsorted) {

		TreeMap<String, String> sorted = new TreeMap<>();

		// Copy all data from hashMap into TreeMap
		sorted.putAll(unsorted);
		System.out.println("Sorted PhoneBook" + sorted.entrySet());
	}

	private void mostWanted() {
		List<String> list = callSomeone();
		Map<String, Integer> duplicates = new HashMap<String, Integer>();
		for (String str : list) {
			if (duplicates.containsKey(str)) {
				duplicates.put(str, duplicates.get(str) + 1);
			} else {
				duplicates.put(str, 1);
			}
		}
		for (Map.Entry<String, Integer> entry : duplicates.entrySet()) {
			System.out.println(entry.getKey() + " = " + entry.getValue());
		}
	}

	private List<String> callSomeone() {
		List<String> list = new ArrayList<String>();
		String choice;

		do {
			System.out.println("Enter The Name : ");
			String name = input.next();
			list.add(name);
			System.out.println("Do you want to continue calling? y or n");
			choice = input.next();
		} while (choice.equals("Y"));

		if (!choice.equals("Y")) {
			System.out.println("Ok let's see most called person");
		}
		return list;
	}
}
