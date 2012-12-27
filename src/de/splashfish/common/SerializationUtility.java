package de.splashfish.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 
 * @author lukas
 *
 */
public class SerializationUtility {

	/**
	 * stores a {@link Serializable} <i>object</i> in the fiven {@link File} <i>output</i>
	 * 
	 * @param output
	 * @param object
	 */
	public static synchronized void store(File output, Serializable object) {
		FileUtility.createFile(output);
		
		if(object instanceof Serializable) {
		
			try {
				FileOutputStream out = new FileOutputStream(output);
					ObjectOutputStream objout = new ObjectOutputStream(out);
						objout.writeObject(object);
					objout.close();
				out.close();
				Logger.getLogger().log("stored "+ object.getClass().getSimpleName() +"@"+ object.hashCode() +" in ["+ output +"]");
			} catch(IOException e1) {
				Logger.getLogger().err(SerializationUtility.class, e1);
			}
			
			return;
		}
		
		Logger.getLogger().err(SerializationUtility.class, "cannot serialize null");
	}
	
	/**
	 * tries to load a {@link Serializable} object from the given {@link File} input.
	 * It may return <code>null</code>
	 * 
	 * @param input
	 * @return
	 */
	public static synchronized Object load(File input) {
		Object obj = null;
		
		try {
			FileInputStream in = new FileInputStream(input);
				ObjectInputStream objin = new ObjectInputStream(in);
					obj = objin.readObject();
				objin.close();
			in.close();
		} catch (Exception e) {
			Logger.getLogger().err(SerializationUtility.class, "was not able to load ["+ input +"], returning null");
		}

		if(obj == null)
			Logger.getLogger().log("loaded Object is null");
		
		return obj;
	}
	
}
