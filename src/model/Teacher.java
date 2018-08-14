/**
 * 
 */
package model;

/**
 * @author cw222kq
 *
 */
public class Teacher {

		private int id;
		private String name;
		private String SSN;
		
		// getters
		public int getId(){
			return this.id;
		}
		public String getName(){
			return this.name;
		}
		public String getSSN(){
			return this.SSN;
		}
		// setters
		public void setId(int newId){
			this.id = newId;
		}
		public void setName(String newName){
			this.name = newName;
		}
		public void setSSN(String newSSN){
			this.SSN = newSSN;
		}

}
