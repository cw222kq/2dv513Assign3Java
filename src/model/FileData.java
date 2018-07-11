/**
 * 
 */
package model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author cw222kq
 *
 */
@SuppressWarnings("serial")
public class FileData implements Serializable{

	public FileData() {
		
	}
	File file = new File("file/data.bin");
	public ArrayList<model.Data> listOfData = new ArrayList<model.Data>();
	
	// Save the input data to an array and saves the array to a file
	public void save(Object obj) throws IOException {
		
	}
	public Iterable<model.Data> getArray(){
		
		return listOfData;
	}
	

}
