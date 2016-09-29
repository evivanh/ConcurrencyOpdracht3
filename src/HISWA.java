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

    private Condition placeAvailable;

    public void HISWA(){
        lock = new ReentrantLock();
        placeAvailable = lock.newCondition();
    }

    public void visitHISWA() throws InterruptedException{

        try {
            while(noPlaceAvailable ())
                placeAvailable.wait();

            System.out.println(Thread.currentThread().getName() + " waits in line to enter the HISWA");

            nrOfViewers++;

        } finally {
                lock.unlock();
        }



    }

    private boolean noPlaceAvailable() {

        if (nrOfViewers == MAXIMUM_AMOUNT_VISITORS){
            return true;
        }else if (nrOfBuyers > 0){
            return true;
        }else {
            return false;
        }
    }
}
