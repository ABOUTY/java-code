package input;

import java.util.Random;
import java.util.NoSuchElementException;

// YOU SHOULD NOT NEED TO MODIFY THIS FILE
// Any modifications you make will be ignored by the autograder.

/**
 * This class is used to generate input for the various labs.
 * It is abstract, so you can't instantiate it directly.
 * Instead, you should use one of its subclasses.
 * @param <T> the type of elements to generate
 */
public abstract class InputGenerator<T> {
    protected int n;
    protected int count;
    protected Random rand;
    protected StringBuilder sb;
    protected boolean toString;

    /**
     * This method is used to generate the next element of type T.
     * It is abstract, so you can't call it directly.
     * Instead, you should use one of its subclasses.
     * @return
     */
    public abstract T nextGen();


    /**
     * Constructor that takes a number of elements to generate and
     * a boolean indicating whether to generate a string representation.
     * Note that the string representation takes O(n) space, so only
     * use this constructor with toString=true if O(n) space is okay.
     * @param n
     * @param toString
     */
    public InputGenerator(int n, boolean toString ) {
        this.n = n;
        this.toString = toString;
        this.count = 0;
        this.rand = new Random();
        if( toString ) {
            sb = new StringBuilder();
        }
    }


    /**
     * Sets the random seed to the input.
     * This is useful for testing, since it allows you to generate
     * the same sequence of random elements.
     * @param seed
     */
    public void setSeed(long seed) {
        this.rand = new Random(seed);
    }

    /**
     * Generates the next element of type T.
     * @return
     * @throws NoSuchElementException
     */
    public T next() throws NoSuchElementException {
        if( !hasNext() ) {
            throw new NoSuchElementException();
        }
        T c = nextGen();
        if( toString ) {
            sb.append(c);
        }

        count++;
        return c;
    }

    /**
     * Returns true if there are more elements to generate.
     * @return
     */
    public boolean hasNext() {
        return count < n;
    }

    /**
     * Returns a string representation of the generated elements.
     * @return
     */
    public String toString() {
        if( toString ) {
            return sb.toString();
        } else {
            return "";
        }
    }

    // Note that there is no main method because this class is abstract.
    // To see an InputGenerator in action, look at one of its subclasses.
}
