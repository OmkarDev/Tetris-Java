package blocks;

import window.Launcher;

public class OBlock extends Block {

	final int[][] original_struct = { { 0, 1, 1, 0 }, { 0, 1, 1, 0 }, { 0, 0, 0, 0 } };

	public OBlock(Launcher game) {
		super(game, "O");
		this.struct = this.original_struct;
	}

	public void rotateRight() {
		
	}
	
	public void rotateLeft() {
		
	}
	
	int[][] getOriginalStruct() {
		return original_struct;
	}
	
}
