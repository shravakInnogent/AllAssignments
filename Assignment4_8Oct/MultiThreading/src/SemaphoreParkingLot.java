
import java.util.concurrent.Semaphore;

public class SemaphoreParkingLot {
    private static final int MAX_SPOTS = 3;
    private static final Semaphore parkingSpots = new Semaphore(MAX_SPOTS);

    static class Car implements Runnable {
        private final String name;

        Car(String name) {
            this.name = name;
        }
        @Override
        public void run() {
            try {
                System.out.println(name + " is trying to park.");
                parkingSpots.acquire();
                System.out.println(name + " has parked.");
                Thread.sleep((long) (Math.random() * 4000));
                System.out.println(name + " is leaving.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                parkingSpots.release();
            }
        }
    }
    public static void main(String[] args) {
        String[] carNames = {"Car-1", "Car-2", "Car-3"};

        for (String name : carNames) {
            new Thread(new Car(name)).start();
        }
    }
}
