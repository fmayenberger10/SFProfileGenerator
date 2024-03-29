package front;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import back.Profile;

public class PanelPerfil extends JPanel {

	private static final long serialVersionUID = 1L;
	
	Profile perfil;
	JButton accept;
	private String rutaNuevos;
	private JTree cambios;
	private GridBagConstraints c;
	JPanel detail;
	int minSize;
	
	public PanelPerfil(Profile perfil, JPanel root, String rutaNuevos) {
		this.rutaNuevos = rutaNuevos;
		c = new GridBagConstraints();
		setPerfil(perfil);
	}
	
	public void setPerfil(Profile perfil) {

		this.setLayout(new GridBagLayout());
		this.perfil = perfil;
    	this.setBorder(	BorderFactory.createCompoundBorder(
    			BorderFactory.createEmptyBorder((int)(10*GUIFrame.SCALE), (int)(10*GUIFrame.SCALE), (int)(10*GUIFrame.SCALE), (int)(5*GUIFrame.SCALE)),
    			BorderFactory.createCompoundBorder(
    				BorderFactory.createTitledBorder(perfil.name), 
    				BorderFactory.createEmptyBorder((int)(5*GUIFrame.SCALE), (int)(5*GUIFrame.SCALE), (int)(5*GUIFrame.SCALE), (int)(5*GUIFrame.SCALE)))));
    	
    	accept = new JButton("Create");
		accept.setMinimumSize(new Dimension((int)(100*GUIFrame.SCALE), (int)(40*GUIFrame.SCALE)));
		accept.setEnabled(true);
    	accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nme = perfil.name + ".profile";
				perfil.acceptAll();
				boolean genero = perfil.generateFile(rutaNuevos + nme);
				if(genero) {
					accept.setText("Success!");
					accept.setEnabled(false);
				}
			}
		});
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Changes");
		DefaultMutableTreeNode changes = new DefaultMutableTreeNode("Changes");
		LinkedHashMap<String, ArrayList<String>> changeList = perfil.getChanges();
		for(String type : changeList.keySet()) {
			DefaultMutableTreeNode tipo = new DefaultMutableTreeNode(type);
			changes.add(tipo);
		    for (String nombre : changeList.get(type)) {
		    	DefaultMutableTreeNode cambio = new DefaultMutableTreeNode(nombre);
		    	tipo.add(cambio);
		    }
		}
		DefaultMutableTreeNode insertions = new DefaultMutableTreeNode("Insertions");
		LinkedHashMap<String, ArrayList<String>> insertList = perfil.getInserts();
		for(String type : insertList.keySet()) {
			DefaultMutableTreeNode tipo = new DefaultMutableTreeNode(type);
			insertions.add(tipo);
		    for (String nombre : insertList.get(type)) {
		    	DefaultMutableTreeNode cambio = new DefaultMutableTreeNode(nombre);
		    	tipo.add(cambio);
		    }
		}
		top.add(changes);
		top.add(insertions);

    	detail = new JPanel();
    	detail.setLayout(new BoxLayout(detail, BoxLayout.PAGE_AXIS));
	    cambios = new JTree(top);
	    for (int i = 0; i < cambios.getRowCount(); i++) {
	    	cambios.expandRow(i);
	    }
	    cambios.setRootVisible(false);
	    cambios.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	    cambios.addTreeSelectionListener(new TreeSelectionListener() {
	        public void valueChanged(TreeSelectionEvent e) {
	            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
	            		cambios.getLastSelectedPathComponent();

	            if (node == null) return;
	            
	            updateDetail(node);
	            revalidate();
	            
	        }
	    });
	    JScrollPane treeView = new JScrollPane(cambios);
	    treeView.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.WHITE, new Color(200,200,200)));
	    minSize = (int)(((cambios.getRowCount() * 20)+90)*GUIFrame.SCALE);
	    if(minSize < 150) minSize = 150;
	    if(cambios.getRowCount() < 4) minSize = 110;
	    if(minSize > 700*GUIFrame.SCALE) minSize = (int)(700*GUIFrame.SCALE);
	    this.setMinimumSize(new Dimension((int)(100*GUIFrame.SCALE), (int)(minSize*GUIFrame.SCALE)));
    	this.setMaximumSize(new Dimension((int)(800*GUIFrame.SCALE), (int)(minSize*GUIFrame.SCALE)));
	    c.gridx = 0;
	    c.gridy = 0;
	    c.weighty = 1;
	    c.weightx = 1;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTHWEST;
	    this.add(treeView, c);
	    
	    c.gridx = 1;
	    c.gridy = 0;
	    c.weighty = 1;
	    c.weightx = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
	    this.add(detail, c);

		c.gridx = 1;
		c.gridy = 1;
	    c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.gridwidth = 1;
		c.gridheight = 1;
	    c.weighty = 0;
	    c.weightx = 1;
		this.add(new JPanel(), c);

		c.gridx = 2;
		c.gridy = 1;
	    c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.gridwidth = 1;
		c.gridheight = 1;
	    c.weighty = 0;
	    c.weightx = 0;
		this.add(accept, c);
	}
	
	@Override
	public int getHeight() {
		return minSize;
	}
	
	private void updateDetail(DefaultMutableTreeNode node) {
		String selected = node.getUserObject().toString();
		String parent = node.getParent().toString();
		if(selected.equals("Changes") || selected.equals("Insertions")) {
			detail.removeAll();
			JScrollPane sp = new JScrollPane(new JPanel());
			detail.add(sp);
		} else if (perfil.getKeys().contains(selected)) {
			int num = 0;
			detail.removeAll();
			LinkedHashMap<String, LinkedHashMap<String,String>> oldMap = perfil.getType(selected, Profile.OLD);
			LinkedHashMap<String, LinkedHashMap<String,String>> newMap = perfil.getType(selected, Profile.NEW);
			boolean isInsert = node.getParent().toString().equals("Insertions") ? true : false;
			JPanel tempPane = new JPanel();
			tempPane.setLayout(new GridLayout(0,1));
			tempPane.setBorder(BorderFactory.createEmptyBorder(0, (int)(5*GUIFrame.SCALE), 0, 0));
			for(int i = 0; i < node.getChildCount(); i++) {
				JLabel nombre = new JLabel();
				if(node.toString().equals("layoutAssignments")) {
					String[] partes = node.getChildAt(i).toString().split("\\|");
					JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
					JPanel separatorPane = new JPanel();
					separatorPane.setLayout(new BoxLayout(separatorPane, BoxLayout.PAGE_AXIS));
					separatorPane.add(Box.createVerticalStrut(25));
					separatorPane.add(separator);
					tempPane.add(separatorPane);
					if(partes.length == 4 && !(partes[3].isEmpty() || partes[3].equals("null"))) {
						tempPane.add(new JLabel(trimLabel((int)(400*GUIFrame.SCALE), partes[1])));
						num++;
						nombre.setText(trimLabel((int)(400*GUIFrame.SCALE),partes[3]));
					} else {
						nombre.setText(trimLabel((int)(400*GUIFrame.SCALE),partes[1]));
					}
				} else {
					nombre.setText(trimLabel((int)(390*GUIFrame.SCALE), node.getChildAt(i).toString()));
				}
				tempPane.add(nombre);
				num++;
				if(isInsert) {
					for(String name : newMap.get(node.getChildAt(i).toString()).keySet()) {
						JPanel vals = new JPanel(new FlowLayout(FlowLayout.RIGHT));
						if(!newMap.get(node.getChildAt(i).toString()).get(name).equals(node.getChildAt(i).toString())) {
							JLabel tag = new JLabel(name);
							JTextField newVal = new JTextField(newMap.get(node.getChildAt(i).toString()).get(name));
					    	newVal.setEditable(false);
							newVal.setPreferredSize(new Dimension((int)(280*GUIFrame.SCALE),(int)(30*GUIFrame.SCALE)));
							vals.add(tag);
							vals.add(newVal);
							tempPane.add(vals);
							num++;
						}
					}
				} else {
					for(String name : newMap.get(node.getChildAt(i).toString()).keySet()) {
						JPanel vals = new JPanel(new FlowLayout(FlowLayout.RIGHT));
						if(!newMap.get(node.getChildAt(i).toString()).get(name).equals(node.getChildAt(i).toString())) {
							JLabel tag = new JLabel(name);
							JTextField oldVal = new JTextField(oldMap.get(node.getChildAt(i).toString()).get(name));
					    	oldVal.setEditable(false);
					    	oldVal.setPreferredSize(new Dimension((int)(120*GUIFrame.SCALE),(int)(30*GUIFrame.SCALE)));
							JTextField newVal = new JTextField(newMap.get(node.getChildAt(i).toString()).get(name));
					    	newVal.setEditable(false);
					    	newVal.setPreferredSize(new Dimension((int)(120*GUIFrame.SCALE),(int)(30*GUIFrame.SCALE)));
							vals.add(tag);
							vals.add(oldVal);
							vals.add(new JLabel("->"));
							vals.add(newVal);
							tempPane.add(vals);
							num++;
						}
					}
				}
			}
			JScrollPane sp = new JScrollPane(tempPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			detail.add(sp);
			if(num*40*GUIFrame.SCALE < minSize-100) {
				detail.add(Box.createVerticalStrut((int)(minSize-100-(num*40*GUIFrame.SCALE))));
			}
		} else {
			int num = 0;
			detail.removeAll();
			LinkedHashMap<String, String> oldVals = perfil.getTag(parent, selected, Profile.OLD);
			LinkedHashMap<String, String> newVals = perfil.getTag(parent, selected, Profile.NEW);
			JPanel tempPane = new JPanel();
			tempPane.setLayout(new GridLayout(0,1));
			tempPane.setBorder(BorderFactory.createEmptyBorder((int)(10*GUIFrame.SCALE), (int)(5*GUIFrame.SCALE), 0, 0));
			for(String tag: newVals.keySet()) {
				if(!newVals.get(tag).equals(selected)) {
					boolean isInsert = oldVals == null ? true : false;
					JPanel vals = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			    	JLabel name = new JLabel(tag);
			    	vals.add(name);
					if(isInsert) {
				    	JTextField newVal = new JTextField(newVals.get(tag));
				    	newVal.setEditable(false);
						newVal.setPreferredSize(new Dimension((int)(280*GUIFrame.SCALE),(int)(30*GUIFrame.SCALE)));
				    	vals.add(newVal);
					} else {
						JTextField oldVal = new JTextField(oldVals.get(tag));
				    	oldVal.setEditable(false);
				    	oldVal.setPreferredSize(new Dimension((int)(130*GUIFrame.SCALE),(int)(30*GUIFrame.SCALE)));
				    	JTextField newVal = new JTextField(newVals.get(tag));
				    	newVal.setEditable(false);
				    	newVal.setPreferredSize(new Dimension((int)(130*GUIFrame.SCALE),(int)(30*GUIFrame.SCALE)));
				    	vals.add(oldVal);
						vals.add(new JLabel("->"));
				    	vals.add(newVal);
					}
			    	tempPane.add(vals);
			    	num++;
				}
			}
			JScrollPane sp = new JScrollPane(tempPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			detail.add(sp);
			if(num*48*GUIFrame.SCALE < minSize-100) {
				detail.add(Box.createVerticalStrut((int)(minSize-100-(num*48*GUIFrame.SCALE))));
			}
		}
	}
	
	private String trimLabel(int length, String str) {
		Graphics g = this.getGraphics();
		boolean cortado = false;
		int width = g.getFontMetrics().stringWidth(str);
		while(width > length - g.getFontMetrics().stringWidth("[...]")) {
			cortado = true;
			str = str.substring(0, str.length()-1);
			width = g.getFontMetrics().stringWidth(str);
		} 
		if(cortado) {
			str += "[...]";
		}
		return str;
	}
}
