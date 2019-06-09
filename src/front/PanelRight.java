package front;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PanelRight extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel outp;
	private JPanel rightJButtons;
	public JButton cancel;
	public JButton acceptAll;
	private JScrollPane spOut;
	
	public PanelRight (boolean enabled, ArrayList<PanelPerfil> profilePanes) {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder("Output"), 
					BorderFactory.createEmptyBorder(10, 10, 10, 10))));

		acceptAll = new JButton("Accept all");
		cancel = new JButton("Cancel");
		if(enabled) {
			//acceptAll.setEnabled(true);
			//cancel.setEnabled(true);
			
			outp = new JPanel();
			outp.setLayout(new BoxLayout(outp, BoxLayout.PAGE_AXIS));
			outp.setPreferredSize(new Dimension(400,650));
			for(PanelPerfil pane : profilePanes) {
				outp.add(pane);
			}
			spOut = new JScrollPane(outp, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			spOut.setBorder(null);
			this.add(spOut);
		} else {
			//acceptAll.setEnabled(false);
			//cancel.setEnabled(false);
		}

		acceptAll.setPreferredSize(new Dimension(100, 40));
		cancel.setPreferredSize(new Dimension(100, 40));
		
		rightJButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		rightJButtons.setMaximumSize(new Dimension(500, 100));
		rightJButtons.add(acceptAll);
		rightJButtons.add(cancel);
		
		this.add(rightJButtons, BorderLayout.PAGE_END);
	}
}
