package assignment02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class TestWords {
	public static void main(String[] args) throws IOException {
		String test = Files.readString(Paths.get("assignment02/smallTest.txt"));
		test = test.toLowerCase();
		String[] split = test.split("[,.!?\\s]+");
		//check:
		System.out.println(Arrays.toString(split));
		Component comp = new FinalPeriod();
		//create linked list of decorator words
		for(String str : split)
			comp = new Word(comp, str);
		//check:
		comp.print();
		System.out.println();
		System.out.println(comp.getConsonants());
		System.out.println(comp.getVowels());
	}
}
