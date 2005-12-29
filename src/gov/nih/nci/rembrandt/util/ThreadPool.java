package gov.nih.nci.rembrandt.util;

import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * @author BhattarR
 */
public class ThreadPool {
    static HashMap allThreads = new HashMap();
    public final static long MAX_THREADS = 400;
    public static long THREAD_COUNT = 0;
    private static Logger logger = Logger.getLogger(ThreadPool.class);

    public synchronized static AppThread newAppThread(MyRunnable r) {
        while (THREAD_COUNT >= MAX_THREADS ) {
            try {
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        AppThread appThread = new AppThread(r);
        logger.debug("BEGIN: New thread created.  Thread Count: " + ThreadPool.THREAD_COUNT);
        return appThread;
    }

    public static class AppThread {
        private MyRunnable ar = null;
        Thread t = null;
        Long ID;

        public void execute() {
             ar.codeToRun();
             synchronized(allThreads) {
                THREAD_COUNT--;
                allThreads.remove(this.getID());
             };
            logger.debug("END: Thread completed.  Thread Count: " + ThreadPool.THREAD_COUNT);
        }
        private  AppThread(MyRunnable appRunnable) {
            this.ar = appRunnable;
            t= new Thread(new Runnable() {
                public void run() {
                    execute();
                }
            });
            synchronized(allThreads) {
                THREAD_COUNT++;
                ID = new Long(System.currentTimeMillis() + THREAD_COUNT);
                allThreads.put(this.getID(), this);
            };
        }
        public Long getID(){
            return ID;
        }
        public void start() {
            t.start();
        }
    }


    public static interface MyRunnable {
        public void codeToRun();
    }
}
