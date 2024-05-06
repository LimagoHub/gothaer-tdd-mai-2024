package de.goodyear.collections;

public class Stapel {

    private boolean empty = true;
    public boolean isEmpty() {
        return empty;
    }

    public void push(int i) {
        empty = false;
    }
}
