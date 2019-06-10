package front;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SFProfileGenerator {
	
	public static void main(String args[]) { 
        try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		@SuppressWarnings("unused")
		GUIFrame frame = new GUIFrame();
	}
}
