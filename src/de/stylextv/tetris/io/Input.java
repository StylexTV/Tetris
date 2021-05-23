package de.stylextv.tetris.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {
	
	public static final int KEY_LEFT = KeyEvent.VK_A;
	public static final int KEY_RIGHT = KeyEvent.VK_D;
	public static final int KEY_DOWN = KeyEvent.VK_SPACE;
	
	public static final int KEY_ROTATE_LEFT = KeyEvent.VK_W;
	public static final int KEY_ROTATE_RIGHT = KeyEvent.VK_S;
	
	private static boolean[] keyStates = new boolean[KeyEvent.KEY_LAST + 1];
	
	private static boolean[] usedStates = new boolean[KeyEvent.KEY_LAST + 1];
	
	private static long[] lastUse = new long[KeyEvent.KEY_LAST + 1];
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code < keyStates.length && !keyStates[code]) {
			keyStates[code] = true;
			
			usedStates[code] = true;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code < keyStates.length) keyStates[code] = false;
	}
	
	public static boolean isKeyPressed(int code) {
		return isKeyPressed(code, 0);
	}
	
	public static boolean isKeyPressed(int code, int cooldown) {
		if(keyStates[code]) {
			if(cooldown == 0) {
				boolean b = usedStates[code];
				
				usedStates[code] = false;
				
				return b;
			}
			
			long now = System.nanoTime();
			
			long time = lastUse[code];
			
			if(now - time >= cooldown) {
				lastUse[code] = now;
				
				return true;
			}
		}
		
		return false;
	}
	
}
