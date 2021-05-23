package de.stylextv.tetris.render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.stylextv.tetris.game.BlockType;
import de.stylextv.tetris.game.Game;
import de.stylextv.tetris.main.Main;

public class GameRenderer {
	
	public static final int SCALE = 4;
	
	public static final int BLOCK_SIZE = 7;
	
	public static final int CANVAS_WIDTH = (BLOCK_SIZE + 1) * Game.WIDTH + 1;
	public static final int CANVAS_HEIGHT = (BLOCK_SIZE + 1) * Game.HEIGHT + 1;
	
	private static BufferedImage canvas = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	private static Graphics canvasGraphics = canvas.getGraphics();
	
	public static void drawFrame(Graphics graphics) {
		Game game = Main.getGame();
		
		ColorPalette palette = ColorPalette.getPalette(game.getLevel());
		
		canvasGraphics.setColor(game.isFlashing() ? ColorPalette.WHITE : ColorPalette.BLACK);
		
		canvasGraphics.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		
		for(int x = 0; x < Game.WIDTH; x++) {
			for(int y = 0; y < Game.HEIGHT; y++) {
				
				int type = game.getBlockType(x, y);
				
				if(type != BlockType.EMPTY) {
					Color c = BlockType.isPrimaryColored(type) ? palette.getPrimaryColor() : palette.getSecondaryColor();
					
					boolean full = BlockType.isFull(type);
					
					int renderX = x * (BLOCK_SIZE + 1) + 1;
					int renderY = y * (BLOCK_SIZE + 1) + 1;
					
					int i = x == 0 ? 1 : 0;
					int j = y == 0 ? 1 : 0;
					
					canvasGraphics.setColor(ColorPalette.BLACK);
					
					canvasGraphics.fillRect(renderX - i, renderY - j, BLOCK_SIZE + 1 + i, BLOCK_SIZE + 1 + j);
					
					canvasGraphics.setColor(c);
					
					canvasGraphics.fillRect(renderX, renderY, BLOCK_SIZE, BLOCK_SIZE);
					
					canvas.setRGB(renderX, renderY, ColorPalette.WHITE.getRGB());
					
					canvasGraphics.setColor(ColorPalette.WHITE);
					
					if(full) {
						canvasGraphics.fillRect(renderX + 1, renderY + 1, 2, 2);
						
						canvas.setRGB(renderX + 2, renderY + 2, c.getRGB());
					} else {
						canvasGraphics.fillRect(renderX + 1, renderY + 1, BLOCK_SIZE - 2, BLOCK_SIZE - 2);
					}
				}
			}
		}
		
		int h = game.getDeathScreenState();
		
		int barWidth = CANVAS_WIDTH - 2;
		
		for(int y = 0; y < h; y++) {
			
			int renderX = 1;
			int renderY = y * (BLOCK_SIZE + 1) + 1;
			
			canvasGraphics.setColor(palette.getSecondaryColor());
			canvasGraphics.fillRect(renderX, renderY, barWidth, 2);
			
			canvasGraphics.setColor(ColorPalette.WHITE);
			canvasGraphics.fillRect(renderX, renderY + 2, barWidth, 3);
			
			canvasGraphics.setColor(palette.getPrimaryColor());
			canvasGraphics.fillRect(renderX, renderY + 5, barWidth, 2);
		}
		
		graphics.drawImage(canvas, 0, 0, CANVAS_WIDTH * SCALE, CANVAS_HEIGHT * SCALE, null);
	}
	
}
