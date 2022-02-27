package blocks;

import window.Launcher;

public class SBlock extends Block {
	
	final int[][] original_struct = { { 0, 1, 1 }, { 1, 1, 0 }, { 0, 0, 0 } };

	public SBlock(Launcher game) {
		super(game, "S");
		this.struct = original_struct;
		this.size = 3;
	}
	
	int[][] getOriginalStruct() {
		return original_struct;
	}
}
