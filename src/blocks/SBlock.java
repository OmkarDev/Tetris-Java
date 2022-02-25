package blocks;

import window.Launcher;

public class SBlock extends Block {

	public SBlock(Launcher game) {
		super(game,"S");
		int[][] struct = { { 0, 1, 1 }, { 1, 1, 0 }, { 0, 0, 0 } };
		this.struct = struct;
		this.size = 3;
	}

}
