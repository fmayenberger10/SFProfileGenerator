package front;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SFProfileGenerator {
	
	public static void main(String args[]) { 
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		@SuppressWarnings("unused")
		GUIFrame frame = new GUIFrame();
	}
}
