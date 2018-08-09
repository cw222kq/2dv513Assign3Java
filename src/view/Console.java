/**
 * 
 */
package view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * @author cw222kq
 *
 */
public class Console {
	
	public boolean start = true;
	static Scanner scan;
	static Scanner scanner;
	protected char inputResult;
	protected boolean valid = false;
	char inChar;
	ResultSet r;
	DecimalFormat df = new DecimalFormat("#.#");
	
	// Will be executed first
	public void printStartMenu(){
		if(start){
			System.out.println("Welcome to Grade statistics!!!"); 
			start = false;
		}
		System.out.println("Please make your selection");
		System.out.println("<1> INSERT DATA");
		System.out.println("<2> OUTPUT DATA");
		System.out.println("<Q> Quit");
			
	}
	// Get the input value from the keyboard. Written with inspiration from: https://stackoverflow.com/questions/15446689/what-is-the-use-of-system-in-read
	protected char getInput() {
	    try {
	      inChar = Character.toUpperCase((char)System.in.read());
	      // don´t return value if value is enter or line feed
	      while (inChar == '\r' || inChar =='\n') {
	    	  inChar = Character.toUpperCase((char)System.in.read());
		  }
	      System.out.print("You entered ");
	      System.out.println(inChar);
	     
	      return inChar;
	    } 
	    catch (java.io.IOException e) {
	      System.out.println("inne i catchen");
	      System.err.println("" + e);
	      
	      return 0;
	    }
	}
	// Will be executed if the user choose 1 from the start menu
	public void printInsertDataMenu(model.Student m_student, model.Teacher m_teacher, model.Course m_course){
		
		System.out.println("INSERT DATA...");
		System.out.println("<1> ABOUT THE TEACHER");
		System.out.println("<2> ABOUT THE STUDENT");
		System.out.println("<3> ABOUT THE COURSE");
		System.out.println("<4> ABOUT THE GRADE");
		
	}
	// Will be executed if the user choose 2 from the start menu
	public void printOutputDataMenu(){
		
		System.out.println("OUTPUT DATA");
		System.out.println("CHOOSE WHICH DATA YOU WANT TO FETCH");
		System.out.println("<1> GET AVERAGE OF GRADE FROM THE WHOLE SCHOOL");
		System.out.println("<2> GET AVERAGE OF GRADE FROM A SPECIFIC COURSE");
		System.out.println("<3> GET AVERAGE OF GRADE ON A SPECIFIC STUDENT");
		System.out.println("<4> IN WHICH COURSES DOES A SPECIFIC STUDENT PERFORMS BEST (i.e have the highest grade, is a 5- student)");
		System.out.println("<5> GET ALL THE GRADES IN ALL THE COURSES FOR A SPECIFIC STUDENT");
		
			
	}
	// Will be executed if the user choose Q from the start menu
	public void quit(model.DB m_db){
		System.out.println("Q. Quit");
		System.exit(0);
		try {
			m_db.connection.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		//return true;
	}
	// Will be executed if the user choose 1 from the insert menu
	public void printInsertTeacher(model.Teacher m_teacher, model.DB m_db){
		validateInputString("Insert teacher social security number(yyyymmdd-xxxx)", true);
		m_teacher.setSSN(scanner.next()); 
		//checks if the SSN already exists in the database
		if(m_db.isStudentOrTeacherInDB(m_teacher.getSSN(),"teacher") == true){
			System.err.println("The teacher already exists in the database");
			System.out.println("innåti insertstudentorteacher i consoleklassen");
			return;
		}
		validateInputString("Insert teacher name", false);
		m_teacher.setName(scanner.nextLine());
		System.out.println("efter m_teacher set name");
		System.out.println("Teacher name: " + m_teacher.getName()+ ", Teacher security number: " + m_teacher.getSSN());

	}
	// Will be executed if the user choose 2 from the insert menu
	public void printInsertStudent(model.Student m_student, model.StudentClass m_studentClass, model.DB m_db){
		validateInputInteger("Insert student year");
		m_studentClass.setYear(scan.nextInt());
		validateInputString("Insert students social security number(yyyymmdd-xxxx)", true);
		m_student.setSSN(scanner.next());
		if(m_db.isStudentOrTeacherInDB(m_student.getSSN(),"student") == true){
			System.err.println("The student already exists in the database");
			System.out.println("innåti insertstudentorteacher i consoleklassen");
			return;
		}
		validateInputString("Insert student name", false);
		m_student.setName(scanner.nextLine());
		// Gets the last saved students id
		int studentId = m_db.getInsertIdFromStudent();
		// Adding it with one to get this students id
		studentId++;
		m_student.setId(studentId);
		
	    System.out.println("Student year: " + m_studentClass.getYear() + ", Student social security number: " + m_student.getSSN()+ ", Student name: " + m_student.getName());
	}
	// Will be executed if the user choose 3 from the insert menu
	public void printInsertCourse(model.Course m_course, model.Teacher m_teacher, model.DB m_db){
		int theId = 0;
		String theName = null;
		validateInputString("Insert course name",false);
		m_course.setName(scanner.next());
		validateInputString("Insert the social security number(yyyymmdd-xxxx)for the teacher responsible for the course", true);
		m_teacher.setSSN(scanner.nextLine());
		theId = m_db.getStudentOrTeacherId(m_teacher.getSSN(),"Teacher");
		if(theId == 0){
			System.err.println("The teacher with the social security number: " + m_teacher.getSSN() + " does not exists in the database. Please insert the teacher first!");
			return;
		}
		// gets the teacher name from the social security number
		m_teacher.setId(theId);
		theName = m_db.getStudentOrTeacherName(m_teacher.getSSN(),"Teacher");
		m_teacher.setName(theName);
		System.out.println("Course: " + m_course.getName() + ", Teacher security number: " + m_teacher.getSSN() + ", Teacher name: " + m_teacher.getName());					
	}
	// Will be executed if the user choose 4 from the insert menu FUNKAR OM MAN FYLLER I FEL PERSONNR BÖRJAR DEN OM
	public void printInsertGrade(model.Student m_student, model.Course m_course, model.DB m_db, model.StudentClass m_studentClass){
		int theId = 0;
		String theName = null;
		// gets the student id for the grade table
		validateInputInteger("Insert year for the student you wish to grade");
		m_studentClass.setYear(scan.nextInt());
		validateInputString("Insert social security number(yyyymmdd-xxxx) for the student you wish to grade", true);
		m_student.setSSN(scanner.nextLine());
		theId = m_db.getStudentOrTeacherId(m_student.getSSN(), "Student");
		if(theId == 0){
			System.err.println("The student with the social security number: " + m_student.getSSN() + " does not exists in the database. Please insert the student first!");
			return;
		}
		m_student.setId(theId);
		
		// gets the name of the student from the student social security number 
		theName = m_db.getStudentOrTeacherName(m_student.getSSN(), "Student");
		m_student.setName(theName);
	
		// gets the course id for the grade table
		validateInputString("Insert the name of the course which you want to grade " + m_student.getName() + " in", false);
		m_course.setName(scanner.nextLine());
		theId = m_db.getCourseId(m_course.getName());
		m_course.setId(theId);
		
		validateInputInteger("Insert " + m_student.getName() + "s grade");
		m_student.setGrade(scan.nextInt());
		System.out.println("Year: " + m_studentClass.getYear() + ", Name: " + m_student.getName() + ", Course: " + m_course.getName() + ", Grade: " + m_student.getGrade());	
	}
	public void setUsersChoice(){
		inputResult = getInput();
	}
	public char getUsersChoice(){
		return this.inputResult;
	}
	public void printErrorMessage(Exception e){
		System.err.println(e.getMessage());
	}
	// validate the input value and checks that it is can't be parsed to an integer if its not a social security number FORTS HÄR!!!!!!!!!!!
	/* metodenprotected void validateInputString(String inputMessage, boolean SSN){
		System.out.println("Inne i BÖRJAN av validateInputString i consoleklassen");
		valid = false;
		while (valid == false) {
			System.out.println(inputMessage);
			scanner = new Scanner(System.in);
			if(!scanner.hasNextInt()){
				valid = true;
				if(SSN == true){
					
				}
			}
			else {
				System.err.println("The input value have to be a STRING. Try again!");
				continue;
			}		
		}
		System.out.println("Inne i SLUTET av validateInputString i consoleklassen");
	}*/
	// test
	protected void validateInputString(String inputMessage, boolean SSN){		// FORTSÄTT HÄR PERSONNUMMER
		System.out.println("Inne i BÖRJAN av validateInputString i consoleklassen");
		valid = false;
		while (valid == false) {
			System.out.println(inputMessage);
			scanner = new Scanner(System.in);
			if(SSN == true){
				if(scanner.hasNextLong()){
					valid = true;
				}
				else {	
					System.err.println("The social security number MUST be ååååmmddxxxx. Try again!");
					continue;
				}
			}
			else {
				if(!scanner.hasNextInt()){
					valid = true;
				}
				else {
					System.err.println("The input value have to be a STRING. Try again!");
					continue;
				}		
			}
		}	
		System.out.println("Inne i SLUTET av validateInputString i consoleklassen");
	}
	// validate the input value and checks that it is an integer
	protected void validateInputInteger(String inputMessage){
		valid = false;
		while (valid == false){
			System.out.println(inputMessage);
			scan = new Scanner(System.in);
			if(scan.hasNextInt()){
				valid = true;
			}
			else {
				System.err.println("The input value have to be a NUMBER. Try again!");
				continue;
			}
		}
	}
	// print out successful message about inserting the data in the database
	public void printSuccessfullyMessage(){
		System.out.println("The data was successfully inserted into the database");
	}
	// PRINT OUT RESULT FROM QUERIES
	// If user choose 1 from output data menu
	public void printResultFromAvgGradeSchool(model.DB m_db) {	
		r = null;
		try {
			r = m_db.getAvgGradeSchool();
			while(r.next()){
				System.out.println("Average grade set by school is: " + df.format(r.getDouble("AVGvalue")));
			}	
		} catch (SQLException e){
			System.err.println(e.getMessage());
		}		
	}
	// If user choose 2 from output data menu
	public void printResultFromAvgGradeCourse(model.DB m_db){
		r = null;
		String theCourse;
		validateInputString("Insert the course name that you want the average grade of", false);
		theCourse = scanner.next();
		try {
			r = m_db.getAvgGradeCourse(theCourse);
			while(r.next()){
				System.out.println("The average grade in the course, " + theCourse +"," + " is: " + df.format(r.getDouble("AVGvalue")));
			}	
		} catch (SQLException e){
			System.err.println(e.getMessage());
		}
	}
	// If user choose 3 from output data menu
	public void printResultFromAvgGradeStudent (model.DB m_db){
		r = null;
		String theName = null;
		validateInputString("Insert the social security number(yyyymmdd-xxxx) of the student you want the average grade of", true);
		String theStudentSSN = scanner.next();
		theName = m_db.getStudentOrTeacherName(theStudentSSN,"Student");
		if(theName == null){
			System.err.println("The student with the social security number: " + theStudentSSN + " does not exists in the database. Please insert the student first!");
			System.err.println("GÅ TILLBAX TILL START MENU");
			return;
		}
		r = m_db.getAvgGradeStudent(theName);
		try {
			while(r.next()){ 
				System.out.println("The average grade for the student, " + theName + "," + " is: " + df.format(r.getDouble("AVGvalue")));
			}	
		} catch (SQLException e){
			System.err.println(e.getMessage());
		}
	}
	// If user choose 4 from output data menu
	public void printResultFromHighestGradedCoursesForStudent (model.DB m_db){
		r = null;
		validateInputString("Insert the social security number(yyyymmdd-xxxx) of the student you want to get the courses with the highest grade from", true);
		String theStudentSSN = scanner.next();
		String theStudent = null;
		theStudent = m_db.getStudentOrTeacherName(theStudentSSN,"Student");
		if(theStudent == null){
			System.err.println("The student with the social security number: " + theStudentSSN + " does not exists in the database. Please insert the student first!");
			return;
		}
		try {
			r = m_db.getAGradedCoursesForStudent(theStudent);
			while(r.next()){
				System.out.println("Course/courses where the student, " + theStudent + "," + " is graded with the highest grade is/are: " + r.getString("name"));
				return;
			}
			if(!r.next()) { System.out.println("The student, " + theStudent + ", is not graded with 5 in any courses.");}
			
		} catch (SQLException e){
			System.err.println(e.getMessage());
		}
	}
	// If user choose 5 from output data menu
	public void printResultFromCoursesForStudent(model.DB m_db){
		r = null;
		validateInputString("Insert the social security number(yyyymmdd-xxxx) of the student you want to get all the grades from", false);
		String theStudentSSN = scanner.next();
		String theStudent = null;
		theStudent = m_db.getStudentOrTeacherName(theStudentSSN,"Student");
		if(theStudent == null){
			System.err.println("The student with the social security number: " + theStudentSSN + " does not exists in the database. Please insert the student first!");
			return;
		}
		try {
			r = m_db.getAllGradesForStudent(theStudent);
			System.out.println("Results for the student, " + theStudent +":");
			while(r.next()){
				System.out.println("Course: " + r.getString("name") + ", Grade: " + r.getInt("value"));
			}	
		} catch (SQLException e){
			System.err.println(e.getMessage());
		}
	}	
}
