import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
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
					+ "4. Show All contact" + "\n" + "5. Sort" + "\n" + "6. Exit");
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
					deleteNumber();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
			case 6:
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

	private void deleteNumber() throws FileNotFoundException, IOException {

		File inputFile = new File(fileName);
		File tempFile = new File("myTempFile.txt");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		System.out.println("Enter The name to Delete : ");
		String name = input.next();
		String lineToRemove = name;
		String currentLine;

		try {
			while ((currentLine = reader.readLine()) != null) {
				// trim newline when comparing with lineToRemove
				String trimmedLine = currentLine.trim();
				if (trimmedLine.equals(lineToRemove)) {
					continue;
				}
				phoneBook.remove(name);
				writer.write(currentLine + System.getProperty("line.separator"));
				System.out.println("Remove it from PhoneBook ");

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Can not find person with this name ");
			e.printStackTrace();
		}
		writer.close();
		reader.close();
		boolean successful = tempFile.renameTo(inputFile);
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
			System.out.println("Number is not valid !!! ");
		}
	}

	private void sortPhoneBook(Map<String, String> unsorted) {

		TreeMap<String, String> sorted = new TreeMap<>();

		// Copy all data from hashMap into TreeMap
		sorted.putAll(unsorted);

		// Display the TreeMap which is naturally sorted
		System.out.println("sorted " + sorted.entrySet());
		for (Map.Entry<String, String> entry : sorted.entrySet())
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

	}

	// private String replaceNumbers(String number) {
	//
	// Pattern p = Pattern.compile("^00");
	// Matcher m = p.matcher(number);
	//
	// Pattern p2 = Pattern.compile("^0");
	// Matcher m2 = p2.matcher(number);
	//
	// StringBuilder sb = new StringBuilder(number);
	// if (m.find()) {
	// sb.replace(0, 0, "+359");
	// return sb.toString();
	// }
	//
	// if (m2.find()) {
	// sb.replace(0, 1, "+");
	// return sb.toString();
	// }
	// return "";
	// }
}
