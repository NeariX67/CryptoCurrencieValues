package com.nearix.cryptovalues;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

public class filemanager {
	public static void writeFile(String text) {

	    BufferedWriter f;
	    try {
	      f = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "\\config.txt"));
	      f.write(text);
	      f.close( );
	    }
	    catch (IOException e) {
	      System.err.println(e.toString());
	    }		
	}
	public static String readFile() {
		LineNumberReader f;
		String line = "";
	    try {
	    	f = new LineNumberReader(new FileReader(System.getProperty("user.dir") + "\\config.txt"));
	    	line = f.readLine();
	    	f.close( );
	    }
	    catch (IOException e) {
	    	System.err.println(e.toString());
	    }
    	return line;
	}

}
