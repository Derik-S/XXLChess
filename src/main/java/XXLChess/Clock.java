package XXLChess;

import processing.core.PApplet;
import java.util.concurrent.TimeUnit;

public class Clock {
    private int totalTime;
    private PApplet parent;
    private boolean isStopped;

    public Clock(int totalTime, PApplet parent) {
        this.totalTime = totalTime;
        this.parent = parent;
        this.isStopped = false;
    }

    /**
	 * Updates the timer so that it decrements when called
	 * @return
	 */
    public void update() {
        if (!isStopped && parent.frameCount % 60 == 0) {
            totalTime--;
        }
    }

    /**
	 * Increments the timer
     * @param amount Timer increments from this amount
     * @param first Edge case so that when it is set up it doesn't increment yet
	 * @return
	 */
    public void increment(int amount, boolean first) {
        if (first) {
            return;
        }
        else if (totalTime < 0) {
            totalTime = 0;
        } 
        else {
            totalTime += amount;
        }
    }

    /**
	 * Stops the timer
	 */
    public void stop() {
        isStopped = true;
    }

    /**
	 * Starts the timer
	 */
    public void start() {
        isStopped = false;
    }

    public void display(float x, float y) {
        int minutes = (int) TimeUnit.SECONDS.toMinutes(totalTime);
        int seconds = totalTime % 60;

        String timerText = String.format("%02d:%02d", minutes, seconds);
        parent.fill(204, 204, 204);
        parent.rect(x, y - 30, 100, 100);
        parent.fill(255);
        parent.text(timerText, x, y);
        parent.textSize(30);
    }

    public boolean isTimeUp() {
        return totalTime <= 0;
    }
}
