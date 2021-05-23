package de.stylextv.tetris.main;

import de.stylextv.tetris.audio.SoundUtil;
import de.stylextv.tetris.game.Game;
import de.stylextv.tetris.io.Window;
import de.stylextv.tetris.render.GameRenderer;

public class Main {
	
	private static final int FPS = 60;
	
	private static boolean running = true;
	
	private static Window window;
	
	private static Game game;
	
	public static void main(String[] args) {
		try {
			
			SoundUtil.load();
			
			int scale = GameRenderer.SCALE;
			
			window = new Window(GameRenderer.CANVAS_WIDTH * scale, GameRenderer.CANVAS_HEIGHT * scale);
			
			game = new Game();
			
			long lastTick = System.nanoTime();
			
			while(running) {
				
				runTick();
				
				long now = System.nanoTime();
				
				long time = now - lastTick;
				
				long sleep = 1000000000 / FPS - time;
				
				lastTick = now;
				
				if(sleep > 0) Thread.sleep(sleep / 1000000, (int) (sleep % 1000000));
			}
			
			System.exit(0);
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static void runTick() {
		game.handleInputs();
		
		game.update();
		
		window.repaint();
	}
	
	public static void quit() {
		running = false;
	}
	
	public static boolean isRunning() {
		return running;
	}
	
	public static Game getGame() {
		return game;
	}
	
}
