package de.stylextv.tetris.game;

import de.stylextv.tetris.util.MathUtil;

public class BlockType {
	
	public static final int EMPTY = 0;
	
	public static final int FULL = 1;
	
	public static final int LIGHT = 1;
	public static final int FULL1 = 2;
	public static final int FULL2 = 3;
	
	public static int random() {
		return LIGHT + MathUtil.RANDOM.nextInt(FULL2 - LIGHT + 1);
	}
	
	public static boolean isPrimaryColored(int type) {
		return type % 2 != 0;
	}
	
	public static boolean isFull(int type) {
		return type >= FULL1;
	}
	
}
