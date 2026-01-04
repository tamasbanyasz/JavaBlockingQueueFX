package GenerateValueTypePackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class TypeGenerator implements Runnable {

    private final BlockingQueue<List<String>> messageTypeQueue;
    private final int messagesPerList;
    private final List<String> typePool = Arrays.asList("Integer", "Float", "Double");

    public TypeGenerator(BlockingQueue<List<String>> messageTypeQueue, int messagesPerList) {
        this.messageTypeQueue = messageTypeQueue;
        this.messagesPerList = messagesPerList;
    }

    @Override
    public void run() {
        Random random = new Random();
        try {
            while (!Thread.currentThread().isInterrupted()) {
                List<String> generatedList = new ArrayList<>();
                for (int j = 0; j < messagesPerList; j++) {
                    String randomType = typePool.get(random.nextInt(typePool.size()));
                    generatedList.add(randomType);
                }
                messageTypeQueue.put(generatedList);
                System.out.println("Generated message list: " + generatedList);

                Thread.sleep(1000); 
            }
        } catch (InterruptedException e) {
            System.out.println("TypeGenerator interrupted.");
            Thread.currentThread().interrupt();
        }
    }
}

