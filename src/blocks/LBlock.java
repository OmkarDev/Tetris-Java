package blocks;

import window.Launcher;

public class LBlock extends Block {

	public LBlock(Launcher game) {
		super(game, "L");
		int[][] struct = { { 0, 0, 1 }, { 1, 1, 1 }, { 0, 0, 0 } };
		this.struct = struct;
		this.size = 3;
	}

}
