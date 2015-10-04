package kiloboltgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import kilobolt.framework.Animation;

public class StartingClass extends Applet implements Runnable, KeyListener {
	enum GameState {Running, Dead};

	private static Robot robot;
	public static Heliboy hb, hb2;
	private Image image, currentSprite, background;
	private Image character, characterJump, characterDown, character2,
			character3;
	private Image heliboy, heliboy2, heliboy3, heliboy4, heliboy5;
	static Image tiledirt, tilegrassTop, tilegrassBot, tilegrassLeft, tilegrassRight;
	private Graphics second;
	private URL base;
	private static Background bg1, bg2;
	public static int score = 0;
	private Font font = new Font(null, Font.BOLD, 30);
	private Animation anim, hanim;
	static final long serialVersionUID = 1;
	private ArrayList<Tile> tilearray = new ArrayList<Tile>();
	GameState state = GameState.Running;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Q-Bot Alpha");
		addKeyListener(this);
		try {
			base = getDocumentBase();
		} catch (Exception e) {
			//
		}

		background = getImage(base, "../src/data/background.png");
		character = getImage(base, "../src/data/character.png");
		characterDown = getImage(base, "../src/data/down.png");
		characterJump = getImage(base, "../src/data/jumped.png");
		character2 = getImage(base, "../src/data/character2.png");
		character3 = getImage(base, "../src/data/character3.png");
		heliboy = getImage(base, "../src/data/heliboy.png");
		heliboy2 = getImage(base, "../src/data/heliboy2.png");
		heliboy3 = getImage(base, "../src/data/heliboy3.png");
		heliboy4 = getImage(base, "../src/data/heliboy4.png");
		heliboy5 = getImage(base, "../src/data/heliboy5.png");
		
		tiledirt = getImage(base, "../src/data/tiledirt.png");
		tilegrassBot = getImage(base, "../src/data/tilegrassbot.png");
		tilegrassTop = getImage(base, "../src/data/tilegrasstop.png");
		tilegrassLeft = getImage(base, "../src/data/tilegrassleft.png");
		tilegrassRight = getImage(base, "../src/data/tilegrassright.png");
		
		anim = new Animation();
		anim.addFrame(character, 1250);
		anim.addFrame(character2, 50);
		anim.addFrame(character3, 50);

		hanim = new Animation();
		hanim.addFrame(heliboy, 50);
		hanim.addFrame(heliboy2, 50);
		hanim.addFrame(heliboy3, 50);
		hanim.addFrame(heliboy4, 50);
		hanim.addFrame(heliboy5, 50);

		currentSprite = anim.getImage();
	}

	@Override
	public void start() {
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		robot = new Robot();
		hb = new Heliboy(340, 360);
		hb2 = new Heliboy(700, 360);
		try{
			loadMap("../src/data/map1.txt");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		Thread thread = new Thread(this);
		thread.start();
	}

	private void loadMap (String filename) throws IOException{
		// TODO Auto-generated method stub
		ArrayList<String> lines = new ArrayList<String>();
		int width = 0;
		int height = 0;
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		while (true) {
			String line = reader.readLine();
			if (line==null) {
				reader.close();
				break;
			}
			if (!line.startsWith("!")) {
				lines.add(line);
				width = Math.max(width, line.length());
			}
		}
		height = lines.size();
		for (int j = 0; j < height; j++) {
            String line = (String) lines.get(j);
            for (int i = 0; i < width; i++) {
                if (i < line.length()) {
                    char ch = line.charAt(i);
                    Tile t = new Tile(i, j, Character.getNumericValue(ch));
                    tilearray.add(t);
                }
            }
		}
	}

	@Override
	public void stop() {
		super.stop();
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void run() {
		if (state == GameState.Running) {
			// TODO Auto-generated method stub
			while (true) {
				robot.update();
				if (robot.isDucked()) {
					currentSprite = characterDown;
				} else if (robot.isJumped()) {
					currentSprite = characterJump;
				} else {
					currentSprite = anim.getImage();
				}
				ArrayList<Projectile> projectiles = robot.getProjectiles();
				for (int i = 0; i < projectiles.size(); i++) {
					Projectile p = projectiles.get(i);
					if (p.isVisible()) {
						p.update();
					} else {
						projectiles.remove(i);
					}
				}
				updateTiles();
				hb.update();
				hb2.update();
				bg1.update();
				bg2.update();

				animate();
				repaint();

				try {
					Thread.sleep(17);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (robot.getCenterY() > 500) {
					state = GameState.Dead;
				}
			}
		}
	}

	private void animate() {
		// TODO Auto-generated method stub
		anim.update(40);
		hanim.update(10);
	}

	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}
		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);
	}

	public void paint(Graphics g) {
		if (state == GameState.Running) {
			g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
			g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
			paintTiles(g);
			//		g.drawRect((int)robot.rectUp.getX(),(int)robot.rectUp.getY(),(int)robot.rectUp.getWidth(), (int)robot.rectUp.getHeight());
			//		g.drawRect((int)robot.rectBottom.getX(),(int)robot.rectBottom.getY(),(int)robot.rectBottom.getWidth(), (int)robot.rectBottom.getHeight());
			g.drawImage(currentSprite, robot.getCenterX() - 61,
					robot.getCenterY() - 63, this);
			g.drawImage(hanim.getImage(), hb.getCenterX() - 48,
					hb.getCenterY() - 48, this);
			g.drawImage(hanim.getImage(), hb2.getCenterX() - 48,
					hb.getCenterY() - 48, this);
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString(score), 740, 30);
			ArrayList<Projectile> projectiles = robot.getProjectiles();
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile p = projectiles.get(i);
				g.setColor(Color.YELLOW);
				g.fillRect(p.getX(), p.getY(), 10, 5);
			}
		}
		else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.WHITE);
			g.drawString("DEAD", 360, 240);
		}
	}

	private void updateTiles() {
		for (int i = 0; i < tilearray.size(); i++) {
			tilearray.get(i).update();
		}
	}

	private void paintTiles(Graphics g) {
		Tile t;
		for (int i = 0; i < tilearray.size(); i++) {
			t = tilearray.get(i);
			g.drawImage(t.getImage(), t.getTileX(), t.getTileY(), this);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_UP:
//			System.out.println("Move up");
			break;
		case KeyEvent.VK_DOWN:
			// System.out.println("Move down");
			robot.setDucked(true);
			break;
		case KeyEvent.VK_LEFT:
			// System.out.println("Move left");
			robot.setMovingLeft(true);
			robot.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			// System.out.println("Move right");
			robot.setMovingRight(true);
			robot.moveRight();
			break;
		case KeyEvent.VK_SPACE:
			// System.out.println("Jump");
			robot.jump();
			break;
		case KeyEvent.VK_CONTROL:
			if (!robot.isDucked() && !robot.isJumped()) {
				robot.shoot();
				robot.setReadyToFire(false);
			}
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_UP:
//			System.out.println("Stop moving up");
			break;
		case KeyEvent.VK_DOWN:
			// System.out.println("Stop moving down");
			// robot.moveRight();
			currentSprite = anim.getImage();
			robot.setDucked(false);
			break;
		case KeyEvent.VK_LEFT:
			// System.out.println("Stop moving left");
			robot.stopLeft();
			break;
		case KeyEvent.VK_RIGHT:
			// System.out.println("Stop moving right");
			robot.stopRight();
			break;
		case KeyEvent.VK_SPACE:
			// System.out.println("Don't jump");
			break;
		case KeyEvent.VK_CONTROL:
			robot.setReadyToFire(true);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	public static Background getBg1() {
		return bg1;
	}
	
	public static void setBg1(Background bg1) {
		StartingClass.bg1 = bg1;
	}
	
	public static Background getBg2() {
		return bg2;
	}
	
	public static void setBg2(Background bg2) {
		StartingClass.bg2 = bg2;
	}
	
	public static Robot getRobot() {
		return robot;
	}

}

