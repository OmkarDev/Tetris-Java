package blocks;

import window.Launcher;

public class LBlock extends Block {

	final int[][] original_struct = { { 0, 0, 1 }, { 1, 1, 1 }, { 0, 0, 0 } };

	public LBlock(Launcher game) {
		super(game, "L");
		this.struct = original_struct;
		this.size = 3;
	}
	
	int[][] getOriginalStruct() {
		return original_struct;
	}
	
	

}
