/**
 * Created by Evi on 28-9-2016.
 */
public class Viewer extends Thread {

    private HISWA hiswa;

    public Viewer(String name, HISWA hiswa){
        super(name);
        this.hiswa = hiswa;
    }

    public void run(){
        while (true){
            try {
                justLive();
                hiswa.visitHISWA();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    private void justLive() {
        try {
            System.out.println(getName() + " living.");
            Thread.sleep((int) (Math.random() * 1000));
        }catch (InterruptedException e){

        }
    }

}
