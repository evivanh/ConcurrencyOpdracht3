/**
 * Created by Evi on 28-9-2016.
 */
public class Viewer extends Thread {

    private HISWA hiswa;

    public Viewer(String name, HISWA hiswa) {
        super(name);
        this.hiswa = hiswa;
    }

    public void run() {
        while (true) {
            try {
                justLive();
                hiswa.viewerVisitsHISWA();
                isLooking();
                hiswa.viewerIsLeaving();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void isLooking() {

        System.out.println("\t\t" + currentThread().getName() + " is in the HISWA.");
        try {
            Thread.sleep((int) (Math.random() * 1000) + 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void justLive() throws InterruptedException {
        System.out.println("\t\t" + getName() + " living.");
        Thread.sleep((int) (Math.random() * 1000) + 1);
    }

}
