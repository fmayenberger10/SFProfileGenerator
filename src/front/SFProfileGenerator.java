package front;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import back.Profile;
import back.ProfileGeneratorBack;

public class SFProfileGenerator {
	
	private static ArrayList<Profile> profiles;
	private static ArrayList<PanelPerfil> profilePanes;
	private static DefaultListModel<String> listaPerfiles;
	private static JList<String> perfiles;
	
	private static JFrame frame;
	private static JPanel root;
	private static JPanel right;
	private static JPanel outp;
	private static JPanel rightButtons;
	private static Button cancel;
	private static Button acceptAll;
	private static JScrollPane spOut;
	
	private static JPanel left;
	private static JPanel leftButtons;
	private static JScrollPane sp;
	private static PanelRuta pOrigen;
	private static PanelRuta pCarga;
	private static PanelRuta pNuevos;
	private static Button remove;
	private static Button add;
	private static Button start;
	
	public static void main(String args[]) { 
		GUIFrame frame = new GUIFrame();
		/*frame = new JFrame("SF Profile Generator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			frame.setIconImage(ImageIO.read(new File("src/images/icon.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//frame.setLocationRelativeTo(null);
		frame.setLocation(300,100);
		frame.setResizable(false);

		
		remove = new Button("Remove selected");
		add = new Button("Add profile");
		start = new Button("Start");
		acceptAll = new Button("Accept all");
		cancel = new Button("Cancel");
		
		paint(true, false);
		setActionListeners();*/
	}
	
	private static void paint(boolean leftEnabled, boolean rightEnabled) {
		
		if(leftEnabled) {
			add.setEnabled(true);
			remove.setEnabled(true);
		} else {
			add.setEnabled(false);
			remove.setEnabled(false);
		}
		if(rightEnabled) {
			acceptAll.setEnabled(true);
			cancel.setEnabled(true);
		} else {
			acceptAll.setEnabled(false);
			cancel.setEnabled(false);
		}

		JPanel left = paintLeft(leftEnabled);
		JPanel right = paintRight(rightEnabled);
		
		root = new JPanel(new BorderLayout());
		root.setPreferredSize(new Dimension(1300, 800));
		root.add(left, BorderLayout.LINE_START);
		root.add(right);
		
		frame.add(root);
		frame.pack();
		frame.setVisible(true);
	}
	
	private static JPanel paintLeft(boolean enabled) {

		left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		left.setPreferredSize(new Dimension(500, 800));
		left.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		pOrigen = new PanelRuta("Origin folder", root, "/home/franz/Documents/Sulamerica/salesforce-ic/profiles/");
		pCarga = new PanelRuta("Upload folder", root, "/home/franz/Documents/Sulamerica/profiles/profiles commit 1 vfin/");
		pNuevos = new PanelRuta("Generated folder", root, "/home/franz/Documents/Sulamerica/profiles/nuevos/");
		left.add(pOrigen);
		left.add(pCarga);
		left.add(pNuevos);
		
		listaPerfiles = new DefaultListModel<>();
		listaPerfiles.addElement("Admin");
		listaPerfiles.addElement("Junior GEAMM");
		listaPerfiles.addElement("Pleno GEAMM");
		listaPerfiles.addElement("Gerente GEAMM");
		//listaPerfiles.addElement("perfil 2");
		perfiles = new JList<>(listaPerfiles);
		perfiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		perfiles.setPreferredSize(new Dimension(440, 600));
		sp = new JScrollPane(perfiles, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(460, 450));
		sp.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Profiles"), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		
		left.add(sp);
		
		leftButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		leftButtons.setMaximumSize(new Dimension(500, 100));
		add.setPreferredSize(new Dimension(100, 40));
		start.setPreferredSize(new Dimension(100, 40));
		remove.setPreferredSize(new Dimension(150, 40));
		leftButtons.add(add);
		leftButtons.add(remove);
		leftButtons.add(start);
		left.add(leftButtons);
		perfiles.setPreferredSize(new Dimension(450, 400));
		return left;
	}
	
	private static JPanel paintRight(boolean enabled) {
		
		right = new JPanel(new BorderLayout());
		right.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder("Output"), 
					BorderFactory.createEmptyBorder(10, 10, 10, 10))));

		acceptAll.setPreferredSize(new Dimension(100, 40));

		cancel.setPreferredSize(new Dimension(100, 40));
		
		if(enabled) {
			outp = new JPanel();
			outp.setLayout(new BoxLayout(outp, BoxLayout.PAGE_AXIS));
			outp.setPreferredSize(new Dimension(400,650));
			for(PanelPerfil pane : profilePanes) {
				outp.add(pane);
			}
			spOut = new JScrollPane(outp, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			spOut.setBorder(null);
			right.add(spOut);
		}
		
		rightButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		rightButtons.setMaximumSize(new Dimension(500, 100));
		
		rightButtons.add(acceptAll);
		rightButtons.add(cancel);
		
		right.add(rightButtons, BorderLayout.PAGE_END);
		return right;
	}
	
	private static void setActionListeners() {
		acceptAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(Profile prof : profiles) {
					String nme = prof.name + ".profile";
					prof.acceptAll();
					boolean genero = prof.generateFile(pNuevos.ruta + nme);
				}
				paint(true, false);
			}
		});
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				profiles = new ArrayList<>();
				paint(true, false);
			}
		});
		add.addActionListener(new ActionListener() {
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
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(listaPerfiles.size() == 0) {
					JOptionPane.showMessageDialog(root, "Add a profile", "Error", JOptionPane.ERROR_MESSAGE);
				} else if (pOrigen.ruta.isEmpty() || pCarga.ruta.isEmpty() || pNuevos.ruta.isEmpty()) {
					JOptionPane.showMessageDialog(root, "Make sure input and output folders are set", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					ArrayList<String> perfs = new ArrayList<>();
					for(Object perf : listaPerfiles.toArray()) {
						perfs.add((String)perf);
					}
					try {
						profiles = ProfileGeneratorBack.start(perfs, pOrigen.ruta, pCarga.ruta);
						profilePanes = new ArrayList<>();
						for(Profile perf : profiles) {
							profilePanes.add(new PanelPerfil(perf, root, pNuevos.ruta));
						}
						paint(false, true);
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(root, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		remove.addActionListener(new ActionListener() {
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
	
	private static String generateOutput() {
		String ret = "";
		for(Profile prof : profiles) {
			ret += prof.name + "\n";
			/*ArrayList<String> keys = prof.getKeys();
			for(String key : keys) {
				ret += "\t" + key + "\n";
			}*/
			ArrayList<String> cambios = prof.getChanges();
			if(cambios.size() > 0) {
				ret += "\ncambios:\n__________\n\n";
				for(String cambio : cambios) {
					ret += cambio + "\n";
				}
			}
			ArrayList<String> inserts = prof.getInserts();
			if(inserts.size() > 0) {
				ret += "\ninserciones:\n__________\n\n";
				for(String insert : inserts) {
					ret += insert + "\n";
				}
			}
			ret += "____________________________________________________________________________________\n\n";
		}
		return ret;
	}
}
