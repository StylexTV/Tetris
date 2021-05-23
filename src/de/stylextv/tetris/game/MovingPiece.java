package de.stylextv.tetris.game;

public class MovingPiece {
	
	private Piece piece;
	
	private int rotation;
	
	private int blockType;
	
	private int x;
	
	private int y;
	
	public MovingPiece(Piece p, int blockType, int x, int y) {
		this.piece = p;
		this.blockType = blockType;
		
		this.x = x;
		this.y = y;
	}
	
	public void putIntoBounds() {
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = 0;
		int maxY = 0;
		
		for(int x = 0; x < 5; x++) {
			for(int y = 0; y < 5; y++) {
				
				if(getMatrix().getBlockType(x, y) != BlockType.EMPTY) {
					
					if(x < minX) minX = x;
					if(x > maxX) maxX = x;
					if(y < minY) minY = y;
					if(y > maxY) maxY = y;
				}
			}
		}
		
		minX += x;
		minY += y;
		maxX += x;
		maxY += y;
		
		if(minX < 0) x += -minX;
		else if(maxX >= Game.WIDTH) x -= maxX - (Game.WIDTH - 1);
		
		if(minY < 0) y += -minY;
		else if(maxY >= Game.HEIGHT) y -= maxY - (Game.HEIGHT - 1);
	}
	
	public void move(int x, int y) {
		this.x += x;
		this.y += y;
	}
	
	public void rotate(int dir) {
		rotation += dir;
		
		int n = piece.getRotationAmount();
		
		if(rotation < 0) rotation += n;
		else if(rotation >= n) rotation -= n;
	}
	
	public void manifest(Matrix blocks) {
		for(int x = 0; x < 5; x++) {
			for(int y = 0; y < 5; y++) {
				
				int type = getMatrix().getBlockType(x, y);
				
				if(type != BlockType.EMPTY) {
					blocks.setBlockType(this.x + x, this.y + y, type * blockType);
				}
			}
		}
	}
	
	public boolean overlaps(Matrix blocks) {
		for(int x = 0; x < 5; x++) {
			for(int y = 0; y < 5; y++) {
				
				if(getMatrix().getBlockType(x, y) != BlockType.EMPTY && blocks.getBlockType(this.x + x, this.y + y) != BlockType.EMPTY) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isOutOfBounds() {
		for(int x = 0; x < 5; x++) {
			for(int y = 0; y < 5; y++) {
				
				if(getMatrix().getBlockType(x, y) != BlockType.EMPTY) {
					
					int canvasX = this.x + x;
					int canvasY = this.y + y;
					
					if(canvasX < 0 || canvasY < 0 || canvasX >= Game.WIDTH || canvasY >= Game.HEIGHT) return true;
				}
			}
		}
		
		return false;
	}
	
	public int getBlockType(int canvasX, int canvasY) {
		int x = canvasX - this.x;
		int y = canvasY - this.y;
		
		return getMatrix().getBlockType(x, y) * blockType;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public Matrix getMatrix() {
		return piece.getRotation(rotation);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
