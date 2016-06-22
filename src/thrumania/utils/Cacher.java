package thrumania.utils;

import java.util.HashMap;

/**
 * Created by mohamadamin on 5/18/16.
 */

public class Cacher<K,T> {

    HashMap<K, T> hashMap;

    public Cacher() {
        this.hashMap= new HashMap<>();
    }

    public T get(K key) {
        if (hashMap.containsKey(key)) {
            return hashMap.get(key);
        } else return null;
    }

    public void insert(K key, T value) {
        hashMap.put(key, value);
    }

}
