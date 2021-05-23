package de.stylextv.tetris.game;

public class Matrix {
	
	private int width;
	
	private int height;
	
	private int[][] blocks;
	
	public Matrix(int width, int height) {
		this.width = width;
		this.height = height;
		
		this.blocks = new int[width][height];
	}
	
	public void clear() {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				blocks[x][y] = BlockType.EMPTY;
			}
		}
	}
	
	public int getBlockType(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) return BlockType.EMPTY;
		
		return blocks[x][y];
	}
	
	public void setBlockType(int x, int y, int type) {
		blocks[x][y] = type;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public static Matrix getMatrix(String[] arr, int start, int width, int height) {
		Matrix m = new Matrix(width, height);
		
		for(int y = 0; y < height; y++) {
			
			String s = arr[start + y];
			
			for(int x = 0; x < width; x++) {
				if(s.charAt(x * 2) == 'X') {
					m.setBlockType(x, y, BlockType.FULL);
				}
			}
		}
		
		return m;
	}
	
}
