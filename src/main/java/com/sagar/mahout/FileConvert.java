/**
 * 
 */
package com.sagar.mahout;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author A039883
 *
 */
public class FileConvert {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("data/u.data"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("data/movies.csv"));
		
		String line = null;
		while((line = br.readLine()) != null) {
			String[] spline = line.split("\t");
			bw.write(spline[0]+","+spline[1]+","+spline[2]+"\n");
		}
		br.close();
		bw.close();
	}

}
