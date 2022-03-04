package blocks;

import static window.Statics.SCORE;
import static window.Statics.TOP_SCORE;
import static window.Statics.W;
import static window.Statics.blue;
import static window.Statics.cols;
import static window.Statics.green;
import static window.Statics.line1;
import static window.Statics.line2;
import static window.Statics.line3;
import static window.Statics.line4;
import static window.Statics.orange;
import static window.Statics.purple;
import static window.Statics.red;
import static window.Statics.rows;
import static window.Statics.skyblue;
import static window.Statics.yellow;

import java.awt.Color;
import java.awt.Graphics2D;

import window.Launcher;

public abstract class Block {

	public int i = 4, j = 0;

	int size = 3;
	int type = 0;

	public int[][] struct;

	Color col;

	Launcher game;

	Block(Launcher game, String block_name) {
		this.game = game;
		if (block_name == "S") {
			this.type = 1;
			col = green;
		}
		if (block_name == "Z") {
			this.type = 2;
			col = red;
		}
		if (block_name == "I") {
			this.type = 3;
			col = skyblue;
		}
		if (block_name == "J") {
			this.type = 4;
			col = blue;
		}
		if (block_name == "L") {
			this.type = 5;
			col = orange;
		}
		if (block_name == "T") {
			this.type = 6;
			col = purple;
		}
		if (block_name == "O") {
			this.type = 7;
			col = yellow;
		}
	}

	abstract int[][] getOriginalStruct();
	
	public void update() {
		if (isInBounds(struct)) {
			j++;
		}

		if (isInBounds(struct) && isHitting(struct)) {
			j--;
			placeTheBlock();
			game.getNewBlock();
		}

		if (!isInBounds(struct)) {
			j--;
			placeTheBlock();
			game.getNewBlock();
		}
	}

	protected boolean isHitting(int[][] struct) {
		for (int j = 0; j < struct.length; j++) {
			for (int i = 0; i < struct[0].length; i++) {
				if (struct[j][i] == 1) {
					int x = i + this.i;
					int y = j + this.j;
					if (Launcher.board[y][x] >= 1) {
						return true;
					}
				}
			}
		}
		return false;
	}

	protected boolean isInBounds(int[][] struct) {
		for (int j = 0; j < struct.length; j++) {
			for (int i = 0; i < struct[0].length; i++) {
				if (struct[j][i] == 1) {
					if (i + this.i >= 0 && i + this.i <= cols - 1 && j + this.j >= 0 && j + this.j <= rows - 1) {
						continue;
					} else {
						return false;
					}
				}
			}
		}
		return true;
	}

	int continuous = 0;

	protected void placeTheBlock() {
		for (int j = 0; j < struct.length; j++) {
			for (int i = 0; i < struct[0].length; i++) {
				if (struct[j][i] == 1) {
					int x = i + this.i;
					int y = j + this.j;
					Launcher.board[y][x] = type;
				}
			}
		}
		removeTheLines();
		if (continuous == 1) {
			SCORE += line1;
		}
		if (continuous == 2) {
			SCORE += line2;
		}
		if (continuous == 3) {
			SCORE += line3;
		}
		if (continuous == 4) {
			SCORE += line4;
		}
		if(SCORE > TOP_SCORE) {
			TOP_SCORE = SCORE;
			Launcher.writeScore();
		}
		Launcher.canHold = true;
	}

	private void removeTheLines() {
		for (int j = rows - 1; j >= 0; j--) {
			int same = 0;
			for (int i = 0; i < cols; i++) {
				if (Launcher.board[j][i] != 0) {
					same++;
				}
			}
			if (same == cols) {
				removeRow(j);
				continuous++;
			}
		}
	}

	private void removeRow(int row) {
		// remove row and then shift down
		for (int i = 0; i < cols; i++) {
			Launcher.board[row][i] = 0;
		}
		for (int j = row; j >= 1; j--) {
			for (int i = 0; i < cols; i++) {
				int curr = Launcher.board[j][i];
				Launcher.board[j][i] = Launcher.board[j - 1][i];
				Launcher.board[j - 1][i] = curr;
			}
		}
		removeTheLines();
	}

	public void goLeft() {
		i--;
		if (!isInBounds(struct) || isHitting(struct)) {
			i++;
		}
	}

	public void goRight() {
		i++;
		if (!isInBounds(struct) || isHitting(struct)) {
			i--;
		}
	}

	public void rotateRight() {
		int[][] struct = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = size - 1; j >= 0; j--) {
				struct[i][Math.abs(j - (size - 1))] = this.struct[j][i];
			}
		}
		if (!isInBounds(struct)) {
			return;
		}
		if (isHitting(struct)) {
			return;
		}
		this.struct = struct;
	}
	
	public void rotateLeft() {
		int[][] struct = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = size - 1; j >= 0; j--) {
				struct[Math.abs(i - (size - 1))][j] = this.struct[j][i];
			}
		}
		if (!isInBounds(struct)) {
			return;
		}
		if (isHitting(struct)) {
			return;
		}
		this.struct = struct;
	}

	public void render(Graphics2D g, int ox, int oy) {
		g.setColor(col);
		for (int j = 0; j < struct.length; j++) {
			for (int i = 0; i < struct[0].length; i++) {
				if (struct[j][i] == 1) {
					int x = ox + i * W + this.i * W;
					int y = oy + j * W + this.j * W;
					g.fillRect(x, y, W + 1, W + 1);
				}
			}
		}
	}
	

	public void reset() {
		i = 4;
		j = 0;
		struct = getOriginalStruct();
	}

}
