package kiloboltgame;

import java.awt.Rectangle;

public class Enemy {
	private int power, speedX, centerX, centerY;
	private Background bg = StartingClass.getBg1();
	public Rectangle r = new Rectangle(0,0,0,0);
	public int health = 5;
	public void update() {
		centerX += speedX;
		speedX += bg.getSpeedX()*5;
		r.setBounds(centerX - 25, centerY-25, 50, 60);
		if (r.intersects(Robot.yellowRed)) {
			checkCollision();
		}
	}
	
	private void checkCollision() {
		if (r.intersects(Robot.rectBottom) || r.intersects(Robot.rectLeft) || r.intersects(Robot.rectRight) || r.intersects(Robot.rectUp)) {
			System.out.println("collision");
		}
	}


	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
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

	public Background getBg() {
		return bg;
	}

	public void setBg(Background bg) {
		this.bg = bg;
	}

	public void die() {
	}
	public void attack() {
	}
}
