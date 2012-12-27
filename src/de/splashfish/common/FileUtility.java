package de.splashfish.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * 
 * @author lukas
 *
 */
public class FileUtility {
	
	/**
	 * readFully uses a {@link Scanner} and {@link StringBuilder}
	 * to guarantee an as fast as possible answer to that
	 * request. 
	 * 
	 * @return a string containing the whole file's contents
	 * @throws IOException
	 */
	public static String readFully(File file) throws IOException {
		StringBuilder buffer = new StringBuilder();
		
		if(!file.exists())
			throw new FileNotFoundException();
		
		Scanner reader = new Scanner(file);
		while(reader.hasNextLine()) {
			buffer.append(reader.nextLine());
			if(reader.hasNextLine())
				buffer.append("\n");
		}
		reader.close();
		
		return buffer.toString();
	}
	
	/**
	 * writeFully uses a {@link BufferedWriter} to guarantee
	 * a fast and heap-friendly writing process of the given file
	 * 
	 * @param lines all the lines that are wanted to be written to the file
	 * @throws IOException
	 */
	public static void writeFully(File file, String... lines) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
		
		for(int i = 0; i < lines.length; i++) {
			writer.append(lines[i]);
			if(i < lines.length - 1)
				writer.newLine();
		}
		
		writer.close();
	}
	
	/**
	 * createFile creates a new {@link File} and catches the occurring {@link IOException}.
	 * 
	 * @param file
	 * @return if the file was able to be created
	 */
	public static boolean createFile(File file) {
		try {
			file.createNewFile();
			Logger.getLogger().log("ERROR " +" ["+ file.getAbsolutePath() +"]");
			return true;
		} catch (IOException e) {
			Logger.getLogger().err(e);
		}
		
		return false;
	}
	
	/**
	 * returns {@link File}.lastModified or the newest modification of a whole directory
	 * 
	 * @param file
	 * @return timestamp of the last modification
	 */
	public static long getLastModified(File file) {
		if(file.isFile())
			return file.lastModified();
		else {
			long last = -1;
			
			for(File sub : getAllFiles(file)) {
				if(sub.lastModified() > last)
					last = sub.lastModified();
			}
			
			return last;
		}
	}
	
	/**
	 * getAllFiles lists all the {@link File}s within a given directory recursively.
	 * That means, that all {@link File}s in subdirectories are included, too.
	 * 
	 * @param file
	 * @return a list of all {@link File}s
	 */
	public static File[] getAllFiles(File file) {
		if(file.isDirectory()) {
			LinkedList<File> children = new LinkedList<File>();
			readAllFiles(file, children);
			
			File[] value = new File[children.size()];
			value = children.toArray(value);
			
			return value;
		}
		return new File[0];
	}
	
	private static void readAllFiles(File file, LinkedList<File> list) {
		if(file.isFile())
			list.add(file);
		else
			for(File sub : file.listFiles())
				readAllFiles(sub, list);
	}
}
