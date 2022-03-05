package window;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public abstract class Window extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public int width, height;
	protected static String title;
	public static JFrame frame;
	private int fps = 60;
	public Thread thread;
	boolean running = true;

	public Window(int width, int height, String title) {
		this.width = width;
		this.height = height;
		Window.title = title;
		frame = new JFrame();
		setFocusable(true);
		requestFocus();
//		WIN_CLASS();
	}

	public void WIN_CLASS() {
		try {
			Toolkit xToolkit = Toolkit.getDefaultToolkit();
			java.lang.reflect.Field awtAppClassNameField = xToolkit.getClass().getDeclaredField("awtAppClassName");
			awtAppClassNameField.setAccessible(true);
			awtAppClassNameField.set(xToolkit, title + "-omkar");
		} catch (Exception e) {

		}
	}

	public synchronized void start() {
		thread = new Thread(this, title);
		thread.start();
	}

	public synchronized void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public abstract void update();

	public abstract void render(Graphics2D g);

	public void run() {
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / fps;
		double delta = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				update();
				renderWindow();
				Toolkit.getDefaultToolkit().sync();
				delta--;
			}
		}
	}

	public void renderWindow() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		render(g);
		g.dispose();
		bs.show();
	}
	
	public void display() {
		frame.add(this);
		frame.pack();
		frame.setSize(width, height + frame.getInsets().top + frame.getInsets().bottom);
		frame.setTitle(title);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		start();
	}

}