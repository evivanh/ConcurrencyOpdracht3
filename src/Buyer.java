/**
 * Created by Evi on 28-9-2016.
 */
public class Buyer extends Thread {
    private HISWA hiswa;

    public Buyer(String name, HISWA hiswa) {
        super(name);
        this.hiswa = hiswa;
    }

    public void run() {
        while (true) {
            try {
                justLive();
                hiswa.buyerVisitsHISWA();
                isLooking();
                hiswa.buyerIsLeaving();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void isLooking() {
        try {
            System.out.println(currentThread().getName() + " is in the HISWA.");
            Thread.sleep((int) (Math.random() * 1000));
        } catch (InterruptedException ie) {

        }
    }

    private void justLive() throws InterruptedException {
        System.out.println(getName() + " living.");
        Thread.sleep((int) (Math.random() * 1000) + 1);
    }
}
