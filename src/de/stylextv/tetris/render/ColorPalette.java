package de.stylextv.tetris.render;

import java.awt.Color;

public class ColorPalette {
	
	private static final ColorPalette[] PALETTES = new ColorPalette[10];
	
	public static final ColorPalette LEVEL_1 = new ColorPalette(0x0058F8, 0x3CBCFC);
	public static final ColorPalette LEVEL_2 = new ColorPalette(0x00A800, 0xB8F818);
	public static final ColorPalette LEVEL_3 = new ColorPalette(0xD800CC, 0xF878F8);
	public static final ColorPalette LEVEL_4 = new ColorPalette(0x0058F8, 0x58D854);
	public static final ColorPalette LEVEL_5 = new ColorPalette(0xE40058, 0x58F898);
	public static final ColorPalette LEVEL_6 = new ColorPalette(0x58F898, 0x6888FC);
	public static final ColorPalette LEVEL_7 = new ColorPalette(0xF83800, 0x7C7C7C);
	public static final ColorPalette LEVEL_8 = new ColorPalette(0x6844FC, 0xA80020);
	public static final ColorPalette LEVEL_9 = new ColorPalette(0x0058F8, 0xF83800);
	public static final ColorPalette LEVEL_10 = new ColorPalette(0xF83800, 0xFCA044);
	
	public static final Color BLACK = new Color(0x000000);
	public static final Color WHITE = new Color(0xFFFFFF);
	
	private static int paletteID;
	
	private Color primary;
	
	private Color secondary;
	
	public ColorPalette(int primaryRgb, int secondaryRgb) {
		this.primary = new Color(primaryRgb);
		this.secondary = new Color(secondaryRgb);
		
		PALETTES[paletteID] = this;
		
		paletteID++;
	}
	
	public Color getPrimaryColor() {
		return primary;
	}
	
	public Color getSecondaryColor() {
		return secondary;
	}
	
	public static ColorPalette getPalette(int level) {
		return PALETTES[level % PALETTES.length];
	}
	
}
