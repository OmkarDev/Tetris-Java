package blocks;

import window.Launcher;

public class JBlock extends Block {

	public JBlock(Launcher game) {
		super(game, "J");
		int[][] struct = { { 1, 0, 0 }, { 1, 1, 1 }, { 0, 0, 0 } };
		this.struct = struct;
		this.size = 3;
	}

}
