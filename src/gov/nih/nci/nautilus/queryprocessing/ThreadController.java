package gov.nih.nci.nautilus.queryprocessing;

import java.util.Iterator;
import java.util.List;

/**
 * @author BhattarR
 */
public class ThreadController {
     private final static long SLEEP_TIME= 10;
    public static void sleepOnEvents(List eventList) throws InterruptedException {
        boolean sleep = true;
        do {
            Thread.sleep(SLEEP_TIME);
            sleep = false;
            for (Iterator iterator = eventList.iterator(); iterator.hasNext();) {
                DBEvent eventObj = (DBEvent)iterator.next();
                if (! eventObj.isCompleted()) {
                    sleep = true;
                    break;
                }
            }
        } while (sleep);

        return;
    }
}
