package input;

// From within the Lab directory (or wherever you put the input directory):
// javac input/RASIntGenerator.java
// java input/RASIntGenerator

// YOU SHOULD NOT NEED TO MODIFY THIS FILE
// Any modifications you make will be ignored by the autograder.

import java.util.NoSuchElementException;
import java.io.IOException;

/**
 * This class is used to generate input 1,2, 2, 3, 3, 3, 4, 4, 4, 4...
 * You supply the characters it alternates through (in order) in the constructor.
 * It will be used for the various labs.
 */
public class RASIntGenerator extends InputGenerator<Integer> {
    private int k; // The max integer

    /**
     * Constructor that uses k=10
     */
    public RASIntGenerator() {
        this(10, 100);
    }

    /**
     * Constructor that takes an integer that will be our max.
     * and the number of characters to generate.
     * @param k
     * @param n
     */
    public RASIntGenerator(int k, int n) {
        this(k, n, false);
    }

    /**
     * Constructor that takes an integer that will be our max,
     * the number of characters to generate, and a boolean indicating whether
     * to generate a string representation.
     * @param options
     * @param n
     * @param toString
     */
    public RASIntGenerator(int k, int n, boolean toString) {
        super(n, toString);
        this.k = k;
    }

    /**
     * Returns the next character in the sequence.
     * @return
     * @throws NoSuchElementException
     */
    public Integer nextGen() throws NoSuchElementException {
        return i2b(count % (k*(k+1)/2)) + 1;
    }

    protected static int i2b(int i) {
        double db = (-3.0 + Math.sqrt(9 + 8*i)) / 2.0;
        int b = (int)Math.ceil(db);
        return b;
    }

    // You can run this main method to try out the ALternatingCharGenerator
    public static void main(String[] args) {
        // This tests out the AlternatingCharGenerator.
        InputGenerator<Integer> gen = new RASIntGenerator(4, 100);
        while( gen.hasNext() ) {
            System.out.print(gen.next());
        }
        System.out.println();
        gen = new RASIntGenerator(5, 25, true); // stores the generated characters in a string
        while( gen.hasNext() ) {
            gen.next();
        }
        System.out.println(gen); // prints out the generated characters. Useful for debugging purposes.
    }
}
