package one;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Regex
 * 1a. (\ban?\b)|(\bAn?\b)|(\bthe\b)|(\bThe\b)
 * 1b. \b(Mina Harker)\b|\b(Mrs\. Harker)\b
 * 1c. (\w+\sTransylvania\s\w+)|(\w+\sTransylvania\b)|(\bTransylvania\s\w+)|(\bTransylvania\b)
 * 1d. (\bto\s\w*)
 * 1e. \b(?:(?!Helsing\b|Godalming))(\w*ing\s)
 */
/**
 * Searches the text of a file and pulls out certain words decided by the entered regex
 * @author Justin Mattix, David Jones, Taden Duerod
 * @version 1.0
 * Compiler Project 3
 * CS322 - Compiler Construction
 * Fall 2021
 */
public class TextProcessor {

    public static void main(String[] args) {
        int counter = 0;
        String filename = args[0];
        String regex = args[1];
        
        try {
        	FileInputStream fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            StringBuilder bigString = new StringBuilder();
            //Turns Dracula.txt into a single massive line
            while((line = br.readLine()) != null) {
                bigString.append(" " + line);
            }
            	Matcher m = Pattern.compile(regex).matcher(bigString.toString());
                while(m.find()) {
                	//Ifs are used to prevent crashes from differing amount of groups in the regex
                	if(m.group(1) != null) {
                        System.out.println("(1)" + m.group(1));
                    }
                	if(m.groupCount() > 1) {
                		if(m.group(2) != null) {
                			System.out.println("(2)" + m.group(2));
                		}
                	}
                	if(m.groupCount() > 2) {
                    	if(m.group(3) != null) {
                    		System.out.println("(3)" + m.group(3));
                    	}
                	}
                    if(m.groupCount() > 3) {
                    	if(m.group(4) != null) {
                    		System.out.println("(4)" + m.group(4));
                    	}
                    }
                    //Adds 1 to the counter per condition met
                	counter++;
                }
                br.close();
            }
        catch (Exception e) {
            System.out.println("File not Found");
            e.printStackTrace();
        }
        System.out.println("Counted : " + counter);

    }
}