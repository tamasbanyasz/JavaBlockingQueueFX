package CounterPackage;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Counter {
    AtomicInteger counter = new AtomicInteger(0);
    AtomicReference<Double> doubleValue = new AtomicReference<>(0.0);
    AtomicReference<Float> floatValue = new AtomicReference<>(0.0f);

    
    public void addAtomicDouble(double value) {
    	doubleValue.updateAndGet(old -> old + value);
    }
    
    public void addAtomicFloat(float value) {
    	floatValue.updateAndGet(old -> old + value);
    }
    
    public void addAtomicValue(int value) {
        counter.addAndGet(value); 
    }

    public int getAtomicInteger() {
        return counter.get(); 
    }
    
    public double getAtomicDouble() {
    	return doubleValue.get();
    	
    }
    
    public float getAtomicFloat() {
    	return floatValue.get();	
    }
    
    public void subtractAtomicDouble(double value) {
        doubleValue.updateAndGet(old -> old - value);
    }

    public void subtractAtomicFloat(float value) {
        floatValue.updateAndGet(old -> old - value);
    }

    public void subtractAtomicValue(int value) {
        counter.addAndGet(-value);
    }
}