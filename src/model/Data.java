/** Delete this class. It is divided into the classes Student, Course and Teather instead
 * 
 */
package model;

/**
 * @author cw222kq
 *
 */
public class Data {

	private int studentId;
	private String studentName;
	private int studentYear;
	private int courseId;
	private char grade;
	private String courseName;
	private int teacherId;
	private String teacherName;
	
	public Data(int studentId, String studentName, int studentYear, int courseId, char grade, String courseName, int teacherId, String teacherName) {
		
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentYear = studentYear;
		this.courseId = courseId;
		this.grade = grade;
		this.courseName = courseName;
		this.teacherId = teacherId;
		this.teacherName = teacherName;
		
	}
	public Data(){}
	
	public void setStudentId(int studentId){
		this.studentId = studentId;
	}
	public void setStudentName(String studentName){
		this.studentName = studentName;
	}
	public void setStudentYear(int studentYear){
		this.studentYear = studentYear;
	}
	public void setCourseId(int courseId){
		this.courseId = courseId;
	}
	public void setGrade(char grade){
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
	public int getStudentId(){
		return this.studentId;
	}
	public String getStudentName(){
		return this.studentName;
	}
	public int getStudentYear(){
		return this.studentYear;
	}
	public int getCourseId(){
		return this.courseId;
	}
	public char getGrade(){
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
	/*@Override
	public String toString(){
		return "Student id: " + this.studentId + ", " + "Student name: " + this.studentName + ", " + "Årskurs: " + this.studentYear + ", " + "Kursid: " + this.courseId + ", " + "Betyg: " + this.grade + ", " + "Kursnamn: " + this.courseName + ", " + "Lärarid: " + this.teacherId + ", " + "Lärare: "  + this.teacherName;
		
	}*/
	/*@Override
	public String toString(){
		return this.userName + "\t" + this.points;
	}*/

}
