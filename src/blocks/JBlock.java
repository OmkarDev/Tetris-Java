package blocks;

import window.Launcher;

public class JBlock extends Block {

	final int[][] original_struct = { { 1, 0, 0 }, { 1, 1, 1 }, { 0, 0, 0 } };

	public JBlock(Launcher game) {
		super(game, "J");
		this.struct = original_struct;
		this.size = 3;
	}
	
	int[][] getOriginalStruct() {
		return original_struct;
	}

}
