package QueuesPackage;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import TypedValuePackage.TypedValue;

public class PutValueIntoQueue implements Runnable {
    private BlockingQueue<Object> queue;
    private List<String> messages;
    private int taskId;

    public PutValueIntoQueue(BlockingQueue<Object> queue, List<String> messages, int taskId) {
        this.queue = queue;
        this.messages = messages;
        this.taskId = taskId;
    }

    @Override
    public void run() {
        Random random = new Random();
        try {
            messages.forEach(message -> {
                try {
                    TypedValue typedValue;
                    switch (message) {
                        case "Integer":
                            typedValue = new TypedValue(TypedValue.Type.INTEGER, random.nextInt(100));
                            break;
                        case "Float":
                            typedValue = new TypedValue(TypedValue.Type.FLOAT, random.nextFloat() * 100);
                            break;
                        case "Double":
                            typedValue = new TypedValue(TypedValue.Type.DOUBLE, random.nextDouble() * 100);
                            break;
                        default:
                            typedValue = new TypedValue(TypedValue.Type.OTHER, message);
                    }
                    System.out.println("Generated value by:" + taskId + "_Thread is: " + typedValue);
                    queue.put(typedValue);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            System.out.println("Create values: Values sent by task " + taskId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void runPutValueIntoQueue(BlockingQueue<Object> message_queue, List<List<String>> messagesLists) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < messagesLists.size(); i++) {
            List<String> messages = messagesLists.get(i);
            int taskId = i;

            PutValueIntoQueue task = new PutValueIntoQueue(message_queue, messages, taskId);
            executor.submit(task);
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }

}


