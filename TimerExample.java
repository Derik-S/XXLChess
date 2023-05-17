import java.util.concurrent.TimeUnit;

public class TimerExample {
    public static void main(String[] args) {
        int totalTime = 5 * 60; // Total time in seconds (5 minutes)

        while (totalTime > 0) {
            int minutes = (int) TimeUnit.SECONDS.toMinutes(totalTime);
            int seconds = totalTime - (int) TimeUnit.MINUTES.toSeconds(minutes);

            System.out.printf("%02d:%02d%n", minutes, seconds); // Display time in "mm:ss" format

            try {
                TimeUnit.SECONDS.sleep(1); // Wait for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            totalTime--; // Decrease the remaining time by 1 second
        }

        System.out.println("Time's up!");
    }
}