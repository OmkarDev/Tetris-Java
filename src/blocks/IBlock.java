package blocks;

import window.Launcher;

public class IBlock extends Block {

	final int[][] original_struct = { { 0, 0, 0, 0 }, { 1, 1, 1, 1 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

	public IBlock(Launcher game) {
		super(game, "I");
		this.struct = original_struct;
		this.size = 4;
	}

	int[][] getOriginalStruct() {
		return original_struct;
	}

}
