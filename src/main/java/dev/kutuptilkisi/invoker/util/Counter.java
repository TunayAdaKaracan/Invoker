package dev.kutuptilkisi.invoker.util;

public class Counter {
    private int count;

    public Counter(){
        this(0);
    }

    public Counter(int start){
        this.count = start;
    }

    public void increase(){
        setCount(getCount()+1);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}