package front;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PanelRight extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel outp;
	private JPanel rightJButtons;
	private JScrollPane profilePane;
	public JButton cancel;
	public JButton acceptAll;
	private JScrollPane spOut;
	
	public PanelRight (boolean enabled, ArrayList<PanelPerfil> profilePanes) {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder("Output"), 
					BorderFactory.createEmptyBorder(5, 5, 5, 5))));

		acceptAll = new JButton("Accept all");
		cancel = new JButton("Cancel");
		outp = new JPanel(new CardLayout());
		JPanel instructions = new JPanel();
		instructions.setLayout(new BoxLayout(instructions, BoxLayout.PAGE_AXIS));
		instructions.setBorder(BorderFactory.createEmptyBorder(40,20,20,20));
		JLabel ins1 = new JLabel("<-- Select folder with profiles to be updated");
		instructions.add(ins1);
		instructions.add(Box.createVerticalStrut(85));
		JLabel ins2 = new JLabel("<-- Select folder with updated profiles");
		instructions.add(ins2);
		instructions.add(Box.createVerticalStrut(85));
		JLabel ins3 = new JLabel("<-- Select folder to store generated profiles");
		instructions.add(ins3);
		instructions.add(Box.createVerticalStrut(75));
		JLabel ins4 = new JLabel("<-- Insert profile names without \".profile\" (ex. Admin)");
		instructions.add(ins4);
		instructions.add(Box.createVerticalStrut(310));
		JLabel ins5 = new JLabel("<-- Start!");
		instructions.add(ins5);
		outp.add(instructions);
		if(profilePanes.size() != 0) {
			switchViews(true, profilePanes);
		}
		this.add(outp);
		acceptAll.setPreferredSize(new Dimension(100, 40));
		cancel.setPreferredSize(new Dimension(100, 40));
		
		rightJButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		rightJButtons.setMaximumSize(new Dimension(500, 100));
		acceptAll.setEnabled(false);
		cancel.setEnabled(false);
		rightJButtons.add(acceptAll);
		rightJButtons.add(cancel);
		
		this.add(rightJButtons, BorderLayout.PAGE_END);
	}
	
	private JScrollPane generateProfilePane(ArrayList<PanelPerfil> profilePanes) {
		JPanel profiles = new JPanel();
		profiles.setLayout(new BoxLayout(profiles, BoxLayout.PAGE_AXIS));
		int height = 0;
		for(PanelPerfil pane : profilePanes) {
			height += pane.getHeight();
			profiles.add(pane);
		}
		profiles.setPreferredSize(new Dimension(400, height));
		spOut = new JScrollPane(profiles, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		spOut.setBorder(BorderFactory.createEmptyBorder());
		spOut.setViewportBorder(null);
		profilePane = spOut;
		return spOut;
	}
	
	public void switchViews(boolean prof, ArrayList<PanelPerfil> profilePanes) {
		if(prof) {
			acceptAll.setEnabled(true);
			cancel.setEnabled(true);
			outp.add(generateProfilePane(profilePanes));
			CardLayout cl = (CardLayout)(outp.getLayout());
			cl.last(outp);
		} else {
			acceptAll.setEnabled(false);
			cancel.setEnabled(false);
			CardLayout cl = (CardLayout)(outp.getLayout());
			cl.first(outp);
		}
	}
}
