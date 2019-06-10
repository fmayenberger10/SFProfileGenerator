package front;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class PanelLeft extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel leftJButtons;
	private JScrollPane sp;
	public PanelRuta pOrigen;
	public PanelRuta pCarga;
	public PanelRuta pNuevos;
	public JButton remove;
	public JButton add;
	public JButton start;
	
	public PanelLeft (boolean enabled, JPanel root, DefaultListModel<String> listaPerfiles, JList<String> perfiles) {

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(500, 800));
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		pOrigen = new PanelRuta("Origin folder", root, "/home/franz/Documents/Sulamerica/salesforce-ic/profiles/");
		pCarga = new PanelRuta("Upload folder", root, "/home/franz/Documents/Sulamerica/profiles/profiles commit 1 vfin/");
		pNuevos = new PanelRuta("Generated folder", root, "/home/franz/Documents/Sulamerica/profiles/nuevos/");
		this.add(pOrigen);
		this.add(pCarga);
		this.add(pNuevos);
		
		sp = new JScrollPane(perfiles, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(460, 450));
		sp.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Profiles"), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		//sp.setBackground(new Color(150,150,150));
		
		leftJButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		leftJButtons.setMaximumSize(new Dimension(500, 100));
		
		remove = new JButton("Remove selected");
		add = new JButton("Add profile");
		start = new JButton("Start");
		add.setPreferredSize(new Dimension(120, 40));
		start.setPreferredSize(new Dimension(100, 40));
		remove.setPreferredSize(new Dimension(150, 40));
		
		/*if(enabled) {
			add.setEnabled(true);
			remove.setEnabled(true);
		} else {
			add.setEnabled(false);
			remove.setEnabled(false);
		}*/

		leftJButtons.add(add);
		leftJButtons.add(remove);
		leftJButtons.add(start);
		
		this.add(sp);
		
		this.add(leftJButtons);
		perfiles.setPreferredSize(new Dimension(450, 400));
	}
}
