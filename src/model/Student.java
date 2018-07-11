/**
 * 
 */
package model;

/**
 * @author cw222kq
 *
 */
public class Student {
	
	// initiates the values
	private int id;
	private String name;
	private int year;
	private int course_id;
	//private Grade grade;
	private char grade;
	

	// Constructor with arguments to set the values
	public Student(int id, String name, int year, int course_id, char grade) {

		this.id = id;
		this.year = year;
		this.course_id = course_id;
		this.grade = grade;
		
		
	}
	// Constructor without arguments
	public Student() {

		
	}
	// initiates the grade values
	/*private enum Grade {
		A,
		B,
		C,
		D,
		E,
		F;
		
	}*/
	// creates getters for the values
	public int getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
	public int getYear(){
		return this.year;
	}
	public int getCourseId(){
		return this.course_id;
	}
	public char getGrade() {
		return this.grade;
	}
	// creates setters for the values
	public void setId(int newID){
		this.id = newID;
	}
	public void setName(String newName){
		this.name = newName;
	}
	public void setYear(int newYear){
		this.year = newYear;
	}
	public void setCourseId(int newCourse_id){
		this.course_id = newCourse_id;
	}
	public void setGrade(String newGrade) {
		this.grade = newGrade.charAt(0);
	}

}
