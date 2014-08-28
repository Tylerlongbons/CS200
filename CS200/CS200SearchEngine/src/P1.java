import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class P1 {
	public ArrayList<String> words = new ArrayList<String>();

	public P1(String in) {
		readFile(in);
		writeWords();
	}

	public void readFile(String filename) {
		toList(filename);
		noChars();
		noPunct();
		alphaSort();
		noDuplicates();
	}

	private void toList(String filename) {
		try {
			Scanner scanner = new Scanner(new File(filename));
			while (scanner.hasNext()) {
				String word = scanner.next();
				int search = Collections
						.binarySearch(words, word.toLowerCase());
				if (search < 0)
					words.add(word.toLowerCase());
			}
		} catch (FileNotFoundException e) {
			// TODO How do they want errors
			e.printStackTrace();
		}
	}

	private void noChars() {
		boolean removed =false;
		for (int i = 0; i < words.size(); i++) {
			if (words.get(i).contains("<")) {
				String[] split = words.get(i).split("<");
				if (!split[0].equals(""))
					words.set(i, split[0]);
				else{
					words.remove(i);
					removed=true;
				}
				while (!words.get(i).contains(">")) {
					words.remove(i);
				}
				if(removed)
					i--;
			}
			else if (words.get(i).contains(">")) {
				String[] split = words.get(i).split(">");
				if (split.length > 1)
					words.set(i + 1, split[1]);
				else{
					words.remove(i);
					i--;
				}
			}
		}

	}

	private void noPunct() {
		boolean allpunct = false;
		boolean remove = false;
		boolean firstpunct = false;
		boolean endpunct = false;
		String word = "";
		char[] chars = null;
		for (int i = 0; i < words.size(); i++) {
			word = words.get(i);
			chars = word.toCharArray();
			if (!Character.isAlphabetic(chars[0])
					&& !Character.isDigit(chars[0]))
				firstpunct = true;
			if (!Character.isAlphabetic(chars[chars.length - 1])
					&& !Character.isDigit(chars[chars.length - 1]))
				endpunct = true;
			if (firstpunct && endpunct) {
				allpunct = true;
				for (int j = 1; j < chars.length - 1 && !allpunct; j++) {
					if (Character.isAlphabetic(chars[j])
							|| Character.isDigit(chars[j]))
						allpunct = false;
				}
				if (allpunct)
					remove = true;
			}

			if (remove) {
				words.remove(i);
				i--;
			} else if (firstpunct || endpunct) {
				if (firstpunct)
					word = word.substring(1);
				if (endpunct)
					word = word.substring(0, word.length() - 1);
				words.set(i, word);
			}
			allpunct = false;
			remove = false;
			firstpunct = false;
			endpunct = false;
		}

	}

	private void alphaSort() {
		Collections.sort(words);
	}

	private void noDuplicates() {
		for (int i = 0; i < words.size() - 1; i++) {
			if (words.get(i).equals(words.get(i + 1))) {
				words.remove(i + 1);
				i--;
			}
		}
	}

	public void writeWords() {
		System.out.println("WORDS");
		for (String s : words) {
			System.out.println(s);
		}
		System.out.println(words.size());

	}

	public static void main(String[] args) {
		P1 p = new P1(args[0]);
	}
}