package blocks;

import static window.Statics.W;
import static window.Statics.cols;
import static window.Statics.rows;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import window.Launcher;

public abstract class Block {

	public int i = 4, j;

	int size = 3;
	int type;

	public int[][] struct;

	BufferedImage img;

	Launcher game;

	Block(Launcher game, String block_name) {
		this.game = game;
		if (block_name == "S" || block_name == "J") {
			img = Launcher.col1;
			this.type = 1;
		}
		if (block_name == "Z" || block_name == "L") {
			img = Launcher.col2;
			this.type = 2;
		}
		if (block_name == "I" || block_name == "T" || block_name == "O") {
			img = Launcher.col3;
			this.type = 3;
		}
	}

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

	public boolean isHitting(int[][] struct) {
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

	public boolean isInBounds(int[][] struct) {
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

	public void placeTheBlock() {
		for (int j = 0; j < struct.length; j++) {
			for (int i = 0; i < struct[0].length; i++) {
				if (struct[j][i] == 1) {
					int x = i + this.i;
					int y = j + this.j;
					Launcher.board[y][x] = type;
				}
			}
		}
		removeTheLine();
	}

	public void removeTheLine() {
		for (int j = rows - 1; j >= 0; j--) {
			int same = 0;
			for (int i = 0; i < cols; i++) {
				if (Launcher.board[j][i] != 0) {
					same++;
				}
			}
			if (same == cols) {
				removeRow(j);
			}
		}
	}

	public void removeRow(int row) {
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
		removeTheLine();
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

	public void rotate() {
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

	public void render(Graphics2D g, int ox, int oy) {
		g.setStroke(new BasicStroke(1.5f));
		g.setColor(Color.black);
		for (int j = 0; j < struct.length; j++) {
			for (int i = 0; i < struct[0].length; i++) {
				if (struct[j][i] == 1) {
					g.drawImage(img, ox + i * W + this.i * W, oy + j * W + this.j * W, W, W, null);
					g.drawRect(ox + i * W + this.i * W, oy + j * W + this.j * W, W, W);
				}
			}
		}
	}

}
