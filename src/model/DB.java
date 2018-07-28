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
	
	public DB() {
		
		connection = null;	
		
	}
	public void connect() throws SQLException, ClassNotFoundException {
	
		Class.forName("org.sqlite.JDBC");
		
		connection = DriverManager.getConnection("jdbc:sqlite:database/school.db");
        statement = connection.createStatement();
        
        // create tables if they not already exists
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS Teacher(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL)");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS Student(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, year INTEGER NOT NULL)");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS Course(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, teacher_id INTEGER NOT NULL, FOREIGN KEY(teacher_id) REFERENCES Teacher(id) ON UPDATE CASCADE)");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS Grade(student_id INTEGER, course_id INTEGER, value INTEGER NOT NULL, PRIMARY KEY(student_id, course_id), FOREIGN KEY(student_id) REFERENCES Student(id) ON UPDATE CASCADE, FOREIGN KEY(course_id) REFERENCES Course(id) ON UPDATE CASCADE)");
  	
	}
	public void insert(model.FileData m_filedata)throws SQLException {
		try{
			connection.setAutoCommit(false);
			// with id
			//statement.executeUpdate("INSERT INTO Student(name, year) VALUES(" + "'" + m_filedata.getListOfData().get(0).getStudentName() + "'" + "," + m_filedata.getListOfData().get(0).getStudentYear()+ ")");
			/*statement.executeUpdate("INSERT INTO Grade VALUES(" + m_filedata.getListOfData().get(0).getStudentId() + "," + m_filedata.getListOfData().get(0).getCourseId() + "," + "'" + m_filedata.getListOfData().get(0).getGrade() + "'" + ")");
			statement.executeUpdate("INSERT INTO Course VALUES(" + m_filedata.getListOfData().get(0).getCourseId() + "," + "'" + m_filedata.getListOfData().get(0).getCourseName() + "'" + "," + m_filedata.getListOfData().get(0).getTeacherId()+ ")");
			statement.executeUpdate("INSERT INTO Teacher VALUES(" + m_filedata.getListOfData().get(0).getTeacherId() + "," + "'" + m_filedata.getListOfData().get(0).getTeacherName() + "'" + ")"); */
			// without id
			/*
			statement.executeUpdate("INSERT INTO Grade VALUES(" +  m_filedata.getListOfData().get(0).getGrade() + "'" + ")");
			*/
			
			// teacher
			//statement.executeUpdate("INSERT INTO Teacher(name)VALUES(" + "'" + m_filedata.getListOfData().get(0).getTeacherName() + "'" + ")");
			
			// student
			//statement.executeUpdate("INSERT INTO Student(year,name) VALUES(" + m_filedata.getListOfData().get(0).getStudentYear() + "," + "'" + m_filedata.getListOfData().get(0).getStudentName() + "'" + ")");
			
			// course
			statement.executeUpdate("INSERT INTO Course(name, teacher_id) VALUES(" + "'" + m_filedata.getListOfData().get(0).getCourseName() + "'" + ", " + m_filedata.getListOfData().get(0).getTeacherId() + ")" );
			
			connection.commit();
			System.out.println("The data was successfully inserted into the database");
		}catch(Exception e){
			System.err.println(e);
			connection.rollback();
		}
	}
	public Connection getConn(){
		return connection;
	}
	/* QUERIES WHO FETCH DATA TO SUPPORT THE INSERT METODS*/
	// course
	// If the name for student, course or teacher exists in the database, this method returns the id for that name
	public ResultSet getTablesId (String theName, String theTable)throws SQLException {
	
		rs = statement.executeQuery("SELECT id FROM " + theTable + " WHERE name = '" + theName + "'");
		return rs;		
	}
	/* QUERIES WHO FETCH DATA FROM THE DATABASE FOR THE OUTPUT MENU*/
	// 1. Get average of grade from the whole school FUNKAR ÄNDRA DESSA LÄGG TILL RS KOLLA PÅ OVANSTÅENDE METOD
	public ResultSet getAvgGradeSchool() throws SQLException {
		statement.executeUpdate("SELECT AVG(value) FROM Grade");
		return rs;	
	}
	// 2. Get average of grade from a specific course FUNKAR
	public ResultSet getAvgGradeCourse(String theCourse) throws SQLException {
		statement.executeUpdate("SELECT AVG(Grade.value) FROM Course LEFT JOIN Grade ON (Course.id = Grade.course_id) WHERE Course.name = " + "'" + theCourse + "'");
		return rs;	
	
	}
	// 3. Get average of grade on a specific student FUNKAR
	public ResultSet getAvgGradeStudent(String theStudent) throws SQLException {
		statement.executeUpdate("SELECT AVG(value) FROM Grade LEFT JOIN Student ON (Grade.student_id = Student.id) WHERE Student.name = " + "'" + theStudent + "'");
		return rs;	
		
	}
	// 4. Which courses does a specific student perform best in (is a "5-student" in)
	public ResultSet getAGradedCoursesForStudent(String theStudent) throws SQLException {
		statement.executeUpdate("SELECT Course.name FROM Course LEFT JOIN Grade ON (Course.id = Grade.course_id) LEFT JOIN Student ON (Student.id = Grade.student_id) WHERE Student.name = " + "'" + theStudent + " AND Grade.value = 5");
		return rs;	
// SELECT Course.name FROM Course LEFT JOIN Grade ON (Course.id = Grade.course_id) LEFT JOIN Student ON (Student.id = Grade.student_id) WHERE Student.name = " + "'" + theStudent + "' AND Grade.value = 5;
// FUNKAR SELECT Course.name FROM Course LEFT JOIN Grade ON (Course.id = Grade.course_id) LEFT JOIN Student ON (Student.id = Grade.student_id) WHERE Student.name = "Agnes Carlsson" AND Grade.value = "A" ";		
	}
	// 5. Get all the grades in all the courses for a specific student
	public ResultSet getAllGradesForStudent(String theStudent) throws SQLException {
		statement.executeUpdate("SELECT Course.name, value FROM Grade WHERE Grade.student_id = Student.id AND Student.name = " + "'" + theStudent + "'");
		return rs;	
// FUNKAR SELECT Course.name FROM Course LEFT JOIN Grade ON (Course.id = Grade.course_id) LEFT JOIN Student ON (Student.id = Grade.student_id) WHERE Student.name = "Agnes Carlsson" UNION SELECT Grade.value FROM Course LEFT JOIN Grade ON (Course.id = Grade.course_id) LEFT JOIN Student ON (Student.id = Grade.student_id) WHERE Student.name = "Agnes Carlsson";
	}
	
}
