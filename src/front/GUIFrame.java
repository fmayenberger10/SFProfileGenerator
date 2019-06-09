package front;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import back.Profile;
import back.ProfileGeneratorBack;

public class GUIFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Profile> profiles;
	private ArrayList<PanelPerfil> profilePanes;
	public DefaultListModel<String> listaPerfiles;
	public JList<String> perfiles;
	
	private JPanel root;
	private PanelRight rightPane;
	private PanelLeft leftPane;
	
	public GUIFrame() {
		profilePanes = new ArrayList<>();
		listaPerfiles = new DefaultListModel<>();
		perfiles = new JList<>(listaPerfiles);
		listaPerfiles.addElement("Admin");
		listaPerfiles.addElement("Junior GEAMM");
		listaPerfiles.addElement("Pleno GEAMM");
		listaPerfiles.addElement("Gerente GEAMM");
		perfiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		perfiles.setPreferredSize(new Dimension(440, 600));
		this.setTitle("SF Profile Generator");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			this.setIconImage(ImageIO.read(new File("src/images/icon.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//frame.setLocationRelativeTo(null);
		this.setLocation(300,100);
		this.setResizable(false);
		
		paint(true, false);
		setActionListeners();
		this.setVisible(true);
	}
	
	private void paint(boolean leftEnabled, boolean rightEnabled) {
		
		root = new JPanel(new BorderLayout());
		root.setPreferredSize(new Dimension(1300, 800));
		
		leftPane = new PanelLeft(leftEnabled, root, listaPerfiles, perfiles);
		rightPane = new PanelRight(rightEnabled, profilePanes);
		
		root.add(leftPane, BorderLayout.LINE_START);
		root.add(rightPane);
		this.add(root);
		this.pack();
	}
	
	private void setActionListeners() {
		rightPane.acceptAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(profiles == null || profiles.size() == 0) {
					JOptionPane.showMessageDialog(root, "To begin press start", "Error", JOptionPane.NO_OPTION);
				} else {
					for(Profile prof : profiles) {
						String nme = prof.name + ".profile";
						prof.acceptAll();
						boolean genero = prof.generateFile(leftPane.pNuevos.ruta + nme);
					}
					profilePanes = new ArrayList<>();
					paint(true, false);
				}
			}
		});
		rightPane.cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				profiles = new ArrayList<>();
				profilePanes = new ArrayList<>();
				paint(true, false);
			}
		});
		leftPane.add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String s = (String)JOptionPane.showInputDialog(
	                    root,
	                    "Add profile",
	                    "Enter profile name",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    null,
	                    "");
				if ((s != null) && (s.length() > 0)) {
				    listaPerfiles.addElement(s);
				    return;
				}
			}
		});
		leftPane.start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(listaPerfiles.size() == 0) {
					JOptionPane.showMessageDialog(root, "Add a profile", "Error", JOptionPane.ERROR_MESSAGE);
				} else if (leftPane.pOrigen.ruta.isEmpty() || leftPane.pCarga.ruta.isEmpty() || leftPane.pNuevos.ruta.isEmpty()) {
					JOptionPane.showMessageDialog(root, "Make sure input and output folders are set", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					ArrayList<String> perfs = new ArrayList<>();
					for(Object perf : listaPerfiles.toArray()) {
						perfs.add((String)perf);
					}
					try {
						profiles = ProfileGeneratorBack.start(perfs, leftPane.pOrigen.ruta, leftPane.pCarga.ruta);
						profilePanes = new ArrayList<>();
						for(Profile perf : profiles) {
							profilePanes.add(new PanelPerfil(perf, root, leftPane.pNuevos.ruta));
						}
						paint(false, true);
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(root, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		leftPane.remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(listaPerfiles.size() > 0) {
					if(perfiles.getSelectedIndex() > -1) {
						listaPerfiles.remove(perfiles.getSelectedIndex());
					}
				}
			}
		});
	}
}
