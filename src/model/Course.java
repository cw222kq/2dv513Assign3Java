/**
 * 
 */
package model;

/**
 * @author cw222kq
 *
 */
public class Course {

	// initiates the values
	private int id;
	private String name;
	private int teacher_id;
	
	// Constructor with arguments to set the values
	public Course(int id, String name, int teacher_id) {
		
		this.id = id;
		this.name = name;
		this.teacher_id = teacher_id;
	}
	// Constructor without arguments
	public Course() {

					
	}
	// creates getters for the values
	public int getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
	public int getTeacher_id(){
		return this.teacher_id;
	}
	// creates getters for the values
	public void setId(int newId){
		this.id = newId;
	}
	public void setName(String newName){
		this.name = newName;
	}
	public void setTeacher_id(int newTeacher_id){
		this.teacher_id = newTeacher_id;
	}

}
