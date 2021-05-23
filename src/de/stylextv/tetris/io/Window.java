package de.stylextv.tetris.io;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import de.stylextv.tetris.main.Constants;
import de.stylextv.tetris.main.Main;
import de.stylextv.tetris.render.Renderer;

public class Window {
	
	private JFrame frame;
	
	private Renderer renderer;
	
	public Window(int width, int height) {
		frame = new JFrame(Constants.NAME);
		
		frame.setSize(width + 16, height + 39);
		
		centerOnScreen();
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		frame.addWindowListener(new WindowAdapter() {
			
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	Main.quit();
		    }
		});
		
		frame.addKeyListener(new Input());
		
		frame.add(renderer = new Renderer());
		
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public void centerOnScreen() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - 40 - frame.getHeight()) / 2);
	}
	
	public void repaint() {
		renderer.repaint();
	}
	
}
