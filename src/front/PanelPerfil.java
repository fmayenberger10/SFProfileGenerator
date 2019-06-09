package front;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import back.Profile;

public class PanelPerfil extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Profile perfil;
	private JButton accept;
	private String rutaNuevos;
	private JTree cambios;
	private GridBagConstraints c;
	
	public PanelPerfil(JPanel root, String rutaNuevos) {
		this.rutaNuevos = rutaNuevos;
		generateLayout(root, rutaNuevos);
	}
	
	public PanelPerfil(Profile perfil, JPanel root, String rutaNuevos) {
		this.rutaNuevos = rutaNuevos;
		c = new GridBagConstraints();
		generateLayout(root, rutaNuevos);
		setPerfil(perfil);
	}
	
	public void setPerfil(Profile perfil) {
	    
		this.perfil = perfil;
    	this.setBorder(	BorderFactory.createCompoundBorder(
    			BorderFactory.createEmptyBorder(10, 10, 10, 10),
    			BorderFactory.createCompoundBorder(
    				BorderFactory.createTitledBorder(perfil.name), 
    				BorderFactory.createEmptyBorder(10, 10, 10, 10))));
		accept.setEnabled(true);
    	accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nme = perfil.name + ".profile";
				perfil.acceptAll();
				boolean genero = perfil.generateFile(rutaNuevos + nme);
			}
		});
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Changes");
		for(String type : perfil.getKeys()) {

			DefaultMutableTreeNode tipo = new DefaultMutableTreeNode(type);
		    top.add(tipo);
		    /*for () {
		    	
		    }*/
		}
	    
	    
	    //original Tutorial
	    /*book = new DefaultMutableTreeNode(new BookInfo
	        ("The Java Tutorial: A Short Course on the Basics",
	        "tutorial.html"));
	    category.add(book);*/
	    cambios = new JTree(top);
	    JScrollPane treeView = new JScrollPane(cambios);
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridx = 0;
	    c.gridy = 0;
	    c.weighty = 2;
	    c.weightx = 2;
		c.gridwidth = 2;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTHWEST;
	    this.add(treeView, c);
	    c.weighty = 0;
	    c.weightx = 0;
	}
	
	private void generateLayout(JPanel root, String rutaNuevos) {
		this.setLayout(new GridBagLayout());
    	this.setBorder(	BorderFactory.createCompoundBorder(
            			BorderFactory.createEmptyBorder(10, 10, 10, 10),
            			BorderFactory.createCompoundBorder(
            				BorderFactory.createTitledBorder(""), 
            				BorderFactory.createEmptyBorder(10, 10, 10, 10))));
    	this.setPreferredSize(new Dimension(400, 200));
    	accept = new JButton("Accept");
    	accept.setEnabled(false);
		accept.setPreferredSize(new Dimension(120, 40));

	    c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.gridwidth = 1;
		c.gridheight = 1;
	    c.weighty = 0;
	    c.weightx = 0;
		c.gridx = 3;
		c.gridy = 3;
		this.add(accept, c);
	}
}
