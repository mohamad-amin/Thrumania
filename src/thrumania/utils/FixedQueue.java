package thrumania.utils;

import java.util.*;

/**
 * Created by mohamadamin on 6/20/16.
 */
public class FixedQueue<T> {

    Stack<T> previousItems;
    Stack<T> nextItems;
    int capacity;

    public FixedQueue(int capacity) {
        this.capacity = capacity;
        this.nextItems = new Stack<>();
        this.previousItems = new Stack<>();
    }

    public void add(T item) {
        nextItems.clear();
        addToPreviousItems(item);
    }

    private void addToPreviousItems(T item) {
        if (item instanceof HashMap && previousItems.size() > 0) {
            HashMap<Integer, Object> nState = (HashMap<Integer, Object>) item;
            HashMap<Integer, Object> pState = (HashMap<Integer, Object>) previousItems.peek();
            if (Arrays.deepEquals((byte[][]) nState.get(2), (byte[][]) pState.get(2))) return;
        }
        if (previousItems.size() == capacity) {
            previousItems.remove(0);
            previousItems.push(item);
        } else {
            previousItems.push(item);
        }
    }

    private void addToNextItems(T item) {
        if (item instanceof HashMap && nextItems.size() > 0) {
            HashMap<Integer, Object> nState = (HashMap<Integer, Object>) item;
            HashMap<Integer, Object> pState = (HashMap<Integer, Object>) nextItems.peek();
            if (Arrays.deepEquals((byte[][]) nState.get(2), (byte[][]) pState.get(2))) return;
        }
        if (nextItems.size() == capacity) {
            nextItems.remove(0);
            nextItems.push(item);
        } else {
            nextItems.push(item);
        }
    }

    public T getPrevious(T item) {
        if (previousItems.size() > 0 && previousItems.peek() != null) {
            addToNextItems(item);
            return previousItems.pop();
        } else {
            return null;
        }
    }

    public T getNext(T item) {
        if (nextItems.size() > 0 && nextItems.peek() != null) {
            addToPreviousItems(item);
            return nextItems.pop();
        } else {
            return null;
        }
    }

}
