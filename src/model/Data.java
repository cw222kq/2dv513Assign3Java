/** Delete this class. It is divided into the classes Student, Course and Teather instead
 * 
 */
package model;

import java.io.Serializable;

/**
 * @author cw222kq
 *
 */
@SuppressWarnings("serial")
public class Data implements Serializable{

	private int studentClassYear;
	private int studentId;
	private String studentName;
	private String studentSSN;
	private int courseId;
	private int grade;
	private String courseName;
	private int teacherId;
	private String teacherName;
	private String teacherSSN;
		
	// setters
	public void setStudentClassYear(int studentClassYear){
		this.studentClassYear = studentClassYear;
	}
	public void setStudentId(int studentId){
		this.studentId = studentId;
	}
	public void setStudentName(String studentName){
		this.studentName = studentName;
	}
	public void setStudentSSN(String studentSSN){
		this.studentSSN = studentSSN;
	}
	public void setCourseId(int courseId){
		this.courseId = courseId;
	}
	public void setGrade(int grade){
		this.grade = grade;
	}
	public void setCourseName(String courseName){
		this.courseName = courseName;
	}
	public void setTeacherId(int teacherId){
		this.teacherId = teacherId;
	}
	public void setTeacherName(String teacherName){
		this.teacherName = teacherName;
	}
	public void setTeacherSSN(String teacherSSN){
		this.teacherSSN = teacherSSN;
	}
	// getters
	public int getStudentClassYear(){
		return this.studentClassYear;
	}
	public int getStudentId(){
		return this.studentId;
	}
	public String getStudentName(){
		return this.studentName;
	}
	public String getStudentSSN(){
		return this.studentSSN;
	}
	public int getCourseId(){
		return this.courseId;
	}
	public int getGrade(){
		return this.grade;
	}
	public String getCourseName(){
		return this.courseName;
	}
	public int getTeacherId(){
		return this.teacherId;
	}
	public String getTeacherName(){
		return this.teacherName;
	}
	public String getTeacherSSN(){
		return this.teacherSSN;
	}
	/*@Override
	public String toString(){
		return "Student id: " + this.studentId + ", " + "Student name: " + this.studentName + ", " + "Årskurs: " + this.studentYear + ", " + "Kursid: " + this.courseId + ", " + "Betyg: " + this.grade + ", " + "Kursnamn: " + this.courseName + ", " + "Lärarid: " + this.teacherId + ", " + "Lärare: "  + this.teacherName;
		
	}*/
	/*@Override
	public String toString(){
		return this.userName + "\t" + this.points;
	}*/

}
