package front;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import back.Profile;

public class PanelPerfil extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Profile perfil;
	private JButton accept;
	private String rutaNuevos;
	
	public PanelPerfil(JPanel root, String rutaNuevos) {
		this.rutaNuevos = rutaNuevos;
		generateLayout(root, rutaNuevos);
	}
	
	public PanelPerfil(Profile perfil, JPanel root, String rutaNuevos) {
		this.rutaNuevos = rutaNuevos;
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
	}
	
	private void generateLayout(JPanel root, String rutaNuevos) {
		this.setLayout(new FlowLayout(FlowLayout.RIGHT));
    	this.setBorder(	BorderFactory.createCompoundBorder(
            			BorderFactory.createEmptyBorder(10, 10, 10, 10),
            			BorderFactory.createCompoundBorder(
            				BorderFactory.createTitledBorder(""), 
            				BorderFactory.createEmptyBorder(10, 10, 10, 10))));
    	this.setPreferredSize(new Dimension(600, 400));
    	accept = new JButton("Accept");
    	accept.setEnabled(false);
		accept.setPreferredSize(new Dimension(120, 40));
		this.add(accept);
	}
}
