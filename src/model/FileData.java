/**
 * 
 */
package model;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	private ArrayList<model.Data> listOfData = new ArrayList<model.Data>();
	
	// Save the input data to an array and saves the array to a file
	/*public void save() throws IOException {
		 try {
		      FileOutputStream fileOutputStream = new FileOutputStream(file);
		      ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		      objectOutputStream.writeObject(listOfData);
		      fileOutputStream.close(); 
		      objectOutputStream.close();
		      System.out.println("The data was saved successfully");
		    
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		      System.err.println(e.getMessage());
		      System.err.println("the file was not found");
		    }
		
	}
	@SuppressWarnings("unchecked")
	public void load() throws IOException, ClassNotFoundException, EOFException {
		ObjectInputStream objectInputStream = null; 
	    FileInputStream fileInputStream = null;
	    try {
	      fileInputStream = new FileInputStream(file);
	      objectInputStream = new ObjectInputStream(fileInputStream);
	      listOfData = (ArrayList<model.Data>) objectInputStream.readObject();
	      for(model.Data d: listOfData){
	        System.out.println("from the file: " + u1.getUserName()+ " " + u1.getPoints());   
	      }
	      System.out.println("size of the list: " + highscoreList.size());
	      //this.getArray();
	      
	      
	                  
	    }catch(EOFException ex){objectInputStream.close(); fileInputStream.close();} catch (FileNotFoundException e) {
	      System.err.println("Could not find the file");
	      e.printStackTrace();
	      
	    }
	    objectInputStream.close();
	    fileInputStream.close();
	}*/
	public ArrayList<model.Data> getListOfData() {
		return listOfData;
	}
	public Iterable<model.Data> getArray(){
		
		return listOfData;
	}
	

}
