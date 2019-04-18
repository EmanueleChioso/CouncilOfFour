package model.player;

import java.util.Random;

public enum CardCouncilColor {

	ORANGE,
	PURPLE,
	BLUE,
	WHITE,
	BLACK,
	PINK, 
	UNICORN;
	
	private static final CardCouncilColor[] COLORS = values();
	private static final int SIZE = COLORS.length;
	private static final Random RANDOM = new Random();
	public static CardCouncilColor randomColor(){
		return COLORS[RANDOM.nextInt(SIZE)];
		
	}
	
}
