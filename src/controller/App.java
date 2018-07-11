/**
 * 
 */
package controller;

import java.util.ArrayList;

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
	
	// run app if true, if false stop
	public boolean runApp(view.Console a_view){
		
		a_view.printStartMenu();
		// getting the input from the user
		inputResult = a_view.getUsersChoice();
		// 1 = Insert data
		if(inputResult == '1') {
			a_view.printInsertDataMenu(a_student, a_teacher, a_course, a_data);
			a_fileData.listOfData.add(a_data);
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
