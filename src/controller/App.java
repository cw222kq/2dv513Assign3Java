/**
 * 
 */
package controller;

import java.sql.SQLException;

/**
 * @author cw222kq
 *
 */
public class App {
	
	// initiates the values
	char inputResult;
	public boolean run = true;
	String table;
	//view.Console a_view = new view.Console();
	model.Student a_student = new model.Student();
	model.Course a_course = new model.Course();
	model.Teacher a_teacher = new model.Teacher();
	model.Data a_data = new model.Data();
	model.FileData a_fileData = new model.FileData();
	model.DB a_db = new model.DB();
	model.StudentClass a_studentClass = new model.StudentClass();
	
	// run app if true, if false stop
	public boolean runApp(view.Console a_view){
		
		System.out.println("Börjar metoden runApp");
		
		// connect to the db if the connection is null
		
		try {
			a_db.connect();
		} catch (SQLException e) {
			a_view.printErrorMessage(e);
		} catch (ClassNotFoundException ex){
			a_view.printErrorMessage(ex);
		}
		
		System.out.println("efter connect");

		// present the start menu to the user. HOPPAR ÖVER DENNA DEN ANDRA GÅNGEN.VARFÖR?
		
		a_view.printStartMenu();
		
		System.out.println("Efter startmenu");
		
		// getting the input from the user
		a_view.setUsersChoice();
		inputResult = a_view.getUsersChoice();
		
		// Q = quit
		if(inputResult == 'Q') {
			a_view.quit(a_db);
			//return false;
			run = false;
		}
		
		// 1 = Insert data 
		if(inputResult == '1') {
			
			// present the insert menu to the user 
			a_view.printInsertDataMenu(a_student, a_teacher, a_course);
			
			// setting and getting the input from the user 
			a_view.setUsersChoice();
			inputResult = a_view.getUsersChoice();
			
			// teacher
			if(inputResult == '1'){
				table = "teacher";
				a_view.printInsertTeacher(a_teacher);
				a_data.setTeacherName(a_teacher.getName());
				a_data.setTeacherSSN(a_teacher.getSSN());
				inputResult = 0;
				
			}
			// student and class
			if(inputResult == '2') {
				table = "studentAndClass";
				a_view.printInsertStudent(a_student, a_studentClass, a_db);
				a_data.setStudentClassYear(a_studentClass.getYear());
				a_data.setStudentName(a_student.getName());
				a_data.setStudentSSN(a_student.getSSN());
				a_data.setStudentId(a_student.getId());
				inputResult = 0;
				
			}
			// course
			if(inputResult == '3') {
				table = "course";
				a_view.printInsertCourse(a_course, a_teacher, a_db);
				a_data.setCourseName(a_course.getName());
				a_data.setTeacherId(a_teacher.getId());
				inputResult = 0;
				
			}
			// grade
			if(inputResult == '4') {
				table = "grade";
				a_view.printInsertGrade(a_student, a_course, a_db, a_studentClass);
				a_data.setStudentId(a_student.getId());
				a_data.setCourseId(a_course.getId());
				a_data.setGrade(a_student.getGrade());
				inputResult = 0;
				
			}
			a_fileData.getListOfData().add(a_data);
				
			// insert the data in the database
			try {
				
				a_db.insert(a_fileData,table);
				
			} catch (SQLException e) {
				System.out.println("Inside app.java running the insert method from db ending up in the catch");
				a_view.printErrorMessage(e);;
			}
			
		}
		// 2 = Output data
		if(inputResult == '2') {
			inputResult = 0;
			a_view.printOutputDataMenu();
			// getting the input from the user
			a_view.setUsersChoice();
			inputResult = a_view.getUsersChoice();
			// if user select 1 from the Output data menu
			if(inputResult == '1') {
				inputResult = 0;
				a_view.printResultFromAvgGradeSchool(a_db);
			}
			// if user select 2 from the Output data menu 
			if(inputResult == '2') {
				inputResult = 0;
				a_view.printResultFromAvgGradeCourse(a_db);
			}
			// if user select 3 from the Output data menu
			if(inputResult == '3') {
				inputResult = 0;
				a_view.printResultFromAvgGradeStudent(a_db);
				
			}
			// if user select 4 from the Output data menu JOBBA HÄR!!!
			if(inputResult == '4') {
				inputResult = 0;
				a_view.printResultFromHighestGradedCoursesForStudent(a_db);
			}
			// if user select 5 from the Output data menu
			if(inputResult == '5') {
				inputResult = 0;
				a_view.printResultFromCoursesForStudent(a_db);
			}
			
		}
					
		return run;	
	}

	
}
