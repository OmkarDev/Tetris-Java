package blocks;

import window.Launcher;

public class ZBlock extends Block {

	public ZBlock(Launcher game) {
		super(game, "Z");
		int[][] struct = { { 1, 1, 0 }, { 0, 1, 1 }, { 0, 0, 0 } };
		this.struct = struct;
		this.size = 3;
	}

}
