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

    public void update() {
        if (!isStopped && parent.frameCount % 60 == 0) {
            totalTime--;
        }
    }

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

    public void stop() {
        isStopped = true;
    }

    public void start() {
        isStopped = false;
    }

    public void display(float x, float y) {
        int minutes = (int) TimeUnit.SECONDS.toMinutes(totalTime);
        int seconds = totalTime % 60;

        String timerText = String.format("%02d:%02d", minutes, seconds);
        parent.fill(204, 204, 204);
        parent.rect(x, y - 20, 75, 50);
        parent.fill(255);
        parent.text(timerText, x, y);
    }

    public boolean isTimeUp() {
        return totalTime <= 0;
    }
}
