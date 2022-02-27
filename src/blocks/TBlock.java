package blocks;

import window.Launcher;

public class TBlock extends Block {
	
	final int[][] original_struct = { { 0, 1, 0 }, { 1, 1, 1 }, { 0, 0, 0 } };

	public TBlock(Launcher game) {
		super(game, "T");
		this.struct = original_struct;
		this.size = 3;
	}
	
	int[][] getOriginalStruct() {
		return original_struct;
	}

}
