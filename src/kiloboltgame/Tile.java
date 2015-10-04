package kiloboltgame;

import java.awt.Image;
import java.awt.Rectangle;

public class Tile {

	private Image image;
	private int tileX, tileY;
	private int speedX, type;
	private Robot robot = StartingClass.getRobot();
	private Background bg = StartingClass.getBg1();
	private Rectangle r;

	public Tile(int x, int y, int typeInt) {
		// TODO Auto-generated constructor stub
		tileX = x * 40;
		tileY = y * 40;
		type = typeInt;
		r = new Rectangle();
		if (type == 4) {
			image = StartingClass.tilegrassLeft;
		} else if (type == 8) {
			image = StartingClass.tilegrassTop;
		} else if (type == 6) {
			image = StartingClass.tilegrassRight;
		} else if (type == 2) {
			image = StartingClass.tilegrassBot;
		} else if (type == 5) {
			image = StartingClass.tiledirt;
		}
		else {
			type = 0;
		}
	}

	public void update() {
		speedX = bg.getSpeedX() * 5;
		tileX += speedX;
		r.setBounds(tileX, tileY, 40, 40);
		if (type!=0 && r.intersects(Robot.yellowRed)) {
			checkVerticalCollision(robot.rectUp, robot.rectBottom);
			checkSideCollision(robot.rectLeft, robot.rectRight, robot.leftFoot, robot.rightFoot);
		}
	}

	private void checkSideCollision(Rectangle rectLeft, Rectangle rectRight,
			Rectangle leftFoot, Rectangle rightFoot) {
		// TODO Auto-generated method stub
			if (type!=5 && type!=0 && type!=2) {
				if (rectLeft.intersects(r)) {
					robot.setCenterX(tileX+102);
				}
				else if (leftFoot.intersects(r)) {
					robot.setCenterX(tileX+85);
				}
				if (rectRight.intersects(r)) {
					robot.setCenterX(tileX-62);
				}
				else if (rightFoot.intersects(r)) {
					robot.setCenterX(tileX-45);
				}
				robot.setSpeedX(0);
			}
	}

	public void checkVerticalCollision(Rectangle rtop, Rectangle rbot) {
		if (rtop.intersects(r)) {
//			System.out.println("upper collision");
		}
		if (rbot.intersects(r) && type == 8) {
//			System.out.println("lower collision");
			robot.setJumped(false);
			robot.setSpeedY(0);
			robot.setCenterY(tileY - 63);
		}
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getTileX() {
		return tileX;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Background getBg() {
		return bg;
	}

	public void setBg(Background bg) {
		this.bg = bg;
	}

}
