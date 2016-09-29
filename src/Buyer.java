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
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    private void justLive() throws InterruptedException {
        System.out.println(getName() + " living.");
        Thread.sleep((int) (Math.random() * 1000) +1);
    }
}
