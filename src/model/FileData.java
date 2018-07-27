/**
 * 
 */
package model;

import java.util.ArrayList;

/**
 * @author cw222kq
 *
 */
public class FileData {

	public FileData() {
		
	}
	private ArrayList<model.Data> listOfData = new ArrayList<model.Data>();
	
	public ArrayList<model.Data> getListOfData() {
		return listOfData;
	}
	public Iterable<model.Data> getArray(){
		
		return listOfData;
	}
	

}
