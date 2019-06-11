package front;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PanelLeft extends JPanel {
	
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
		this.setPreferredSize(new Dimension((int)(500*GUIFrame.SCALE), (int)(800*GUIFrame.SCALE)));
		this.setBorder(BorderFactory.createEmptyBorder((int)(20*GUIFrame.SCALE), (int)(20*GUIFrame.SCALE), (int)(20*GUIFrame.SCALE), (int)(20*GUIFrame.SCALE)));

		/*pOrigen = new PanelRuta("Origin folder", root, "/home/franz/Documents/Sulamerica/salesforce-ic/profiles/");
		pCarga = new PanelRuta("Upload folder", root, "/home/franz/Documents/Sulamerica/profiles/profiles commit 1 vfin/");
		pNuevos = new PanelRuta("Generated folder", root, "/home/franz/Documents/Sulamerica/profiles/nuevos/");*/
		pOrigen = new PanelRuta("Origin folder", root, "");
		pCarga = new PanelRuta("Upload folder", root, "");
		pNuevos = new PanelRuta("Generated folder", root, "");
		this.add(pOrigen);
		this.add(pCarga);
		this.add(pNuevos);
		
		sp = new JScrollPane(perfiles, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension((int)(460*GUIFrame.SCALE), (int)(450*GUIFrame.SCALE)));
		sp.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Profiles"), 
				BorderFactory.createEmptyBorder((int)(10*GUIFrame.SCALE), (int)(10*GUIFrame.SCALE), (int)(10*GUIFrame.SCALE), (int)(10*GUIFrame.SCALE))));

		leftJButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		leftJButtons.setMaximumSize(new Dimension((int)(500*GUIFrame.SCALE), (int)(100*GUIFrame.SCALE)));
		
		remove = new JButton("Remove selected");
		add = new JButton("Add profile");
		start = new JButton("Start");
		add.setPreferredSize(new Dimension((int)(120*GUIFrame.SCALE), (int)(40*GUIFrame.SCALE)));
		start.setPreferredSize(new Dimension((int)(100*GUIFrame.SCALE), (int)(40*GUIFrame.SCALE)));
		remove.setPreferredSize(new Dimension((int)(150*GUIFrame.SCALE), (int)(40*GUIFrame.SCALE)));

		leftJButtons.add(add);
		leftJButtons.add(remove);
		leftJButtons.add(start);
		
		this.add(sp);
		
		this.add(leftJButtons);
		perfiles.setPreferredSize(new Dimension((int)(450*GUIFrame.SCALE), (int)(400*GUIFrame.SCALE)));
	}
}
