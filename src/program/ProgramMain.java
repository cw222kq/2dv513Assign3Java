/**
 * 
 */
package program;

import controller.App;
import view.Console;

/**
 * @author cw222kq
 *
 */
public class ProgramMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		view.Console c = new Console();
		controller.App p = new App();
		
	
	
		//while(p.runApp(c));
		
		// körs
		p.runApp(c);
		
		
		
		// test query
		/*String theName = "Urban";
		int theYear = 2008;
		
		System.out.println("SELECT id FROM Student WHERE name = '" + theName + "' AND year = " + theYear);*/
	}

}
