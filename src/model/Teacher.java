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
		private String SSN;
		
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
		public String getSSN(){
			return this.SSN;
		}
		// creates getters for the values
		public void setId(int newId){
			this.id = newId;
		}
		public void setName(String newName){
			System.out.println("INNÅT I SET NAME I TEACHERKLASSEN. NAMNET SÄTTS TILL: " + newName);
			this.name = newName;
		}
		public void setSSN(String newSSN){
		//	if(newSSN.length() == 13 && newSSN.charAt(8) == '-'){
		//		System.out.println("Personnret består av 13 tecken och har ett bindestreck i mitten. BRA!!!");
				this.SSN = newSSN;
		//	}
			
		}

}
