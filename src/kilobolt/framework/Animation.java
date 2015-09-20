package kilobolt.framework;

import java.awt.Image;
import java.util.ArrayList;

public class Animation {

	private ArrayList<AnimFrame> frames;
	private int currentFrame;
	private long animTime, totalDuration;
	
	public Animation() {
		// TODO Auto-generated constructor stub
		frames = new ArrayList<AnimFrame>();
		totalDuration = 0;
		synchronized (this) {
			animTime = 0;
			currentFrame = 0;
		}
	}
	
	private class AnimFrame {
		Image image;
		long endTime;
		public AnimFrame(Image i, long time) {
			image = i;
			endTime = time;
		}
	}
	
	private AnimFrame getFrame(int i) {
		return frames.get(i);
	}
	public synchronized Image getImage() {
		if (frames.size()==0) {
			return null;
		}
		return getFrame(currentFrame).image;
	}
	public synchronized void addFrame(Image image, long duration) {
		totalDuration += duration;
		frames.add(new AnimFrame(image, totalDuration));
	}
	public synchronized void update(long elapsedTime) {
		if (frames.size()>1) {
			animTime += elapsedTime;
			if (animTime >= totalDuration) {
				animTime %= totalDuration;
				currentFrame = 0;
			}
			while (animTime > getFrame(currentFrame).endTime) {
				currentFrame++;
			}
		}
	}
	
}
