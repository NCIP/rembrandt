package gov.nih.nci.nautilus.queryprocessing;

import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: Oct 25, 2004
 * Time: 11:11:29 AM
 * To change this template use File | Settings | File Templates.
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
