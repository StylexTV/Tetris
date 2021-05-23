package de.stylextv.tetris.game;

import de.stylextv.tetris.util.MathUtil;

public class Piece {
	
	private static final Piece[] PIECES = new Piece[7];
	
	public static final Piece T = new Piece(
			". . . . .",
			". . . . .",
			". X X X .",
			". . X . .",
			". . . . .",
			
			". . . . .",
			". . X . .",
			". X X . .",
			". . X . .",
			". . . . .",
			
			". . . . .",
			". . X . .",
			". X X X .",
			". . . . .",
			". . . . .",
			
			". . . . .",
			". . X . .",
			". . X X .",
			". . X . .",
			". . . . ."
	);
	
	public static final Piece J = new Piece(
			". . . . .",
			". . . . .",
			". X X X .",
			". . . X .",
			". . . . .",
			
			". . . . .",
			". . X . .",
			". . X . .",
			". X X . .",
			". . . . .",
			
			". . . . .",
			". X . . .",
			". X X X .",
			". . . . .",
			". . . . .",
			
			". . . . .",
			". . X X .",
			". . X . .",
			". . X . .",
			". . . . ."
	);
	
	public static final Piece Z = new Piece(
			". . . . .",
			". . . . .",
			". X X . .",
			". . X X .",
			". . . . .",
			
			". . . . .",
			". . . X .",
			". . X X .",
			". . X . .",
			". . . . ."
	);
	
	public static final Piece O = new Piece(
			". . . . .",
			". . . . .",
			". X X . .",
			". X X . .",
			". . . . ."
	);
	
	public static final Piece S = new Piece(
			". . . . .",
			". . . . .",
			". . X X .",
			". X X . .",
			". . . . .",
			
			". . . . .",
			". . X . .",
			". . X X .",
			". . . X .",
			". . . . ."
	);
	
	public static final Piece L = new Piece(
			". . . . .",
			". . . . .",
			". X X X .",
			". X . . .",
			". . . . .",
			
			". . . . .",
			". X X . .",
			". . X . .",
			". . X . .",
			". . . . .",
			
			". . . . .",
			". . . X .",
			". X X X .",
			". . . . .",
			". . . . .",
			
			". . . . .",
			". . X . .",
			". . X . .",
			". . X X .",
			". . . . ."
	);
	
	public static final Piece I = new Piece(
			". . . . .",
			". . . . .",
			"X X X X .",
			". . . . .",
			". . . . .",
			
			". . X . .",
			". . X . .",
			". . X . .",
			". . X . .",
			". . . . ."
	);
	
	private static int pieceID;
	
	private Matrix[] rotations;
	
	public Piece(String... arr) {
		int l = arr.length / 5;
		
		this.rotations = new Matrix[l];
		
		for(int i = 0; i < l; i++) {
			rotations[i] = Matrix.getMatrix(arr, i * 5, 5, 5);
		}
		
		PIECES[pieceID] = this;
		
		pieceID++;
	}
	
	public Matrix getRotation(int i) {
		return rotations[i];
	}
	
	public int getRotationAmount() {
		return rotations.length;
	}
	
	public static Piece random() {
		return PIECES[MathUtil.RANDOM.nextInt(PIECES.length)];
	}
	
}
