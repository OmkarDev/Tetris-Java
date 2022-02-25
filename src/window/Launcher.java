package window;

import static window.Statics.W;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

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

	public static int[][] board = new int[20][10];

	public static BufferedImage col1, col2, col3;
	
	int normal_speed = 500;
	int speed = normal_speed;
	int max_speed = 50;

	public Launcher(int width, int height, String title) {
		super(width, height, title);
		try {
			col1 = ImageIO.read(getClass().getResource("/Blue_Tile.png"));
			col2 = ImageIO.read(getClass().getResource("/Red_Tile.png"));
			col3 = ImageIO.read(getClass().getResource("/White_Tile.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		now = Instant.now();
		nextBlock = getRandomBlock();
		getNewBlock();
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
		return (int) (Math.floor(Math.random() * (max - min + 1)) + min);
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
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.black);
		g.fillRect(10 - 1, 10, 10 * W + 3, 20 * W + 2);
		block.render(g, 10, 10);
		renderBoard(g, 10, 10);
//		drawGrid(g, 10, 10);
		nextBlock(g, 250, 20);
	}

	public void nextBlock(Graphics2D g, int x, int y) {
		g.setColor(Color.black);
		g.fillRect(x + W * 3, 10, W * 6, W * 4);
		nextBlock.render(g, x, y + W / 2);
	}

	private void renderBoard(Graphics2D g, int x, int y) {
		for (int j = 0; j < 20; j++) {
			for (int i = 0; i < 10; i++) {
				int type = board[j][i];
				BufferedImage img;
				if (type == 1) {
					img = col1;
				} else if (type == 2) {
					img = col2;
				} else if (type == 3) {
					img = col3;
				} else {
					continue;
				}
				g.drawImage(img, i * W + x, j * W + y, W, W, null);
				g.setColor(Color.black);
				g.drawRect(i * W + x, j * W + y, W, W);
			}
		}
	}

//	private void drawGrid(Graphics2D g, int x, int y) {
//		g.setColor(Color.black);
//		g.setStroke(new BasicStroke(1.5f));
//		for (int i = 0; i <= rows; i++) {
//			g.drawLine(0 + x, i * W + y, cols * W + x, i * W + y);
//		}
//		for (int i = 0; i <= cols; i++) {
//			g.drawLine(i * W + x, y, i * W + x, rows * W + y);
//		}
//	}

	public static void main(String[] args) {
		Launcher game = new Launcher(10 * W * 2 + 10, 20 * W + 30, "Tetris");
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

	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			speed = normal_speed;
		}
	}

	public void keyTyped(KeyEvent e) {

	}

}
