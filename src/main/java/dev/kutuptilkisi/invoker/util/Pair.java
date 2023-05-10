package dev.kutuptilkisi.invoker.util;

public class Pair<K, V>{
    private final K key;
    private final V value;

    public Pair(){
        this(null, null);
    }

    public Pair(K key, V value){
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
