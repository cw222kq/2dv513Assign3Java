/**
 * 
 */
package model;

/**
 * @author cw222kq
 *
 */
public class StudentClass {

	// initiates the values
	private String name;
	private int year;
	
	// creates setters for the values
	public void setName(String newName){
		this.name = newName;
	}
	public void setYear(int newYear){
		this.year = newYear;
	}
	// creates getters for the values
	public String getName(){
		return this.name;
	}
	public int getYear(){
		return this.year;
	}

}
