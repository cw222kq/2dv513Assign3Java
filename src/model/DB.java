/** written by inspiration from: https://github.com/xerial/sqlite-jdbc#usage and http://www.sqlitetutorial.net/sqlite-java/create-database/
 * 
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author cw222kq
 *
 */
public class DB {
	
	Connection connection;
	Statement statement;
	ResultSet rs;
	boolean isInTheDB = true;
	
	public DB() {
		
		connection = null;	
		
	}
	public void connect() throws SQLException, ClassNotFoundException {
	
		Class.forName("org.sqlite.JDBC");
		
		connection = DriverManager.getConnection("jdbc:sqlite:database/school.db");
		statement = connection.createStatement();
		
		// create tables if they not already exists
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS Teacher(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, SSN TEXT NOT NULL)");
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS Student(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, SSN TEXT NOT NULL)");
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS Course(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, teacher_id INTEGER NOT NULL, FOREIGN KEY(teacher_id) REFERENCES Teacher(id) ON UPDATE CASCADE)");
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS Grade(student_id INTEGER, course_id INTEGER, value INTEGER NOT NULL, PRIMARY KEY(student_id, course_id), FOREIGN KEY(student_id) REFERENCES Student(id) ON UPDATE CASCADE, FOREIGN KEY(course_id) REFERENCES Course(id) ON UPDATE CASCADE)");
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS Class(id INTEGER PRIMARY KEY AUTOINCREMENT, year INTEGER NOT NULL, student_id INTEGER, FOREIGN KEY(student_id) REFERENCES Student(id) ON UPDATE CASCADE)");
		
	}
	// Inserts data to the different tables that is in the db
	public void insert(model.FileData m_filedata,String table)throws SQLException {
		try{
			connection.setAutoCommit(false);
			// teacher
			if(table == "teacher"){
				if(isInTheDB) {return;}
				if(m_filedata.getListOfData().get(0).getTeacherName() == null){return;}
				statement.executeUpdate("INSERT INTO Teacher(name,SSN)VALUES(" + "'" + m_filedata.getListOfData().get(0).getTeacherName() + "'" +  ", '" + m_filedata.getListOfData().get(0).getTeacherSSN() + "'" + ")");
			}
			// student and class
			if(table == "studentAndClass"){
				if(isInTheDB) {return;}
				if(m_filedata.getListOfData().get(0).getStudentName() == null){return;}
				statement.executeUpdate("INSERT INTO Student(name,SSN) VALUES(" + "'" + m_filedata.getListOfData().get(0).getStudentName() + "'" + ", '" + m_filedata.getListOfData().get(0).getStudentSSN() + "'" +")");
				statement.executeUpdate("INSERT INTO Class(year, student_id) VALUES(" + m_filedata.getListOfData().get(0).getStudentClassYear() + ", " + m_filedata.getListOfData().get(0).getStudentId() + ")");
			}
			// course
			if(table == "course"){
				if(m_filedata.getListOfData().get(0).getCourseName() == null || m_filedata.getListOfData().get(0).getTeacherId() == 0){return;}
				statement.executeUpdate("INSERT INTO Course(name, teacher_id) VALUES(" + "'" + m_filedata.getListOfData().get(0).getCourseName() + "'" + ", " + m_filedata.getListOfData().get(0).getTeacherId() + ")" );
			}
			// grade
			if(table == "grade"){
				if(m_filedata.getListOfData().get(0).getGrade() == 0){return;}
				statement.executeUpdate("INSERT INTO Grade(student_id, course_id, value) VALUES(" + m_filedata.getListOfData().get(0).getStudentId() + ", " + m_filedata.getListOfData().get(0).getCourseId() + ", " + m_filedata.getListOfData().get(0).getGrade() + ")");
			}
			isInTheDB = false;
			connection.commit(); 
		}catch(Exception e){
			e.printStackTrace();	
			connection.rollback();
		}
	}
	public ResultSet getRS(){
		return this.rs;
	}
	// Checks if the student or teacher already exists in the database
	// This method is executed each time we want to add a new person (teacher or student) to the db, to check if she/he already exists
	public boolean isStudentOrTeacherInDB(String SSN, String theTable) {
		int id = 0;
		id = getStudentOrTeacherId(SSN,theTable);
		if(id == 0){ 
			isInTheDB = false;
			return isInTheDB;
		}
		isInTheDB = true;	
		return isInTheDB;
	}
	/* QUERIES THAT FETCH DATA TO SUPPORT THE INSERT METODS*/
	// If the social security number for student or teacher exists in the database, this method returns the id for that row
	public int getStudentOrTeacherId(String SSN, String theTable) { 
		rs = null;
		int id = 0;
		try {
			rs = statement.executeQuery("SELECT id FROM " + theTable + " WHERE SSN = '" + SSN + "'");
			while(rs.next()){ 
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;		
	}
	// If the course name exits in the database, this method returns the id for that row
	public int getCourseId(String theName){
		rs = null;
		int id = 0;
		try {
			rs = statement.executeQuery("SELECT id FROM Course WHERE name = '" + theName + "'");
			while(rs.next()){
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;	
	}
	// If the students or teachers social security number exists in the database, this method returns the name of that person
	public String getStudentOrTeacherName(String theSSN, String theTable){
		ResultSet r = null;
		String theName = null;
		try {
			r = statement.executeQuery("SELECT name FROM " + theTable + " WHERE SSN = '" + theSSN + "'");
			while(r.next()){ 
				theName = r.getString("name");
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				r.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return theName;	
	}
	// Returns the number of rows of the student table. The return value is added with one to get the new students id for the class table, used when added new student to the db
	public int getInsertIdFromStudent(){
		rs = null;
		int theID = 0;
		try {
			rs = statement.executeQuery("SELECT COUNT(*) AS presentId FROM Student");
			while(rs.next()){
				theID = rs.getInt("presentId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return theID;	
	}
	/* QUERIES WHO FETCH DATA FROM THE DATABASE FOR THE OUTPUT MENU*/
	// 1. Get average of grade from the whole school
	public ResultSet getAvgGradeSchool() {
		rs = null;
		try {
			rs = statement.executeQuery("SELECT AVG(value) AS AVGvalue FROM Grade");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return rs;	
	}
	// 2. Get average of grade from a specific course
	public ResultSet getAvgGradeCourse(String theCourse) {
		rs = null;
		try {
			rs = statement.executeQuery("SELECT AVG(Grade.value) AS AVGvalue FROM Course LEFT JOIN Grade ON (Course.id = Grade.course_id) WHERE Course.name = " + "'" + theCourse + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;	
	
	}
	// 3. Get average of grade on a specific student
	public ResultSet getAvgGradeStudent(String theStudent) {
		rs = null;
		try {
			rs = statement.executeQuery("SELECT AVG(value) AS AVGvalue FROM Grade LEFT JOIN Student ON (Grade.student_id = Student.id) WHERE Student.name = " + "'" + theStudent + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return rs;	
		
	}
	// 4. Which courses does a specific student performs best in (is a "5-student" in)
	public ResultSet getAGradedCoursesForStudent(String theStudent) {
		rs = null;
		try {
			rs= statement.executeQuery("SELECT Course.name FROM Course LEFT JOIN Grade ON (Course.id = Grade.course_id) LEFT JOIN Student ON (Student.id = Grade.student_id) WHERE Student.name = " + "'" + theStudent + "' AND Grade.value = 5");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return rs;			
	}
	// 5. Get all the grades in all the courses for a specific student
	public ResultSet getAllGradesForStudent(String theStudent) {
		rs = null;
		try {
			rs = statement.executeQuery("SELECT Course.name, Grade.value FROM Course Course LEFT JOIN Grade ON (Course.id = Grade.course_id) LEFT JOIN Student ON (Grade.student_id = Student.id) WHERE Student.name = " + "'" + theStudent + "' ");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return rs;	
	}
	// Closes the result set and the connection. Executed when user wants to quit the application
	public void closeConnection(){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
