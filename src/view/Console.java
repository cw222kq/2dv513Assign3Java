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
		        //inChar = (char)System.in.read();
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
	public void printInsertTeacher(model.Teacher m_teacher){
		validateInputString("Insert teacher social security number(yyyymmdd-xxxx)");
		m_teacher.setSSN(scanner.next());
		validateInputString("Insert teacher name");
		m_teacher.setName(scanner.nextLine());
		System.out.println("Teacher name: " + m_teacher.getName()+ ", Teacher security number: " + m_teacher.getSSN());

	}
	// Will be executed if the user choose 2 from the insert menu
	public void printInsertStudent(model.Student m_student, model.StudentClass m_studentClass, model.DB m_db){
		validateInputInteger("Insert student year");
		m_studentClass.setYear(scan.nextInt());
		validateInputString("Insert students social security number(yyyymmdd-xxxx)");
		m_student.setSSN(scanner.next());
		validateInputString("Insert student name");
		m_student.setName(scanner.nextLine());
		// Gets the student_id for this student
		try {
			r = m_db.getInsertIdFromStudent();
			try {
				while(r.next()){
					// Gets the current length of student
					int idCount = r.getInt("presentId"); 
					System.out.println("innan adderingen i while loopen: " + idCount);
					// Adding one to get the new id
					idCount++;
					System.out.println("Efter adderingen: " + idCount);
					// Sets the value 
					m_student.setId(idCount);	
				}	
			} catch (SQLException e){
				System.err.println(e.getMessage());
			}
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("The selected teacher dosen´t exist in the database. Please insert the teacher first");
		}
	    System.out.println("Student year: " + m_studentClass.getYear() + ", Student social security number: " + m_student.getSSN()+ ", Student name: " + m_student.getName());
	}
	// Will be executed if the user choose 3 from the insert menu
	public void printInsertCourse(model.Course m_course, model.Teacher m_teacher, model.DB m_db){
		validateInputString("Insert course name");
		m_course.setName(scanner.next());
		validateInputString("Insert the social security number(yyyymmdd-xxxx)for the teacher responsible for the course");
		m_teacher.setSSN(scanner.nextLine());
		// gets teacher id for the course table
		try {
			r = m_db.getStudentOrTeacherId(m_teacher.getSSN(),"Teacher");
			try {
				while(r.next()){
					m_teacher.setId(r.getInt("id"));
				}	
			} catch (SQLException e){
				System.err.println(e.getMessage());
			}
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("The selected teacher dosen´t exist in the database. Please insert the teacher first");
		}
		// gets the teacher name from the social security number
		try {
			r = m_db.getStudentOrTeacherName(m_teacher.getSSN(),"Teacher");
			try {
				while(r.next()){
					m_teacher.setName(r.getString("name"));
				}	
			} catch (SQLException e){
				System.err.println(e.getMessage());
			}
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("The selected teacher dosen´t exist in the database. Please insert the teacher first");
		}
		System.out.println("Course: " + m_course.getName() + ", Teacher security number: " + m_teacher.getSSN() + ", Teacher name: " + m_teacher.getName());
						
	}
	// Will be executed if the user choose 4 from the insert menu JOBBAR HÄR NU ***!!!!!!!!!!!!!!!!!!!!!!!!!
	public void printInsertGrade(model.Student m_student, model.Course m_course, model.DB m_db, model.StudentClass m_studentClass){
		// gets the student id for the grade table
		validateInputInteger("Insert year for the student you wish to grade");
		m_studentClass.setYear(scan.nextInt());
		validateInputString("Insert social security number(yyyymmdd-xxxx) for the student you wish to grade");
		m_student.setSSN(scanner.nextLine());
		try {
			r = m_db.getStudentOrTeacherId(m_student.getSSN(), "Student");
			try {
				while(r.next()){
					m_student.setId(r.getInt("id"));
				}	
			} catch (SQLException e){
				System.err.println(e.getMessage());
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("The selected student dosen´t exist in the database. Please insert the student first");
		}
		// gets the name of the student from the student social security number
		try {
			r = m_db.getStudentOrTeacherName(m_student.getSSN(), "Student");
			try {
				while(r.next()){
					m_student.setName(r.getString("name"));
				}	
			} catch (SQLException e){
				System.err.println(e.getMessage());
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("The selected student dosen´t exist in the database. Please insert the student first");
		}
		// gets the course id for the grade table
		validateInputString("Insert the name of the course which you want to grade " + m_student.getName() + " in");
		m_course.setName(scanner.nextLine());
		try {
			r = m_db.getCourseId(m_course.getName());
			try {
				while(r.next()){
					m_course.setId(r.getInt("id"));
				}	
			} catch (SQLException e){
				System.err.println(e.getMessage());
			}
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("The selected student dosen´t exist in the database. Please insert the student first");
		}
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
	// validate the input value and checks that it is not an integer
	protected void validateInputString(String inputMessage){
		valid = false;
		while (valid == false) {
			System.out.println(inputMessage);
			scanner = new Scanner(System.in);
			if(!scanner.hasNextInt()){
				valid = true;
			}
			else {
				System.err.println("The input value have to be a STRING. Try again!");
			}		
		}
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
		String theCourse;
		validateInputString("Insert the course name that you want the average grade of");
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
		validateInputString("Insert the social security number(yyyymmdd-xxxx) of the student you want the average grade of");
		String theStudentSSN = scanner.next();
		String theStudent = "";
		// gets the student name from the social security number
		try {
			r = m_db.getStudentOrTeacherName(theStudentSSN,"Student");
			theStudent = r.getString("name");
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}
		try {
			r = m_db.getAvgGradeStudent(theStudent);
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}
		try {
			while(r.next()){
				System.out.println("The average grade for the student, " + theStudent + "," + " is: " + df.format(r.getDouble("AVGvalue")));
			}	
		} catch (SQLException e){
			System.err.println(e.getMessage());
		}
	}
	// If user choose 4 from output data menu
	public void printResultFromHighestGradedCoursesForStudent (model.DB m_db){
		validateInputString("Insert the social security number(yyyymmdd-xxxx) of the student you want to get the courses with the highest grade from");
		String theStudentSSN = scanner.next();
		String theStudent = "";
		// gets the student name from the social security number
		try {
			r = m_db.getStudentOrTeacherName(theStudentSSN,"Student");
			theStudent = r.getString("name");
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}
		try {
			r = m_db.getAGradedCoursesForStudent(theStudent);
			while(r.next()){
				System.out.println("Course/courses where the student, " + theStudent +"," + " is graded with the highest grade is/are: " + r.getString("name"));
			}	
		} catch (SQLException e){
			System.err.println(e.getMessage());
		}
	}
	// If user choose 5 from output data menu
	public void printResultFromCoursesForStudent(model.DB m_db){
		validateInputString("Insert the social security number(yyyymmdd-xxxx) of the student you want to get all the grades from");
		String theStudentSSN = scanner.next();
		String theStudent = "";
		// gets the student name from the social security number
		try {
			r = m_db.getStudentOrTeacherName(theStudentSSN,"Student");
			theStudent = r.getString("name");
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
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
