package xenoframium.transitoreality.datastructures;

import java.util.ArrayList;

public class Stack<T> {
    private ArrayList<T> stack = new ArrayList<>();

    public T peek() {
        if (stack.size() == 0) {
            throw new EmptyStackException();
        }
        return stack.get(stack.size() - 1);
    }

    public T pop() {
        if (stack.size() == 0) {
            throw new EmptyStackException();
        }
        T temp = peek();
        stack.remove(stack.size() - 1);
        return temp;
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public boolean push(T t) {
        return stack.add(t);
    }
}
