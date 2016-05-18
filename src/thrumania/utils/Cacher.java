package thrumania.utils;

import java.util.HashMap;

/**
 * Created by mohamadamin on 5/18/16.
 */

public class Cacher<T> {

    HashMap<String, T> hashMap;

    public Cacher() {
        this.hashMap= new HashMap<>();
    }

    public T get(String key) {
        if (hashMap.containsKey(key)) {
            return hashMap.get(key);
        } else return null;
    }

    public void insert(String key, T value) {
        hashMap.put(key, value);
    }

}
