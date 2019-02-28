import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Window extends JFrame {

	Panel panel;
	public Window()
	{
		panel=new Panel();
		setSize(920,720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(50,50);		
		add(panel);
		setVisible(true);
		setResizable(false);
        
        
	}
	

	
		
	
	
}
