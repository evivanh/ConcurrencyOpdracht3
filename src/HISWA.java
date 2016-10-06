import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Evi on 28-9-2016.
 */
public class HISWA {
    private Lock lock;

    private static final int MAXIMUM_AMOUNT_VISITORS = 10;

    private int nrOfViewers = 0;
    private int nrOfBuyers = 0;
    private boolean buyerWaiting;
    private boolean buyerInside;

    private Condition placeAvailableForViewer, placeAvailableForBuyer;

    public HISWA() {
        lock = new ReentrantLock();
        placeAvailableForViewer = lock.newCondition();
        placeAvailableForBuyer = lock.newCondition();
    }

    public synchronized void viewerVisitsHISWA() throws InterruptedException {
        lock.lock();


        while (noPlaceAvailableForViewers()) {
            System.out.println(Thread.currentThread().getName() + " waits in line to enter the HISWA");

            placeAvailableForViewer.await();
        }


        nrOfViewers++;
        System.out.println(Thread.currentThread().getName() + " enters the HISWA");


        nrOfViewers++;
        System.out.println(Thread.currentThread().getName() + " is leaving the HISWA");
        placeAvailableForViewer.signal();

        lock.unlock();
    }

    public synchronized void buyerVisitsHISWA() throws InterruptedException {
        lock.lock();
        try {
            while (noPlaceAvailableForBuyer()) {
                System.out.println(Thread.currentThread().getName() + " waits in line to enter the HISWA");
                buyerWaiting = true;
                placeAvailableForBuyer.await();

            }

            nrOfBuyers++;
            buyerInside = true;
            System.out.println(Thread.currentThread().getName() + " enters the HISWA");

            // TODO: 06/10/16 thread.sleep has to be placed inside the buyer class, done but not working
//            Thread.currentThread().sleep(3000);
            placeAvailableForBuyer.signal();
            System.out.println(Thread.currentThread().getName() + " is leaving the HISWA");


        } finally {
            lock.unlock();
        }
    }



    private boolean noPlaceAvailableForBuyer() {
        return nrOfBuyers == 4 || nrOfViewers > 0 || buyerInside;
    }

    private boolean noPlaceAvailableForViewers() {
        return nrOfViewers == MAXIMUM_AMOUNT_VISITORS || buyerInside || buyerWaiting;
    }

    private boolean placeAvailableInHiswa() {
        return noPlaceAvailableForBuyer() && noPlaceAvailableForViewers();
    }


    public synchronized void viewerIsLeaving() {
        lock.lock();
        try {
            nrOfViewers++;
            System.out.println(Thread.currentThread().getName() + " is leaving the HISWA");
            placeAvailableForViewer.signal();
        } finally {
            lock.unlock();
        }

    }
}
