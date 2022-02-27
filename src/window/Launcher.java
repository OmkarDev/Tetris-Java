package window;

import static window.Statics.TOP_SCORE;
import static window.Statics.W;
import static window.Statics.backgroundColor;
import static window.Statics.blue;
import static window.Statics.cols;
import static window.Statics.green;
import static window.Statics.max_speed;
import static window.Statics.normal_speed;
import static window.Statics.orange;
import static window.Statics.purple;
import static window.Statics.red;
import static window.Statics.rows;
import static window.Statics.skyblue;
import static window.Statics.speed;
import static window.Statics.yellow;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;

import blocks.Block;
import blocks.IBlock;
import blocks.JBlock;
import blocks.LBlock;
import blocks.OBlock;
import blocks.SBlock;
import blocks.TBlock;
import blocks.ZBlock;

public class Launcher extends Window implements KeyListener {
	private static final long serialVersionUID = 1L;

	public Block block;
	Instant now, later;

	public Block nextBlock;
	public Block holdingBlock;

	public static int[][] board = new int[20][10];

	public static BufferedImage frame;

	public static boolean canHold = true;

	Font font;

	public Launcher(int width, int height, String title) {
		super(width, height, title);
		try {
			frame = ImageIO.read(getClass().getResource("/Frame.png"));
			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/font.ttf"));
			font = font.deriveFont(Font.BOLD, 30);
		} catch (Exception e) {
			e.printStackTrace();
		}
		now = Instant.now();
		nextBlock = getRandomBlock();
		getNewBlock();

		TOP_SCORE = getScore();
	}

	public Block getRandomBlock() {
		ArrayList<Block> blocks = new ArrayList<Block>();
		blocks.add(new IBlock(this));
		blocks.add(new JBlock(this));
		blocks.add(new LBlock(this));
		blocks.add(new OBlock(this));
		blocks.add(new SBlock(this));
		blocks.add(new TBlock(this));
		blocks.add(new ZBlock(this));
		return blocks.get(randomRange(0, 6));
	}

	int randomRange(int min, int max) {
		Random random = new Random();
		int number = random.nextInt(7);
		return number;
	}

	public void getNewBlock() {
		block = nextBlock;
		nextBlock = getRandomBlock();
	}

	public void update() {
		later = Instant.now();
		if (Duration.between(now, later).toMillis() >= speed) {
			block.update();
			now = Instant.now();
		}
	}

	public void render(Graphics2D g) {
		int x = W * 9 + W / 2 - 2;
		int y = W * 2;
		g.drawImage(frame, 0, 0, null);
		drawGrid(g, x, y);
		block.render(g, x, y);
		nextBlock(g, 480, 25 * 5 - 20);
		holdingBlock(g, 480, 25 * 17);
		renderBoard(g, x, y);

		g.setColor(Color.white);
		g.setFont(font);
		g.drawString(String.format("%8d", Statics.SCORE), 29, 90 + g.getFontMetrics().getHeight());
		g.drawString(String.format("%8d", Statics.TOP_SCORE), 29, 214 + g.getFontMetrics().getHeight());
	}

	private void holdingBlock(Graphics2D g, int x, int y) {
		if (holdingBlock != null) {
			holdingBlock.render(g, x, y + W / 2);
		}
	}

	private void drawGrid(Graphics2D g, int x, int y) {
		g.setColor(backgroundColor);
		for (int i = 0; i <= rows; i++) {
			g.drawLine(0 + x, i * W + y, cols * W + x, i * W + y);
		}
		for (int i = 0; i <= cols; i++) {
			g.drawLine(i * W + x, y, i * W + x, rows * W + y);
		}
	}

	public void nextBlock(Graphics2D g, int x, int y) {
		nextBlock.render(g, x, y + W / 2);
	}

	private void renderBoard(Graphics2D g, int x, int y) {
		for (int j = 0; j < 20; j++) {
			for (int i = 0; i < 10; i++) {
				int type = board[j][i];
				Color col;
				if (type == 1) {
					col = green;
				} else if (type == 2) {
					col = red;
				} else if (type == 3) {
					col = skyblue;
				} else if (type == 4) {
					col = blue;
				} else if (type == 5) {
					col = orange;
				} else if (type == 6) {
					col = purple;
				} else if (type == 7) {
					col = yellow;
				} else {
					continue;
				}
				g.setColor(col);
				g.fillRect(i * W + x, j * W + y, W + 1, W + 1);
			}
		}
	}

	public static void main(String[] args) {
		Launcher game = new Launcher(720, 600, "Tetris");
		game.addKeyListener(game);
		game.display();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) {
			block.rotate();
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			block.goLeft();
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			block.goRight();
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			speed = max_speed;
		}
		if (e.getKeyCode() == KeyEvent.VK_C) {
			if (holdingBlock == null) {
				Block currentBlock = block;
				currentBlock.reset();
				holdingBlock = currentBlock;
				block = nextBlock;
				nextBlock = getRandomBlock();
				canHold = false;
				return;
			} else {
				if (canHold) {
					Block currentBlock = block;
					currentBlock.reset();
					block = holdingBlock;
					holdingBlock = currentBlock;
					canHold = false;
				}
			}
		}

	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			speed = normal_speed;
		}
	}

	public void keyTyped(KeyEvent e) {

	}

	public static void writeScore() {
		try {
			File file = new File("top.txt");
			FileWriter myWriter = new FileWriter(file);
			myWriter.write(Integer.toString(TOP_SCORE));
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getScore() {
		int score = 0;
		File file = new File("top.txt");
		if(!file.exists()) {
			try {
				file.createNewFile();
				TOP_SCORE = 0;
				writeScore();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			Scanner myReader = new Scanner(file);
			score = myReader.nextInt();
			myReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return score;
	}

}
