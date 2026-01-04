package QueuesPackage;

import java.util.concurrent.BlockingQueue;

import CounterPackage.Counter;
import TypedValuePackage.TypedValue;

public class TakeValueFromQueue implements Runnable {

    private final BlockingQueue<Object> queue;
    private final Counter counter;

    public TakeValueFromQueue(BlockingQueue<Object> queue, Counter counter) {
        this.queue = queue;
        this.counter = counter;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Object obj = queue.take();

                if (obj instanceof TypedValue typedValue) {
                    switch (typedValue.getType()) {
                        case INTEGER -> counter.addAtomicValue((Integer) typedValue.getValue());
                        case FLOAT -> counter.addAtomicFloat((Float) typedValue.getValue());
                        case DOUBLE -> counter.addAtomicDouble((Double) typedValue.getValue());
                        case OTHER -> {
                            System.out.println("Ignored non-numeric value: " + typedValue.getValue());
                        }
                    }
                } else {
                    System.out.println("Unknown object in queue: " + obj);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Taking value interrupted.");
            Thread.currentThread().interrupt();
        }
    }
    
    public static void runTakeValueFromQueue(BlockingQueue<Object> message_queue, Counter counter) throws InterruptedException {
    	
    	TakeValueFromQueue takeValue = new TakeValueFromQueue(message_queue, counter);
	    Thread takeValueFromQueueThread = new Thread(takeValue);
	    takeValueFromQueueThread.start();

	    Thread.sleep(3000);
	    takeValueFromQueueThread.interrupt();

    }

}
