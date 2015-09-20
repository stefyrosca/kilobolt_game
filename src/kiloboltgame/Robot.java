package kiloboltgame;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Robot {

	final int JUMPSPEED = -15;
	final int MOVESPEED = 5;

	private int centerX = 100;
	private int centerY = 377;
	private boolean jumped = false;
	private boolean ducked = false;
	private boolean movingRight = false;
	private boolean movingLeft = false;
	private boolean readyToFire = true;

	private int speedX = 0;
	private int speedY = 0;

	private static Background bg1 = StartingClass.getBg1();
	private static Background bg2 = StartingClass.getBg2();
	public static Rectangle rectUp = new Rectangle(0,0,0,0);
	public static Rectangle rectBottom = new Rectangle(0,0,0,0);
	public static Rectangle rectLeft = new Rectangle(0,0,0,0);
	public static Rectangle rectRight = new Rectangle(0,0,0,0);
	public static Rectangle leftFoot = new Rectangle(0,0,0,0);
	public static Rectangle rightFoot = new Rectangle(0,0,0,0);
	public static Rectangle yellowRed = new Rectangle(0,0,0,0);
	
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	public boolean isReadyToFire() {
		return readyToFire;
	}

	public void setReadyToFire(boolean readyToFire) {
		this.readyToFire = readyToFire;
	}

	public int getCenterX() {
		return centerX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public boolean isJumped() {
		return jumped;
	}

	public void setJumped(boolean jumped) {
		this.jumped = jumped;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public boolean isDucked() {
		return ducked;
	}

	public void setDucked(boolean ducked) {
		this.ducked = ducked;
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public void update() {
		if (speedX < 0) {
			centerX += speedX;
		}
		if (speedX == 0 || speedX < 0) {
			// System.out.println("Don't scroll the background");
			bg1.setSpeedX(0);
			bg2.setSpeedX(0);
		}
		if (centerX <= 200 && speedX > 0) {
			centerX += speedX;
		}
		// else {
		// System.out.println("Scroll background here");
		// }
		if (speedX > 0 && centerX > 200) {
			bg1.setSpeedX(-MOVESPEED / 5);
			bg2.setSpeedX(-MOVESPEED / 5);
		}

		centerY += speedY;
		// if (centerY + speedY >= GROUND) {
		// centerY = GROUND;
		// }

		speedY++;
			// if (centerY + speedY >= GROUND) {
			// centerY = GROUND;
			// speedY = 0;
			// jumped = false;
			// }
			// }
		if (speedY > 3) {
			jumped = true;
		}
		if (centerX + speedX <= 60) {
			centerX = 61;
		}
		rectUp.setRect(centerX-34,centerY-63,68,63);
		rectBottom.setRect(rectUp.getX(),rectUp.getY()+63,68,63);
		rectLeft.setRect(rectUp.getX()-26,rectUp.getY()+32, 26, 20);
		rectRight.setRect(rectUp.getX()+68,rectUp.getY()+32,26,20);
		yellowRed.setRect(centerX-110, centerY-110, 180, 180);
		leftFoot.setRect(centerX-50, centerY+20, 50, 15);
		rightFoot.setRect(centerX, centerY+20, 50, 15);
	}

	public void moveRight() {
		if (!ducked)
			speedX += MOVESPEED;
	}

	public void moveLeft() {
		if (!ducked)
			speedX -= MOVESPEED;
	}

	public void stop() {
		if (!movingRight && !movingLeft)
			speedX = 0;
		else {
			if (!movingLeft)
				moveRight();
			else
				moveLeft();
		}
	}

	public void stopRight() {
		movingRight = false;
		stop();
	}

	public void stopLeft() {
		movingLeft = false;
		stop();
	}

	public void jump() {
		if (jumped == false) {
			speedY -= 15;
			jumped = true;
		}
	}

	public void shoot() {
		if (readyToFire) {
			Projectile p = new Projectile(centerX + 50, centerY - 25);
			projectiles.add(p);
		}
	}
}
