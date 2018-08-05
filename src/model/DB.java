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
	
	public Connection connection;
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
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS Teacher(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, SSN TEXT NOT NULL)");
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS Student(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, SSN TEXT NOT NULL)");
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS Course(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, teacher_id INTEGER NOT NULL, FOREIGN KEY(teacher_id) REFERENCES Teacher(id) ON UPDATE CASCADE)");
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS Grade(student_id INTEGER, course_id INTEGER, value INTEGER NOT NULL, PRIMARY KEY(student_id, course_id), FOREIGN KEY(student_id) REFERENCES Student(id) ON UPDATE CASCADE, FOREIGN KEY(course_id) REFERENCES Course(id) ON UPDATE CASCADE)");
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS Class(id INTEGER PRIMARY KEY AUTOINCREMENT, year INTEGER NOT NULL, student_id INTEGER, FOREIGN KEY(student_id) REFERENCES Student(id) ON UPDATE CASCADE)");
		
	}
	public void insert(model.FileData m_filedata,String table)throws SQLException {
		try{
			connection.setAutoCommit(false);
			// teacher
			if(table == "teacher"){
				statement.executeUpdate("INSERT INTO Teacher(name,SSN)VALUES(" + "'" + m_filedata.getListOfData().get(0).getTeacherName() + "'" +  ", '" + m_filedata.getListOfData().get(0).getTeacherSSN() + "'" + ")");
			}
			// student
			if(table == "studentAndClass"){
				statement.executeUpdate("INSERT INTO Student(name,SSN) VALUES(" + "'" + m_filedata.getListOfData().get(0).getStudentName() + "'" + ", '" + m_filedata.getListOfData().get(0).getStudentSSN() + "'" +")");
			}
			// course
			if(table == "course"){
			statement.executeUpdate("INSERT INTO Course(name, teacher_id) VALUES(" + "'" + m_filedata.getListOfData().get(0).getCourseName() + "'" + ", " + m_filedata.getListOfData().get(0).getTeacherId() + ")" );
			}
			// grade
			if(table == "grade"){
			statement.executeUpdate("INSERT INTO Grade(student_id, course_id, value) VALUES(" + m_filedata.getListOfData().get(0).getStudentId() + ", " + m_filedata.getListOfData().get(0).getCourseId() + ", " + m_filedata.getListOfData().get(0).getGrade() + ")");
			}
			// class (för id hämta längden av student plussa på med 1
			if(table == "studentAndClass"){
				statement.executeUpdate("INSERT INTO Class(year, student_id) VALUES(" + m_filedata.getListOfData().get(0).getStudentClassYear() + ", " + m_filedata.getListOfData().get(0).getStudentId() + ")");
			}
			connection.commit();
		}catch(Exception e){
			System.err.println("An error occurred. The data was not saved properly. Please try again!!!");
			System.err.println(e.getMessage());
			connection.rollback();
		}
	}
	public Connection getConn(){
		return connection;
	}
	/* QUERIES WHO FETCH DATA TO SUPPORT THE INSERT METODS*/
	// If the social security number for student or teacher exists in the database, this method returns the id for that row
	public ResultSet getStudentOrTeacherId(String SSN, String theTable)throws SQLException {
		
			rs = statement.executeQuery("SELECT id FROM " + theTable + " WHERE SSN = '" + SSN + "'");
			return rs;		
	}
	// If the course name exits in the database, this method returns the id for that row
	public ResultSet getCourseId(String theName)throws SQLException{
		rs = statement.executeQuery("SELECT id FROM Course WHERE name = '" + theName + "'");
		return rs;	
	}
	// If the students or teachers social security number exists in the database, this method returns the name of that person
	public ResultSet getStudentOrTeacherName(String theSSN, String theTable)throws SQLException{
		rs = statement.executeQuery("SELECT name FROM " + theTable + " WHERE SSN = '" + theSSN + "'");
		return rs;	
	}
	// Gets the insert id of student by count the length of student table and add with one (used to insert the student_id in class table the same time as creating the student)
	public ResultSet getInsertIdFromStudent()throws SQLException{
		rs = statement.executeQuery("SELECT COUNT(*) AS presentId FROM Student");
		return rs;	
	}
	/* QUERIES WHO FETCH DATA FROM THE DATABASE FOR THE OUTPUT MENU*/
	// 1. Get average of grade from the whole school FUNKAR!!!!
	public ResultSet getAvgGradeSchool() throws SQLException {
		rs = statement.executeQuery("SELECT AVG(value) AS AVGvalue FROM Grade");
		return rs;	
	}
	// 2. Get average of grade from a specific course FUNKAR!!!!
	public ResultSet getAvgGradeCourse(String theCourse) throws SQLException {
		rs = statement.executeQuery("SELECT AVG(Grade.value) AS AVGvalue FROM Course LEFT JOIN Grade ON (Course.id = Grade.course_id) WHERE Course.name = " + "'" + theCourse + "'");
		return rs;	
	
	}
	// 3. Get average of grade on a specific student JOBBA HÄR!!!
	public ResultSet getAvgGradeStudent(String theStudent) throws SQLException {
		rs = statement.executeQuery("SELECT AVG(value) AS AVGvalue FROM Grade LEFT JOIN Student ON (Grade.student_id = Student.id) WHERE Student.name = " + "'" + theStudent + "'");
		return rs;	
		
	}
	// 4. Which courses does a specific student perform best in (is a "5-student" in)
	public ResultSet getAGradedCoursesForStudent(String theStudent) throws SQLException {
		rs= statement.executeQuery("SELECT Course.name FROM Course LEFT JOIN Grade ON (Course.id = Grade.course_id) LEFT JOIN Student ON (Student.id = Grade.student_id) WHERE Student.name = " + "'" + theStudent + "' AND Grade.value = 5");
		return rs;			
	}
	// 5. Get all the grades in all the courses for a specific student
	public ResultSet getAllGradesForStudent(String theStudent) throws SQLException {
		//System.out.println("SELECT Course.name FROM Course LEFT JOIN Grade ON (Course.id = Grade.course_id) LEFT JOIN Student ON (Grade.student_id = Student.id) WHERE Student.name = " + "'" + theStudent + "' UNION SELECT Grade.value FROM Grade LEFT JOIN Course ON (Grade.course_id = Course.id) LEFT JOIN Student ON (Grade.student_id = Student.id) WHERE Student.name = " + "'" + theStudent + "' ");
		//rs = statement.executeQuery("SELECT Course.name FROM Course LEFT JOIN Grade ON (Course.id = Grade.course_id) LEFT JOIN Student ON (Grade.student_id = Student.id) WHERE Student.name = " + "'" + theStudent + "' UNION SELECT Grade.value FROM Grade LEFT JOIN Course ON (Grade.course_id = Course.id) LEFT JOIN Student ON (Grade.student_id = Student.id) WHERE Student.name = " + "'" + theStudent + "' ");¨
		//rs = statement.executeQuery("SELECT Course.name FROM Course LEFT JOIN Grade ON (Course.id = Grade.course_id) LEFT JOIN Student ON (Grade.student_id = Student.id) WHERE Student.name = 'Anna Book' UNION SELECT Grade.value AS gradeValue FROM Grade LEFT JOIN Course ON (Grade.course_id = Course.id) LEFT JOIN Student ON (Grade.student_id = Student.id) WHERE Student.name = 'Anna Book'");
		
		//rs = statement.executeQuery("SELECT Grade.value AS gradeValue FROM Grade LEFT JOIN Course ON (Grade.course_id = Course.id) LEFT JOIN Student ON (Grade.student_id = Student.id) WHERE Student.name = 'Anna Book'");
		//rs = statement.executeQuery("SELECT Course.name FROM Course LEFT JOIN Grade ON (Course.id = Grade.course_id) LEFT JOIN Student ON (Grade.student_id = Student.id) WHERE Student.name = 'Anna Book' UNION SELECT Grade.value FROM Grade LEFT JOIN Course ON (Grade.course_id = Course.id) LEFT JOIN Student ON (Grade.student_id = Student.id) WHERE Student.name = 'Anna Book'");
		rs = statement.executeQuery("SELECT Course.name, Grade.value FROM Course Course LEFT JOIN Grade ON (Course.id = Grade.course_id) LEFT JOIN Student ON (Grade.student_id = Student.id) WHERE Student.name = " + "'" + theStudent + "' ");
		return rs;	
// FUNKAR SELECT Course.name FROM Course LEFT JOIN Grade ON (Course.id = Grade.course_id) LEFT JOIN Student ON (Student.id = Grade.student_id) WHERE Student.name = "Agnes Carlsson" UNION SELECT Grade.value FROM Course LEFT JOIN Grade ON (Course.id = Grade.course_id) LEFT JOIN Student ON (Student.id = Grade.student_id) WHERE Student.name = "Agnes Carlsson";
	}
	
}
