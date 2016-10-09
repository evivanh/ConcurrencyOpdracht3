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
    private int viewerWaiting = 0;
    private int buyerWaiting = 0;
    private int waiters;
    private boolean buyerInside;

    private Condition placeAvailableForViewer, placeAvailableForBuyer;

    public HISWA() {
        lock = new ReentrantLock();
        placeAvailableForViewer = lock.newCondition();
        placeAvailableForBuyer = lock.newCondition();
    }

    public void viewerVisitsHISWA() throws InterruptedException {
        lock.lock();
        try {
            while (noPlaceAvailableForViewers()) {
                System.out.println("\t\t" + Thread.currentThread().getName() + " waits in line to enter the HISWA");
                viewerWaiting++;
                placeAvailableForViewer.await();
                viewerWaiting--;

            }
            //a separate counter was made so the number of buyers will be set 0 after all the viewers have been in the HISWA.
            if (waiters > 0) {
                waiters--;

            }
            nrOfViewers++;
            if (waiters == 0) {
                nrOfBuyers = 0;
            }

            System.out.println("\t\t" + Thread.currentThread().getName() + " enters the HISWA");

        } finally {
            lock.unlock();
        }

    }

    public void buyerVisitsHISWA() throws InterruptedException {
        lock.lock();
        try {
            while (noPlaceAvailableForBuyer()) {
                System.out.println(Thread.currentThread().getName() + " waits in line to enter the HISWA");
                //the counter of buyer who are waiting is will increase.
                buyerWaiting++;
                placeAvailableForBuyer.await();
                //the counter of buyer who are waiting will decrease because the buyer is done waiting.
                buyerWaiting--;
            }
            nrOfBuyers++;

            buyerInside = true;
            System.out.println(Thread.currentThread().getName() + " enters the HISWA");

        } finally {
            lock.unlock();
        }
    }


    private boolean noPlaceAvailableForBuyer() {
        return nrOfBuyers == 4 || nrOfViewers > 0 || buyerInside;
    }

    private boolean noPlaceAvailableForViewers() {
//        System.out.println("viewers waitings= " + viewerWaiting + "  nr of viewers= " + nrOfViewers + " buyerswaiting " + buyerWaiting);
        //if maximum number of viewers in the hiswa is reached.
        if (nrOfViewers == MAXIMUM_AMOUNT_VISITORS) {
            return true;
        }
        //if there is a buyer inside
        if (buyerInside) {
            return true;
        }
        //if number of buyer in a row is not 4
        if (nrOfBuyers != 4) {
            //if there are buyers waiting.
            if (buyerWaiting > 0) {
                return true;
            }
        }
        return false;
    }


    public void viewerIsLeaving() {
        lock.lock();
        try {
            System.out.println("\t\t" + Thread.currentThread().getName() + " is leaving the HISWA");
            nrOfViewers--;

            //if there are no viewers left, the buyer can enter.
            if (nrOfViewers == 0 || buyerWaiting > 0) {
                placeAvailableForBuyer.signal();
            } else {
                // if there is no buyer waiting OR
                // there have been 4 buyers in a row and there is room OR
                //there are still more viewers inside so allow a new one
                if (nrOfViewers > 0 || nrOfBuyers == 4 || buyerWaiting == 0) {
                    placeAvailableForViewer.signal();

                }

            }
        } finally {
            lock.unlock();
        }

    }

    public void buyerIsLeaving() {
        lock.lock();
        try {
            buyerInside = false;
            System.out.println(Thread.currentThread().getName() + " is leaving the HISWA. Nr of buyers = " + nrOfBuyers);

            if (nrOfBuyers == 4) {
                //separate counter is made so all waiting viewers can enter the HISWA without being interrupted.
                waiters = viewerWaiting;
                //let alle waiting people know the can enter the HISWA.
                placeAvailableForViewer.signalAll();
            } else {
                placeAvailableForBuyer.signal();
            }


        } finally {
            lock.unlock();
        }


    }
}
