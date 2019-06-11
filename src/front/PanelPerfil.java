package front;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.JLabel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import back.Profile;

public class PanelPerfil extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Profile perfil;
	private JButton accept;
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
    			BorderFactory.createEmptyBorder((int)(10*GUIFrame.SCALE), (int)(10*GUIFrame.SCALE), (int)(10*GUIFrame.SCALE), (int)(10*GUIFrame.SCALE)),
    			BorderFactory.createCompoundBorder(
    				BorderFactory.createTitledBorder(perfil.name), 
    				BorderFactory.createEmptyBorder((int)(10*GUIFrame.SCALE), (int)(10*GUIFrame.SCALE), (int)(10*GUIFrame.SCALE), (int)(10*GUIFrame.SCALE)))));
    	
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
    	//detail.setMaximumSize(new Dimension(100,400));
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

	            Object nodeInfo = node.getUserObject();
	            updateDetail(nodeInfo.toString(), node.getParent().toString());
	            System.out.println(nodeInfo);
	            revalidate();
	            
	        }
	    });
	    JScrollPane treeView = new JScrollPane(cambios);
	    minSize = (int)(((cambios.getRowCount() * 25)+50)*GUIFrame.SCALE);
	    treeView.setMaximumSize(new Dimension((int)(600*GUIFrame.SCALE), (int)(((cambios.getRowCount() * 25)+50)*GUIFrame.SCALE)));
	    if(minSize < 200) minSize = 200;
	    if(cambios.getRowCount() < 4) minSize = 110;
	    this.setMinimumSize(new Dimension((int)(100*GUIFrame.SCALE), (int)(minSize*GUIFrame.SCALE)));
    	this.setMaximumSize(new Dimension((int)(750*GUIFrame.SCALE), (int)(minSize*GUIFrame.SCALE)));
	    c.gridx = 0;
	    c.gridy = 0;
	    c.weighty = 1;
	    c.weightx = 1.5;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTHWEST;
	    this.add(treeView, c);
	    
	    c.gridx = 1;
	    c.gridy = 0;
	    c.weighty = 0;
	    c.weightx = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		//detail.setBackground(new Color(123));
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
	
	private void updateDetail(String selected, String parent) {
		if(selected.equals("Changes") || selected.equals("Insertions")) {
			detail.removeAll();
		} else if (perfil.getKeys().contains(selected)) {
			System.out.println(perfil.getType(selected, Profile.OLD));
		} else {
			detail.removeAll();
			LinkedHashMap<String, String> oldVals = perfil.getTag(parent, selected, Profile.OLD);
			LinkedHashMap<String, String> newVals = perfil.getTag(parent, selected, Profile.NEW);
			for(String tag: newVals.keySet()) {
				if(!newVals.get(tag).equals(selected)) {
					boolean isInsert = oldVals == null ? true : false;
					JPanel tempPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
					JTextField oldVal = new JTextField();
					if(!isInsert) {
						oldVal.setText(oldVals.get(tag));
				    	oldVal.setEditable(false);
				    	oldVal.setPreferredSize(new Dimension((int)(120*GUIFrame.SCALE),(int)(30*GUIFrame.SCALE)));
					}
			    	JTextField newVal = new JTextField(newVals.get(tag));
			    	newVal.setEditable(false);
			    	newVal.setPreferredSize(new Dimension(120,30));
			    	JLabel name = new JLabel(tag);
			    	tempPane.add(name);
					if(!isInsert) {
				    	tempPane.add(oldVal);
						tempPane.add(new JLabel("->"));
					} else {
						newVal.setPreferredSize(new Dimension((int)(230*GUIFrame.SCALE),(int)(30*GUIFrame.SCALE)));
					}
			    	tempPane.add(newVal);
			    	detail.add(tempPane);
				}
			}
			System.out.println(perfil.getTag(parent, selected, Profile.OLD));
		}
	}
}
