/**
 * 
 */
package view;

import java.util.Scanner;

/**
 * @author cw222kq
 *
 */
public class Console {
	
	public boolean start = true;
	static Scanner scan = new Scanner(System.in);
	static Scanner scanner = new Scanner(System.in);
	static Scanner in = new Scanner(System.in);
	protected char inputResult;
	

	/**
	 * 
	 */
	public Console() {
		
	}
	// Will be executed first
	public void printStartMenu(){
		if(start){
			System.out.println("Welcome to Grade statistics!!!"); 
			System.out.println("Please make your selection");
			System.out.println("<1> INSERT DATA");
			System.out.println("<2> OUTPUT DATA");
			System.out.println("<Q> Quit");
			start = false;
		}
		setUsersChoice();
		
		
		
	}
	// Get the input value from the keyboard. Written with inspiration from: https://stackoverflow.com/questions/15446689/what-is-the-use-of-system-in-read
	protected static char getInput() {
		char inChar;
	    try {
	      inChar = Character.toUpperCase((char)System.in.read());
	      System.out.print("You entered ");
	      System.out.println(inChar);
	     
	      return inChar;
	    } 
	    catch (java.io.IOException e) {
	      System.out.println("inne i catchen");
	      System.out.println("" + e);
	      
	      return 0;
	    }
	}
	// Will be executed if the user choose 1
	public void printInsertDataMenu(model.Student m_student, model.Teacher m_teacher, model.Course m_course, model.Data m_data){
		
		System.out.println("1. INSERT DATA");
		System.out.println("INSERT DATA ABOUT THE STUDENT");
		System.out.println("Insert student id");
		m_student.setId(scan.nextInt());
		System.out.println("Insert student name");
		m_student.setName(scanner.nextLine());
		System.out.println("Insert student year");
		m_student.setYear(scan.nextInt());
		System.out.println("Insert student grade");
		m_student.setGrade(scanner.nextLine());
		System.out.println("INSERT DATA ABOUT THE COURSE");
		System.out.println("Insert course id");
		m_course.setId(scan.nextInt()); 
		System.out.println("Insert course name");
		m_course.setName(scanner.nextLine());
		System.out.println("INSERT DATA ABOUT THE TEACHER");
		System.out.println("Insert teacher id");
		m_teacher.setId(scan.nextInt()); 
		System.out.println("Insert teacher name");
		m_teacher.setName(scanner.nextLine());
		scan.close();
		scanner.close();
		
		// gör till egen metod i metoden IOM att en metod inte ska göra två saker
		m_data.setStudentId( m_student.getId());
		m_data.setStudentName(m_student.getName());
		m_data.setStudentYear(m_student.getYear());
		m_data.setGrade(m_student.getGrade());
		m_data.setCourseId(m_course.getId());
		m_data.setCourseName(m_course.getName());
		m_data.setTeacherId(m_teacher.getId());
		m_data.setTeacherName(m_teacher.getName());
		
		
	}
	// Will be executed if the user choose 2
	public void printOutputDataMenu(){
		
		System.out.println("2. OUTPUT DATA");
			
	}
	// Will be executed if the user choose Q
	public void quit(){
		System.out.println("Q. Quit");
		System.exit(0);
		
	}
	protected void setUsersChoice(){
		inputResult = getInput();
	}
	public char getUsersChoice(){
		return this.inputResult;
	}
	public void printArray(model.FileData a_fileData, Iterable<model.Data> a_data){
		for(model.Data d: a_data){
			System.out.println("Student id: " + d.getStudentId() + ", " + "Student name: " + d.getStudentName()+ ", " + "Årskurs: " + d.getStudentYear() + ", " + "Kursid: " + d.getCourseId() + ", " + "Betyg: " + d.getGrade() + ", " + "Kursnamn: " + d.getCourseName() + ", " + "Lärarid: " + d.getTeacherId() + ", " + "Lärare: "  + d.getTeacherName());
		}
		
	}
	
}
