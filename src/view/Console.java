/**
 * 
 */
package view;

import java.sql.ResultSet;
import java.sql.SQLException;
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
		//setUsersChoice();
		
		
		
	}
	// Get the input value from the keyboard. Written with inspiration from: https://stackoverflow.com/questions/15446689/what-is-the-use-of-system-in-read
	protected char getInput() {
	    try {
	      inChar = Character.toUpperCase((char)System.in.read());
	      // don´t return value if value is enter or line feed
	      while (inChar == '\r' || inChar =='\n') {
		        inChar = (char)System.in.read();
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
		
		
		//System.out.println("INSERT DATA ABOUT THE STUDENT");
		/*validateInputInteger("Insert student id");
		m_student.setId(scan.nextInt());*/ 
		
		// student
		/*validateInputInteger("Insert student year");
		m_student.setYear(scan.nextInt());
		System.out.println(m_student.getYear());
		validateInputString("Insert student name");
		m_student.setName(scanner.nextLine());	*/
		
		
		/*validateInputInteger("Insert student grade");
		m_student.setGrade(scan.nextInt());
		System.out.println("INSERT DATA ABOUT THE COURSE");*/
		
		
		// course
		/*validateInputInteger("Insert course id");
		m_course.setId(scan.nextInt());*/
		
		/*validateInputString("Insert course name");
		m_course.setName(scanner.nextLine());
		System.out.println("INSERT DATA ABOUT THE TEACHER");*/
		
		/*validateInputInteger("Insert teacher id");
		m_teacher.setId(scan.nextInt());*/
		
		
		// teacher FUNKAR ÄNTLIGEN!!!
		/*validateInputString("Insert teacher name");
		while (scanner.hasNext()) {
			    System.out.print(scanner.nextLine());
			    m_teacher.setName(scanner.nextLine());
			    System.out.println("Teacher name: " + m_teacher.getName());
			    return;
		}*/
			
		
	}
	// Will be executed if the user choose 2 from the start menu
	public void printOutputDataMenu(){
		
		System.out.println("OUTPUT DATA");
		System.out.println("CHOOSE WHICH DATA YOU WANT TO FETCH");
		System.out.println("<1> GET AVERAGE OF GRADE FROM THE WHOLE SCHOOL");
		System.out.println("<2> GET AVERAGE OF GRADE FROM A SPECIFIC COURSE");
		System.out.println("<3> GET AVERAGE OF GRADE ON A SPECIFIC STUDENT");
		System.out.println("<4> IN WHICH COURSES DOES A SPECIFIC STUDENT PERFORMS BEST (i.e is an A- student)");
		System.out.println("<5> GET ALL THE GRADES IN ALL THE COURSES FOR A SPECIFIC STUDENT");
		
			
	}
	// Will be executed if the user choose Q from the start menu
	public void quit(){
		System.out.println("Q. Quit");
		System.exit(0);
		
	}
	// Will be executed if the user choose 1 from the insert menu
	public void printInsertTeacher(model.Teacher m_teacher){
		validateInputString("Insert teacher name");
		while (scanner.hasNext()) {
			    System.out.print(scanner.nextLine());
			    m_teacher.setName(scanner.nextLine());
			    System.out.println("Teacher name: " + m_teacher.getName());
			    return;
		}	
	}
	// Will be executed if the user choose 2 from the insert menu
	public void printInsertStudent(model.Student m_student){
		
		validateInputInteger("Insert student year");
		m_student.setYear(scan.nextInt());
		validateInputString("Insert student name");
		m_student.setName(scanner.nextLine());
	    System.out.println("Student year: " + m_student.getYear() + ", Student name: " + m_student.getName());
	}
	// Will be executed if the user choose 3 from the insert menu
	public void printInsertCourse(model.Course m_course, model.Teacher m_teacher, model.DB m_db){
		validateInputString("Insert course name");
		m_course.setName(scanner.next());
		validateInputString("Insert the responsible teacher for the course");
		m_teacher.setName(scanner.nextLine());
		try {
			ResultSet r = m_db.getTablesId(m_teacher.getName(),"Teacher");
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
		System.out.println("Course: " + m_course.getName() + ", Teacher: " + m_teacher.getName());
						
	}
	// Will be executed if the user choose 4 from the insert menu JOBBAR HÄR NU ***!!!!!!!!!!!!!!!!!!!!!!!!!
	public void printInsertGrade(model.Student m_student, model.Course m_course, model.DB m_db){
		// to get the student id for the grade table
		validateInputInteger("Insert year for the student you wish to grade");
		m_student.setYear(scan.nextInt());
		validateInputString("Insert name for the student you wish to grade");
		m_student.setName(scanner.nextLine());
		try {
			ResultSet r = m_db.getStudentId(m_student.getName(), m_student.getYear());
			try {
				while(r.next()){
					m_student.setId(r.getInt("id"));
				}	
			} catch (SQLException e){
				System.err.println(e.getMessage());
			}
			//System.out.println("Student: " + m_student.getName() + ", Year: " + m_student.getYear());
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("The selected student dosen´t exist in the database. Please insert the student first");
		}
		// to get the course id for the grade table
		validateInputString("Insert which course you want to grade " + m_student.getName() + " in");
		m_course.setName(scanner.nextLine());
		try {
			ResultSet r = m_db.getTablesId(m_course.getName(),"Course");
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
		System.out.println("Year: " + m_student.getYear() + ", Name: " + m_student.getName() + ", Course: " + m_course.getName() + ", Grade: " + m_student.getGrade());
		
	}
	public void setUsersChoice(){
		inputResult = getInput();
	}
	public char getUsersChoice(){
		return this.inputResult;
	}
	public void printArray(model.FileData a_fileData, Iterable<model.Data> a_data){
		for(model.Data d: a_data){
			// with id
			//System.out.println("Student id: " + d.getStudentId() + ", " + "Student name: " + d.getStudentName()+ ", " + "Årskurs: " + d.getStudentYear() + ", " + "Kursid: " + d.getCourseId() + ", " + "Betyg: " + d.getGrade() + ", " + "Kursnamn: " + d.getCourseName() + ", " + "Lärarid: " + d.getTeacherId() + ", " + "Lärare: "  + d.getTeacherName());
			// without id
			//System.out.println("Student name: " + d.getStudentName() + ", " + "Årskurs: " + d.getStudentYear() + ", " + "Betyg: " + d.getGrade() + ", " + "Kursnamn: " + d.getCourseName() + ", " + "Lärare: "  + d.getTeacherName());
			
			// student
			//System.out.println("Student name: " + d.getStudentName() + ", " + "Årskurs: " + d.getStudentYear());
			//teacher
			//System.out.println("Lärare: "  + d.getTeacherName());
		}	
	}
	public void printErrorMessage(Exception e){
		System.err.println(e);
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
	// print out results from queries
	public void printResultFromAvgGradeSchool(ResultSet r) {
		try {
			while(r.next()){
				System.out.println("Average grade set by school is: " + r.getInt("value"));
			}	
		} catch (SQLException e){
			System.err.println(e.getMessage());
		}
		
	}
	public void printResultFromAvgGradeCourse (ResultSet r, String theCourse){
		try {
			while(r.next()){
				System.out.println("Average grade in the course, " + theCourse +"," + " is: " + r.getInt("value"));
			}	
		} catch (SQLException e){
			System.err.println(e.getMessage());
		}
	}
	public void printResultFromAvgGradeStudent (ResultSet r, String theStudent){
		try {
			while(r.next()){
				System.out.println("Average grade for the student, " + theStudent +"," + " is: " + r.getInt("value"));
			}	
		} catch (SQLException e){
			System.err.println(e.getMessage());
		}
	}
	public void printResultFromAGradedCoursesForStudent (ResultSet r, String theStudent){
		try {
			while(r.next()){
				System.out.println("Courses where the student, " + theStudent +"," + " is graded with A is: " + r.getString("name"));
			}	
		} catch (SQLException e){
			System.err.println(e.getMessage());
		}
	}
	public void printResultFromCoursesForStudent (ResultSet r, String theStudent){
		try {
			while(r.next()){
				System.out.println("Results for the student, " + theStudent +":");
				System.out.println("Course: " + r.getString("name") + "\t" + r.getInt("value"));
			}	
		} catch (SQLException e){
			System.err.println(e.getMessage());
		}
	}
	
		
	
}
