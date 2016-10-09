/**
 * Created by Evi on 28-9-2016.
 */
public class World {
    private static final int NR_OF_VIEWERS = 0;
    private static final int NR_OF_BUYERS = 4;

    /**
     * @param args
     */
    public static void main(String[] args) {
        HISWA hiswa = new HISWA();
        Thread[] viewer;
        Thread[] buyer;

        viewer = new Thread[NR_OF_VIEWERS];
        buyer = new Thread[NR_OF_BUYERS];


        for (int i = 0; i < NR_OF_VIEWERS; i++) {
            viewer[i] = new Viewer("v" + (i + 1), hiswa);
            viewer[i].start();
        }

        for (int i = 0; i < NR_OF_BUYERS; i++) {
            buyer[i] = new Buyer("b" + (i + 1), hiswa);
            buyer[i].start();
        }
    }

}
