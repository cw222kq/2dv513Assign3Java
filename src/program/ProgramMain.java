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
		
	
		// while app is true run the program
		//while(p.runApp(c));
		while(p.run){
			p.runApp(c);
		}
		
		// k�rs
		//p.runApp(c);
		

	}

}
