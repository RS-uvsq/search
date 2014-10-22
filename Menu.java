package graphisme;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menu extends JMenuBar{
	
	
	  private JMenu fichierMenu;
	  private JMenu aideMenu;
	  private JMenuItem newSource;
	  private JMenuItem source;
	  private JMenuItem sources;
	  
	
	private static final long serialVersionUID = 1L;
	
	public Menu(){
		
		setBackground(Color.white);
		
		aideMenu = new JMenu("aide");
		aideMenu.setBackground(Color.black);
		fichierMenu = new JMenu("fichier");
		fichierMenu.setBackground(Color.white);
		aideMenu.setPreferredSize(new Dimension(50,10));
		fichierMenu.setForeground(Color.black);;
		newSource = new JMenuItem("nouvelle source");
		source = new JMenuItem("choisir source");
		sources = new JMenuItem("toutes les sources");
		
				
		
		
		fichierMenu.add(newSource);
		fichierMenu.add(source);
		fichierMenu.add(sources);
		
		add(fichierMenu);
		add(aideMenu);
			
		
	}


	
	public void LoadStrategy(){
		
	}




	public JMenu getFichierMenu() {
		return fichierMenu;
	}

	public void setFichierMenu(JMenu fichierMenu) {
		this.fichierMenu = fichierMenu;
	}

	public JMenu getAideMenu() {
		return aideMenu;
	}

	public void setAideMenu(JMenu aideMenu) {
		this.aideMenu = aideMenu;
	}

	public JMenuItem getNewSource() {
		return newSource;
	}

	public void setNewSource(JMenuItem newStrategyItem) {
		this.newSource = newStrategyItem;
	}



	public void setSource(JMenuItem source) {
		this.source = source;
	}



	public JMenuItem getSource() {
		return source;
	}



	public void setSources(JMenuItem sources) {
		this.sources = sources;
	}



	public JMenuItem getSources() {
		return sources;
	}
	
	
}
