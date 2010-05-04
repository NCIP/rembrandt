package gov.nih.nci.rembrandt.service.findings.strategies;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureTest
{

    static ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue( 2 );
    static ExecutorService es = Executors.newSingleThreadExecutor();

    public static void main( String[] args ) throws InterruptedException {
        new Thread( new Producer() ).start();
        Future future = es.submit( new Consumer() );
        Thread.sleep( 10000 );
        future.cancel( true );
    }

    static class Producer implements Runnable  {
        int loopCount;
        // Called from producer thread.
        public void addMessage( final String message ) {
            try {
                blockingQueue.put( message );
            }catch( InterruptedException ie ) {
                // Log exception
            }
        }

        public void run() {
            for( ;; ) {
                addMessage( "Test " + ++loopCount );
               
            }
        }
    }

    // Implement Callable<Integer>
    // Consume messages from blocking queue
    static class Consumer implements Callable {
        public Integer call()    {
            try {
                while( true ) {
                    final String message = (String) blockingQueue.take();
                    System.out.println( message );
                }
            }catch( Throwable t ) {
                System.out.println( "caught Throw: "+ t );
                // log Throwable
            }finally {
                System.out.println( "Stopped." );
                // Log that Thread has been cancelled/stopped
            }
            return 0;
        }
    }
}
