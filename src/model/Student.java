/**
 * 
 */
package model;

/**
 * @author cw222kq
 *
 */
public class Student {
	
	private int id;
	private String name;
	private int course_id;
	private int grade;
	private String SSN;
	
	// getters
	public int getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
	public int getCourseId(){
		return this.course_id;
	}
	public int getGrade() {
		return this.grade;
	}
	public String getSSN(){
		return this.SSN;
	}
	// setters
	public void setId(int inID){
		this.id = inID;
	}
	public void setName(String newName){
		this.name = newName;
	}
	public void setCourseId(int newCourse_id){
		this.course_id = newCourse_id;
	}
	public void setGrade(int newGrade) {
		this.grade = newGrade;
	}
	public void setSSN(String newSSN){
		this.SSN = newSSN;
	}

}
