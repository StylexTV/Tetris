package de.stylextv.tetris.game;

import de.stylextv.tetris.audio.SoundUtil;
import de.stylextv.tetris.io.Input;

public class Game {
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 20;
	
	public static final int UPDATE_RATE = 80;
	public static final int CLEAR_RATE = 8;
	public static final int DEATH_SCREEN_RATE = 10;
	
	private Matrix blocks = new Matrix(WIDTH, HEIGHT);
	
	private MovingPiece movingPiece;
	
	private int score;
	
	private int requiredScore = 4;
	
	private int level;
	
	private int updateTimer;
	
	private boolean[] linesToClear = new boolean[HEIGHT];
	
	private int linesCleared;
	
	private int clearState = -1;
	
	private boolean flash;
	
	private int deathScreenState;
	
	public void handleInputs() {
		if(movingPiece != null) {
			
			int dirX = 0;
			int dirY = 0;
			
			int rotateDir = 0;
			
			if(Input.isKeyPressed(Input.KEY_LEFT)) dirX--;
			if(Input.isKeyPressed(Input.KEY_RIGHT)) dirX++;
			if(Input.isKeyPressed(Input.KEY_DOWN, 1000000000 / 30)) dirY++;
			
			if(Input.isKeyPressed(Input.KEY_ROTATE_LEFT)) rotateDir--;
			if(Input.isKeyPressed(Input.KEY_ROTATE_RIGHT)) rotateDir++;
			
			movePiece(dirX, dirY);
			rotatePiece(rotateDir);
		}
	}
	
	private void movePiece(int x, int y) {
		if(x == 0 && y == 0) return;
		
		movingPiece.move(x, y);
		
		if(movingPiece.isOutOfBounds() || movingPiece.overlaps(blocks)) {
			movingPiece.move(-x, -y);
			
			return;
		}
		
		if(y != 0) updateTimer = getUpdateRate();
	}
	
	private void rotatePiece(int dir) {
		if(dir == 0 || movingPiece.getPiece().getRotationAmount() == 1) return;
		
		movingPiece.rotate(dir);
		
		if(movingPiece.overlaps(blocks)) {
			movingPiece.rotate(-dir);
			
			return;
		}
		
		if(movingPiece.isOutOfBounds()) movingPiece.putIntoBounds();
	}
	
	public void update() {
		if(updateTimer != 0) {
			updateTimer--;
			
			return;
		}
		
		boolean noDelay = false;
		
		if(deathScreenState != 0) {
			
			deathScreenState++;
			
			if(deathScreenState == 30 + HEIGHT) {
				clear();
			}
			
		} else if(movingPiece == null) {
			
			if(clearState == -1) {
				
				flash = false;
				
				summonRandomPiece();
				
			} else {
				
				if(linesCleared >= 4) flash = !flash;
				else flash = false;
				
				if(clearState == WIDTH / 2) {
					moveLinesDown();
					
					clearState = -1;
					
					if(linesCleared < 4) score += linesCleared;
					else score += linesCleared * 2;
					
					if(score >= requiredScore) {
						
						requiredScore += 4 + level * 2;
						
						level++;
					}
					
					noDelay = true;
					
				} else {
					for(int y = 0; y < HEIGHT; y++) {
						if(linesToClear[y]) {
							
							blocks.setBlockType(WIDTH / 2 + clearState, y, BlockType.EMPTY);
							blocks.setBlockType(WIDTH / 2 - 1 - clearState, y, BlockType.EMPTY);
						}
					}
					
					clearState++;
				}
			}
			
		} else {
			movingPiece.move(0, 1);
			
			if(movingPiece.isOutOfBounds() || movingPiece.overlaps(blocks)) {
				
				movingPiece.move(0, -1);
				
				movingPiece.manifest(blocks);
				
				movingPiece = null;
				
				clearFullLines();
				
				noDelay = true;
			}
		}
		
		if(noDelay) {
			update();
		} else {
			updateTimer = deathScreenState != 0 ? DEATH_SCREEN_RATE : clearState == -1 ? getUpdateRate() : CLEAR_RATE;
		}
	}
	
	public void clear() {
		blocks.clear();
		
		movingPiece = null;
		
		score = 0;
		
		requiredScore = 4;
		
		level = 0;
		
		updateTimer = 0;
		
		deathScreenState = 0;
	}
	
	private void clearFullLines() {
		linesCleared = 0;
		
		for(int y = 0; y < HEIGHT; y++) {
			
			if(isFullLine(y)) {
				linesToClear[y] = true;
				
				linesCleared++;
				
				clearState = 0;
			} else {
				linesToClear[y] = false;
			}
		}
		
		if(linesCleared >= 4) SoundUtil.play(SoundUtil.TETRIS_SOUND);
		else if(linesCleared > 0) SoundUtil.play(SoundUtil.CLEAR_SOUND);
	}
	
	private void moveLinesDown() {
		for(int y = HEIGHT - 1; y > 0; y--) {
			
			if(isEmptyLine(y)) {
				
				int nextY = y - 1;
				
				while(true) {
					
					if(nextY < 0) {
						return;
					}
					
					if(!isEmptyLine(nextY)) {
						break;
					}
					
					nextY--;
				}
				
				copyLine(nextY, y);
				clearLine(nextY);
			}
		}
	}
	
	private void summonRandomPiece() {
		movingPiece = new MovingPiece(Piece.random(), BlockType.random(), WIDTH / 2 - 2, -10);
		
		movingPiece.putIntoBounds();
		
		if(movingPiece.overlaps(blocks)) {
			
			movingPiece.manifest(blocks);
			
			deathScreenState = 1;
			
			SoundUtil.play(SoundUtil.DEATH_SOUND);
		}
	}
	
	private void clearLine(int y) {
		for(int x = 0; x < WIDTH; x++) {
			blocks.setBlockType(x, y, BlockType.EMPTY);
		}
	}
	
	private void copyLine(int fromY, int toY) {
		for(int x = 0; x < WIDTH; x++) {
			blocks.setBlockType(x, toY, blocks.getBlockType(x, fromY));
		}
	}
	
	private boolean isEmptyLine(int y) {
		for(int x = 0; x < WIDTH; x++) {
			if(blocks.getBlockType(x, y) != BlockType.EMPTY) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean isFullLine(int y) {
		for(int x = 0; x < WIDTH; x++) {
			if(blocks.getBlockType(x, y) == BlockType.EMPTY) {
				return false;
			}
		}
		
		return true;
	}
	
	private int getUpdateRate() {
		return UPDATE_RATE - level * 4;
	}
	
	public int getBlockType(int x, int y) {
		int type1 = blocks.getBlockType(x, y);
		int type2 = movingPiece == null ? BlockType.EMPTY : movingPiece.getBlockType(x, y);
		
		return type1 | type2;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getLevel() {
		return level;
	}
	
	public boolean isFlashing() {
		return flash;
	}
	
	public int getDeathScreenState() {
		int i = deathScreenState - 10;
		
		if(i < 0) return 0;
		if(i >= HEIGHT) return HEIGHT;
		
		return i;
	}
	
}
