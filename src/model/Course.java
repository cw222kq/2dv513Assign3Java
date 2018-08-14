/**
 * 
 */
package model;

/**
 * @author cw222kq
 *
 */
public class Course {

	private int id;
	private String name;
	private int teacher_id;
	
	// getters
	public int getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
	public int getTeacher_id(){
		return this.teacher_id;
	}
	// setters
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
