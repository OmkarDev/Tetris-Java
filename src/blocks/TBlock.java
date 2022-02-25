package blocks;

import window.Launcher;

public class TBlock extends Block {

	public TBlock(Launcher game) {
		super(game, "T");
		int[][] struct = { { 0, 1, 0 }, { 1, 1, 1 }, { 0, 0, 0 } };
		this.struct = struct;
		this.size = 3;
	}

}
