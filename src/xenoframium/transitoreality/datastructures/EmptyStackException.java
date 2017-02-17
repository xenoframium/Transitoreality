package xenoframium.transitoreality.datastructures;

public class EmptyStackException extends RuntimeException {
    public EmptyStackException() {
        super("Cannot peek or pop on an empty stack.");
    }
}
