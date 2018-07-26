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
	
	public App() {
		
	}
	// initiates the values
	private char inputResult;
	model.Student a_student = new model.Student();
	model.Course a_course = new model.Course();
	model.Teacher a_teacher = new model.Teacher();
	model.Data a_data = new model.Data();
	model.FileData a_fileData = new model.FileData();
	model.DB a_db = new model.DB();
	
	// run app if true, if false stop
	public boolean runApp(view.Console a_view){
		
		// connect to the db
		try {
			a_db.connect();
		} catch (SQLException e) {
			a_view.printErrorMessage(e);
		} catch (ClassNotFoundException ex){
			a_view.printErrorMessage(ex);
		}
		
		// present the start menu to the user
		a_view.printStartMenu();
		
		// getting the input from the user
		inputResult = a_view.getUsersChoice();
		
		// 1 = Insert data
		if(inputResult == '1') {
			
			// present the insert menu to the user
			a_view.printInsertDataMenu(a_student, a_teacher, a_course);
			
			// transfer the data to the Data class
			a_data.setStudentId(a_student.getId());
			a_data.setStudentName(a_student.getName());
			a_data.setStudentYear(a_student.getYear());
			a_data.setGrade(a_student.getGrade());
			a_data.setCourseId(a_course.getId());
			a_data.setCourseName(a_course.getName());
			a_data.setTeacherId(a_teacher.getId());
			a_data.setTeacherName(a_teacher.getName());
			a_fileData.getListOfData().add(a_data);
			
			// insert the data in the database
			try {
			
				a_db.insert(a_fileData);
				
			} catch (SQLException e) {
				
				a_view.printErrorMessage(e);;
			}
			
			a_view.printArray(a_fileData, a_fileData.getArray());
		}
		// 2 = Output data
		if(inputResult == '2') {
			a_view.printOutputDataMenu();
		}
		// Q = quit
		if(inputResult == 'Q') {
			a_view.quit();
		}
			
		return true;
		
	}

	
}
