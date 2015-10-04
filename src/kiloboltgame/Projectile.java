package kiloboltgame;

import java.awt.Rectangle;

public class Projectile {

	private int x, y, speedX;
	private boolean visible;
	private Rectangle r;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Projectile(int startX, int startY) {
		// TODO Auto-generated constructor stub
		x = startX;
		y = startY;
		speedX = 7;
		visible = true;
		r = new Rectangle(0, 0, 0, 0);
	}

	void update() {
		x += speedX;
		r.setBounds(x, y, 10, 5);
		if (x > 800) {
			visible = false;
			r = null;
		}
		if (x <= 800) {
			checkCollision();
		}
	}

	private void checkCollision() {
		// TODO Auto-generated method stub
		if (r.intersects(StartingClass.hb.r)) {
			visible = false;
			if (StartingClass.hb.health > 0) {
				StartingClass.score++;
				StartingClass.hb.health--;
			} else {
				StartingClass.hb.setCenterX(-100);
			}
		}
		if (r.intersects(StartingClass.hb2.r)) {
			visible = false;
			if (StartingClass.hb2.health > 0) {
				StartingClass.score++;
				StartingClass.hb2.health--;
			} else {
				StartingClass.hb2.setCenterX(-100);
			}
		}
	}
}
