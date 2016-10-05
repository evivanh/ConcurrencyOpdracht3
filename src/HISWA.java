import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Evi on 28-9-2016.
 */
public class HISWA {

    Lock lock;

    private static final int MAXIMUM_AMOUNT_VISITORS = 10;

    private int nrOfViewers = 0;
    private int nrOfBuyers = 0;
    private boolean buyerWaiting;
    private boolean buyerInside;

    private Condition placeAvailableForViewer, placeAvailableForBuyer;

    public HISWA(){
        lock = new ReentrantLock();
        placeAvailableForViewer = lock.newCondition();
        placeAvailableForBuyer = lock.newCondition();
    }

    public void viewerVisitsHISWA() throws InterruptedException{
        lock.lock();
        try {
            while(noPlaceAvailableForViewers()) {
                System.out.println(Thread.currentThread().getName() + " waits in line to enter the HISWA");

                placeAvailableForViewer.await();
            }


            nrOfViewers++;
            System.out.println(Thread.currentThread().getName() + " enters the HISWA");

            //TODO: sleep threads when viewers are inside, do not sleep every viewer but sleep the entire hiswa?
//            Thread.currentThread().sleep(2000);

            nrOfViewers--;
            System.out.println(Thread.currentThread().getName() + " is leaving the HISWA");
            placeAvailableForViewer.signal();

        } finally {
                lock.unlock();
        }
    }

    public void buyerVisitsHISWA() throws InterruptedException {
        lock.lock();
        try{
            while (noPlaceAvailableForBuyer()) {
                System.out.println(Thread.currentThread().getName() + " waits in line to enter the HISWA");
                buyerWaiting = true;
                placeAvailableForBuyer.await();

            }

            nrOfBuyers++;
            buyerInside = true;
            System.out.println(Thread.currentThread().getName() + " enters the HISWA");

            Thread.currentThread().sleep(3000);
            placeAvailableForBuyer.signal();
            System.out.println(Thread.currentThread().getName() + " is leaving the HISWA");


        } finally {
            lock.unlock();
        }
    }

    private boolean noPlaceAvailableForBuyer(){
        return nrOfBuyers == 4 || nrOfViewers > 0 || buyerInside ;
    }

    private boolean noPlaceAvailableForViewers() {
        return nrOfViewers == MAXIMUM_AMOUNT_VISITORS || buyerInside || buyerWaiting;
    }
}
