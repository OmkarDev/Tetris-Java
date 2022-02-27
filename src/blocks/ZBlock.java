package blocks;

import window.Launcher;

public class ZBlock extends Block {

	final int[][] original_struct = { { 1, 1, 0 }, { 0, 1, 1 }, { 0, 0, 0 } };

	public ZBlock(Launcher game) {
		super(game, "Z");
		this.struct = original_struct;
		this.size = 3;
	}
	
	int[][] getOriginalStruct() {
		return original_struct;
	}

}
