
package MainPackage;

import java.util.List;
import java.util.concurrent.*;

import CounterPackage.Counter;
import GenerateValueTypePackage.TypeGenerator;
import QueuesPackage.PutValueIntoQueue;
import QueuesPackage.TakeValueFromQueue;

public class Main {

    static BlockingQueue<Object> valueQueue = new LinkedBlockingQueue<>();
    static BlockingQueue<List<String>> generatedMessageQueue = new LinkedBlockingQueue<>();
    static Counter counter = new Counter();
    public static Counter deductedCounter = new Counter();

    
    public static Counter getCounter() {
        return counter;
    }
    
    public static Counter getDeductedCounter() {
        return deductedCounter;
    }

    public static void main(String[] args) throws InterruptedException {
    	System.out.println(System.getProperty("java.runtime.version"));

        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Start TypeGenerator in While
        TypeGenerator generator = new TypeGenerator(generatedMessageQueue, 5);
        executor.submit(generator);

        // Thread for producer
        executor.submit(() -> {
            try {
                while (true) {
                    List<String> messages = generatedMessageQueue.take();
                    PutValueIntoQueue.runPutValueIntoQueue(valueQueue, List.of(messages));
                }
            } catch (InterruptedException e) {
                System.out.println("Producer thread interrupted.");
                Thread.currentThread().interrupt();
            }
        });

        
        executor.submit(() -> {
            try {
                while (true) {
                    TakeValueFromQueue.runTakeValueFromQueue(valueQueue, counter);
                    System.out.println("Összesített Integer érték: " + counter.getAtomicInteger());
                    System.out.println("Összesített Float érték: " + counter.getAtomicFloat());
                    System.out.println("Összesített Double érték: " + counter.getAtomicDouble());
                    Thread.sleep(3000); 
                }
            } catch (InterruptedException e) {
                System.out.println("Consumer thread interrupted.");
                Thread.currentThread().interrupt();
            }
        });
     // JavaFX GUI on separate thread
        new Thread(() -> javafx.application.Application.launch(MainPackage.FXGUI.class)).start();

    }
    
}


