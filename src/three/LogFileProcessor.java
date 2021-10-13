package three;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses through a UNIX Log file and finds specific info based on the regex
 * @author Justin Mattix, David Jones, Taden Duerod
 * @version 1.0
 * Compiler Project 3
 * CS322 - Compiler Construction
 * Fall 2021
 */
public class LogFileProcessor {
	/**
	 * Main method. Used to call to runner method 
	 * @param args
	 */
    public static void main(String args[]){
    	String file = args[0];
        int flag = Integer.valueOf(args[1]);
        System.out.println("calling runner");
        runner(file, flag);
    }
    /**
     * Asks the user to enter the filepath and a print flag. Uses flags to determine what will be printed to the .txt file
     * @param filename
     * @param flag
     */
    public static void runner(String filename, int flag) {
        HashMap<String, Integer> ips = new HashMap<String, Integer>();
        HashMap<String, Integer> usernames = new HashMap<String, Integer>();
        System.out.println("Searching IPs");
        String regex = "(([0-9]{1,3}\\.){3}[0-9]{1,3})";
        String regex2 = "user (.*) from";
        try {
            FileInputStream fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            FileOutputStream fos1 = new FileOutputStream("Part3A.txt", true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos1));
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            int linecounter = 0;
            //Goes through the file and finds the item needed, adds it to the hashmap, and then adds 1 to the counter to the correct counter
            while((line = br.readLine()) != null) {
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(line);
                Pattern p2 = Pattern.compile(regex2);
                Matcher m2 = p2.matcher(line);
                while(m.find()) {
                    if(m.group(1) != null) {
                        if(ips.containsKey(m.group(1))) {
                            int count = ips.get(m.group(1));
                            ips.put(m.group(1), count+1);
                        }
                        else {
                            ips.put(m.group(1), 1);
                        }
                    }
                }
                while(m2.find()) {
                    if(m2.group(1) != null) {
                        if(usernames.containsKey(m2.group(1))) {
                            int count = usernames.get(m2.group(1));
                            usernames.put(m2.group(1), count+1);
                        }
                        else {
                            usernames.put(m2.group(1), 1);
                        }
                    }
                }
                linecounter++;
            }
            //Flag 0
            if(flag == 0) {
                bw.write(linecounter + " many lines in the log file were parsed.");
                bw.newLine();
                bw.write("There are " + ips.size() + " unique IP addresses in the log,");
                bw.newLine();
                bw.write("There are " + usernames.size() + " unique users in the log.");
                bw.newLine();
            }
            //Flag 1
            else if(flag == 1) {
                bw.write("IPs");
                bw.newLine();
                for(String str : ips.keySet()) {
                    bw.write(str + ": " + String.valueOf(ips.get(str)));
                    bw.newLine();
                }
            }
            //Flag 2
            else if(flag == 2) {
                bw.write("Usernames");
                bw.newLine();
                for(String str : usernames.keySet()) {
                    bw.write(str + ": " + String.valueOf(usernames.get(str)));
                    bw.newLine();
                }
            }
            bw.write("All done!");
            bw.close();
            br.close();
            
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}