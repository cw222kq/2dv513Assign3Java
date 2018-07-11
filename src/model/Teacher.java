/**
 * 
 */
package model;

/**
 * @author cw222kq
 *
 */
public class Teacher {

	// initiates the values
		private int id;
		private String name;
		
		// Constructor with arguments to set the values
		public Teacher(int id, String name) {
			
			this.id = id;
			this.name = name;
			
		}
		// Constructor without arguments
		public Teacher() {

				
		}
		// creates getters for the values
		public int getId(){
			return this.id;
		}
		public String getName(){
			return this.name;
		}
		// creates getters for the values
		public void setId(int newId){
			this.id = newId;
		}
		public void setName(String newName){
			this.name = newName;
		}

}
