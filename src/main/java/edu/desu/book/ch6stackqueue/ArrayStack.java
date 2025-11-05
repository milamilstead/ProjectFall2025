package edu.desu.book.ch6stackqueue;

public class ArrayStack<E> implements Stack<E> {
    // instance variables
    public static final int CAPACITY=1000;   // default array capacity
    private E[] data;                        // generic array used for storage
    private int t = -1;                      // index of the top element in stack

    // constructors
    public ArrayStack() { this(CAPACITY); }  // constructs stack with default capacity
    public ArrayStack(int capacity) {        // constructs stack with given capacity
        data = (E[]) new Object[capacity];   // safe cast; compiler may give warning
    }

    // methods
    /** Returns the number of elements in the stack. */
    public int size() { return (t + 1); }

    /** Tests whether the stack is empty. */
    public boolean isEmpty() { return (t == -1); }

    /** Adds an element at the top of the stack. */
    public void push(E e) throws IllegalStateException {
        if (size() == data.length) throw new IllegalStateException("Stack is full");
        data[++t] = e;                       // increment t before storing new item
    }

    /** Returns, but does not remove, the element at the top of the stack. */
    public E top() {
        if (isEmpty()) return null;
        return data[t];
    }

    /** Removes and returns the top element from the stack. */
    public E pop() {
        if (isEmpty()) return null;
        E answer = data[t];
        data[t] = null;                      // dereference to help garbage collection
        t--;
        return answer;
    }
}