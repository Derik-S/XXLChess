package XXLChess;

import processing.core.PApplet;
import java.util.concurrent.TimeUnit;

public class Clock {
    private int totalTime;
    private PApplet parent;

    public Clock(int totalTime, PApplet parent) {
        this.totalTime = totalTime;
        this.parent = parent;
    }

    public void update() {
        if (parent.frameCount % 60 == 0) {
            totalTime--;
        }
    }

    public void increment(int amount) {
        totalTime += amount;
        if (totalTime < 0) {
            totalTime = 0;
        }
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
