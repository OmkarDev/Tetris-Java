package blocks;

import window.Launcher;

public class IBlock extends Block {

	public IBlock(Launcher game) {
		super(game,"I");
		int[][] struct = { { 0, 0, 0, 0 }, { 1, 1, 1, 1 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
		this.struct = struct;
		this.size = 4;
	}

}
