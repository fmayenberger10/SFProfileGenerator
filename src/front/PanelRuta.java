package front;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelRuta extends JPanel {
	
	private static final long serialVersionUID = 1L;
	public String ruta;
	JButton add;
	
	public PanelRuta(String nombre, JPanel root, String def) {
		ruta = def;
		this.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.setBorder(BorderFactory.createTitledBorder(nombre));
		this.setPreferredSize(new Dimension(500, 120));
		JTextField fRuta = new JTextField(ruta);
		fRuta.setEditable(false);
		fRuta.setPreferredSize(new Dimension(440, 37));
		add = new JButton("Find");
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(root);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					ruta = file.getAbsolutePath();
					if(ruta.contains("\\")) {
						ruta += "\\";
					} else {
						ruta += "/";
					}
					fRuta.setText(ruta);
				}
			}
		});
		add.setPreferredSize(new Dimension(80, 30));
		this.add(fRuta);
		this.add(add);
	}
}