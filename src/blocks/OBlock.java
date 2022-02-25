package blocks;

import window.Launcher;

public class OBlock extends Block {

	public OBlock(Launcher game) {
		super(game,"O");
		int[][] struct = { { 0, 1, 1, 0}, { 0, 1, 1, 0}, { 0, 0, 0, 0}};
		this.struct = struct;
	}

	public void rotate() {
	}

}
