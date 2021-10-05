package part2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Uses regex to parse the extracted pdf from ExtractText to find specific info from the text file
 * @author Justin Mattix, David Jones, Taden Duerod
 * @version 1.0
 * Compiler Project 3
 * CS322 - Compiler Construction
 * Fall 2021
 */
public class ProcessSchedule {
	
	public static ArrayList<String> textToOutput = new ArrayList<String>();
	
	public static void main(String args[]) {
		Scanner s = new Scanner(System.in);
		System.out.print("Enter a file to parse: ");
		String file = s.nextLine();
		ExtractText.main(file);
		System.out.print("Enter where the new text file is: ");
		String file2 = s.nextLine();
		s.close();
		extractCourceInfo(file2);
		extractStatus(file2);
		printNumberOfCourses(file2);
		
	}
	/**
	 * Extracts the course name and information
	 * @param filename
	 */
	private static void extractCourceInfo(String filename) {
		String regex = "([A-Z]{4}-[0-9]{3}H?L?-[0-9]{2}.*\\b)";
		try {
        	FileInputStream fstream = new FileInputStream(filename);
        	DataInputStream in = new DataInputStream(fstream);
        	FileOutputStream fos1 = new FileOutputStream("Part2A.txt", true);
        	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos1));
        	BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			//Interates through the file and searches for the specific information that the regex specifies
			while((line = br.readLine()) != null) {
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(line);
				while(m.find()) {
					String a = m.group(1);	
					bw.write(a);
					bw.newLine();
					
				}
			}
			bw.close();
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Got course info");
		
				
	}
	/**
	 * Extracts the status of the course OPEN/ClOSED and the number of seats
	 * @param filename
	 */
	private static void extractStatus(String filename) {
		String regex1 = "([A-Z]{2,4}-([0-9]{3}|ABROAD)H?L?R?-L?C?[0-9]{0,2}[A-Z]{0,2})"; //gets course name/number/section
		String regex2 = "(CLOSED|Open \\d* \\d*)";
		try {
        	FileInputStream fstream = new FileInputStream(filename);
        	DataInputStream in = new DataInputStream(fstream);
        	FileOutputStream fos2 = new FileOutputStream("Part2B.txt", true);
        	BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(fos2));
        	BufferedReader br2 = new BufferedReader(new InputStreamReader(in));
			String line;
			//Interates through the file and searches for the specific information that the regex specifies
			while((line = br2.readLine()) != null) {
				Pattern p = Pattern.compile(regex1);
				Pattern p2 = Pattern.compile(regex2);
				Matcher m = p.matcher(line);
				Matcher m2 = p2.matcher(line);
				while(m.find() | m2.find()) {
					if(m.group(1) != null) {
						bw2.write(m.group(1));
						bw2.newLine();
					}
					if(m2.group(1) != null) {
						bw2.write(m2.group(1));
						bw2.newLine();
						bw2.flush();
					}
					
				}
			}
			bw2.close();
			br2.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Got status of Courses");
		
	}
	/**
	 * Prints the number of individual courses
	 * @param filename
	 */
	private static void printNumberOfCourses(String filename) {
		HashMap<String, Integer> courses = new HashMap<String, Integer>();
		System.out.println("Number of Courses");
		String regex = "([A-Z]{2,4})-\\d{3}";
		try {
        	FileInputStream fstream = new FileInputStream(filename);
        	DataInputStream in = new DataInputStream(fstream);
        	FileOutputStream fos3 = new FileOutputStream("Part2C.txt", true);
        	BufferedWriter bw3 = new BufferedWriter(new OutputStreamWriter(fos3));
        	BufferedReader br3 = new BufferedReader(new InputStreamReader(in));
			String line;
			//Interates through the file and searches for the specific information that the regex specifies
			while((line = br3.readLine()) != null) {
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(line);
				while(m.find()) {
					if(m.group(1) != null) {
						if(courses.containsKey(m.group(1))) {
							int count = courses.get(m.group(1));
							courses.put(m.group(1), count+1);
						}
						else {
							courses.put(m.group(1), 1);
						}
					}
				}
			}
			
			for(String str : courses.keySet()) {
				bw3.write(str);
				bw3.newLine();
				bw3.write(String.valueOf(courses.get(str)));
				bw3.newLine();
				bw3.flush();
			}
			System.out.println("End of Counting Courses");
			br3.close();
			bw3.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}